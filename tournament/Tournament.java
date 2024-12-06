package tournament;

import java.io.*;

public class Tournament {

    public static void main(final String[] args) {
        TournamentNode root = Tournament.readParticipants();
        while (!Tournament.finished(root)) {
            Tournament.showCurrentState(root);
            root = Tournament.readNextResult(root);
        }
        Tournament.showCurrentState(root);
    }

    static TournamentNode addParticipant(final String name, final TournamentNode node) {
        if (node == null) {
            return new TournamentNode(null, null, name, 0);
        }
        if (node.winner() != null) {
            return new TournamentNode(node, new TournamentNode(null, null, name, 0), null, 0);
        }
        final int left = Tournament.countNames(node.left());
        final int right = Tournament.countNames(node.right());
        if (left > right) {
            return new TournamentNode(node.left(), Tournament.addParticipant(name, node.right()), null, 0);
        }
        return new TournamentNode(Tournament.addParticipant(name, node.left()), node.right(), null, 0);
    }

    static int countNames(final TournamentNode node) {
        if (node == null) {
            return 0;
        }
        return (node.winner() == null ? 0 : 1)
            + Tournament.countNames(node.left())
            + Tournament.countNames(node.right());
    }

    static void fillDisplay(
        final TournamentNode node,
        final int level,
        final int row,
        final int columnLength,
        final int height,
        final char[][] display
    ) {
        if (node == null) {
            return;
        }
        final String name = node.winner();
        if (name != null) {
            final int startIndexOfColumn = (height - level) * (columnLength + 1);
            final char[] nameAsArray = name.toCharArray();
            for (int i = 0; i < nameAsArray.length; i++) {
                display[row][startIndexOfColumn + i] = nameAsArray[i];
            }
            if (level > 0) {
                final int pointsOffset = startIndexOfColumn + nameAsArray.length;
                display[row][pointsOffset + 1] = '(';
                final char[] points = String.valueOf(node.points()).toCharArray();
                for (int i = 0; i < points.length; i++) {
                    display[row][pointsOffset + 2 + i] = points[i];
                }
                display[row][pointsOffset + 2 + points.length] = ')';
            }
        }
        Tournament.fillDisplay(node.left(), level + 1, row, columnLength, height, display);
        Tournament.fillDisplay(
            node.right(),
            level + 1,
            row + Tournament.rowOffset(level, height),
            columnLength,
            height,
            display
        );
    }

    static boolean finished(final TournamentNode root) {
        return root.winner() != null;
    }

    static int getHeight(final TournamentNode node) {
        if (node == null) {
            return -1;
        }
        return Math.max(Tournament.getHeight(node.left()), Tournament.getHeight(node.right())) + 1;
    }

    static int getLengthOfLongestColumn(final TournamentNode node) {
        if (node == null) {
            return 0;
        }
        final int max =
            Math.max(
                Tournament.getLengthOfLongestColumn(node.left()),
                Tournament.getLengthOfLongestColumn(node.right())
            );
        return Math.max(
            max,
            node.winner() == null ? 0 : node.winner().length() + String.valueOf(node.points()).length() + 3
        );
    }

    static int getNumberOfLeaves(final TournamentNode node) {
        if (node.left() == null && node.right() == null) {
            return 1;
        }
        return Tournament.getNumberOfLeaves(node.left()) + Tournament.getNumberOfLeaves(node.right());
    }

    static int powerOf2(final int nonNegativeNumber) {
        int result = 1;
        for (int i = 0; i < nonNegativeNumber; i++) {
            result *= 2;
        }
        return result;
    }

    static TournamentNode readNextResult(final TournamentNode node) {
        if (node.left().winner() != null && node.right().winner() != null) {
            int pointsLeft = Tournament.readPoints(node.left().winner());
            int pointsRight = Tournament.readPoints(node.right().winner());
            while (pointsLeft == pointsRight) {
                System.out.println("Punktzahl darf nicht gleich sein!");
                pointsLeft = Tournament.readPoints(node.left().winner());
                pointsRight = Tournament.readPoints(node.right().winner());
            }
            return new TournamentNode(
                Tournament.setPoints(node.left(), pointsLeft),
                Tournament.setPoints(node.right(), pointsRight),
                pointsLeft > pointsRight ? node.left().winner() : node.right().winner(),
                0
            );
        }
        if (node.left().winner() != null) {
            return new TournamentNode(node.left(), Tournament.readNextResult(node.right()), null, 0);
        }
        if (node.right().winner() != null) {
            return new TournamentNode(Tournament.readNextResult(node.left()), node.right(), null, 0);
        }
        final int left = Tournament.countNames(node.left());
        final int right = Tournament.countNames(node.right());
        if (left > right) {
            return new TournamentNode(node.left(), Tournament.readNextResult(node.right()), null, 0);
        }
        return new TournamentNode(Tournament.readNextResult(node.left()), node.right(), null, 0);
    }

    static TournamentNode readParticipants() {
        TournamentNode root = null;
        String input = null;
        while (!"".equals(input)) {
            if (input != null) {
                root = Tournament.addParticipant(input, root);
            }
            System.out.print("Geben Sie den nächsten Teilnehmer ein (leere Eingabe zum Beenden): ");
            try {
                input = Utility.readStringFromConsole();
            } catch (final IOException e) {
                input = "";
            }
        }
        return root;
    }

    static int readPoints(final String name) {
        boolean ok = false;
        int points = 0;
        while (!ok) {
            System.out.print("Punktzahl " + name + ": ");
            try {
                points = Integer.parseInt(Utility.readStringFromConsole());
                ok = true;
            } catch (NumberFormatException | IOException e) {
                // repeat input
            }
        }
        return points;
    }

    static int rowOffset(final int level, final int height) {
        return Tournament.powerOf2(height) / Tournament.powerOf2(level);
    }

    static TournamentNode setPoints(final TournamentNode node, final int points) {
        return new TournamentNode(node.left(), node.right(), node.winner(), points);
    }

    static void showCurrentState(final TournamentNode root) {
        final char[][] display = Tournament.toDisplay(root);
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                System.out.print(display[i][j]);
            }
            System.out.println();
        }
    }

    static char[][] toDisplay(final TournamentNode root) {
        final int columnLength = Tournament.getLengthOfLongestColumn(root);
        final int height = Tournament.getHeight(root);
        final char[][] display = new char[Tournament.powerOf2(height) * 2][(columnLength + 1) * (height + 1)];
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                display[i][j] = ' ';
            }
        }
        Tournament.fillDisplay(root, 0, 0, columnLength, height, display);
        return display;
    }

}
