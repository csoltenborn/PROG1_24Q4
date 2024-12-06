import java.io.IOException;

public class BattleShip {

    static final int SIZE = 10;

    static final String ENTER_SHIP_COORDINATE_PROMPT = "Geben Sie die %skoordinaten für ein Schiff der Länge %d ein: ";

    static int distance(final Coordinate start, final Coordinate end) {
        return Math.abs(start.column() - end.column()) + Math.abs(start.row() - end.row());
    }

    static Coordinate getRandomCoordinate() {
        return new Coordinate(Utility.getRandomInt(BattleShip.SIZE), Utility.getRandomInt(BattleShip.SIZE));
    }

    static boolean onOneLine(final Coordinate start, final Coordinate end) {
        return start.column() == end.column() || start.row() == end.row();
    }

    static void showSeparatorLine() {
        System.out.println("   +-+-+-+-+-+-+-+-+-+-+      +-+-+-+-+-+-+-+-+-+-+");
    }

    static int getMaxSurroundingColumn(final Coordinate start, final Coordinate end) {
        return Math.min(BattleShip.SIZE - 1, Math.max(start.column(), end.column()) + 1);
    }

    static int getMaxSurroundingRow(final Coordinate start, final Coordinate end) {
        return Math.min(BattleShip.SIZE - 1, Math.max(start.row(), end.row()) + 1);
    }

    static int getMinSurroundingColumn(final Coordinate start, final Coordinate end) {
        return Math.max(0, Math.min(start.column(), end.column()) - 1);
    }

    static int getMinSurroundingRow(final Coordinate start, final Coordinate end) {
        return Math.max(0, Math.min(start.row(), end.row()) - 1);
    }

    static Coordinate toCoordinate(final String input) {
        return new Coordinate(input.toUpperCase().charAt(0) - 65, Integer.parseInt(input.substring(1)) - 1);
    }

    static boolean isValidCoordinate(final String input) {
        return input.matches("[A-Ja-j]([1-9]|10)");
    }

    static String getStartCoordinatePrompt(final int length) {
        return String.format(BattleShip.ENTER_SHIP_COORDINATE_PROMPT, "Start", length);
    }

    static String getEndCoordinatePrompt(final int length) {
        return String.format(BattleShip.ENTER_SHIP_COORDINATE_PROMPT, "End", length);
    }

    static void showRowNumber(final int row) {
        if (row < 9) {
            System.out.print(" ");
        }
        System.out.print(String.valueOf(row + 1));
    }

    static Coordinate getRandomEndCoordinate(final Coordinate start, final int distance) {
        int choices = 0;
        if (start.column() >= distance) {
            choices++;
        }
        if (start.column() < BattleShip.SIZE - distance) {
            choices++;
        }
        if (start.row() >= distance) {
            choices++;
        }
        if (start.row() < BattleShip.SIZE - distance) {
            choices++;
        }
        int skip = Utility.getRandomInt(choices);
        if (start.column() >= distance) {
            skip--;
            if (skip < 0) {
                return new Coordinate(start.column() - distance, start.row());
            }
        }
        if (start.column() < BattleShip.SIZE - distance) {
            skip--;
            if (skip < 0) {
                return new Coordinate(start.column() + distance, start.row());
            }
        }
        if (start.row() >= distance) {
            skip--;
            if (skip < 0) {
                return new Coordinate(start.column(), start.row() - distance);
            }
        }
        return new Coordinate(start.column(), start.row() + distance);
    }

    static void showField(final Field field, final boolean showShips) {
        switch (field) {
            case SHIP:
                System.out.print(showShips ? "O" : " ");
                break;
            case SHIP_HIT:
                System.out.print("*");
                break;
            case WATER_HIT:
                System.out.print("X");
                break;
            case WATER:
            default:
                System.out.print(" ");
        }
    }

    static void placeShip(final Coordinate start, final Coordinate end, final Field[][] field) {
        if (start.column() == end.column()) {
            for (int row = Math.min(start.row(), end.row()); row <= Math.max(start.row(), end.row()); row++) {
                field[start.column()][row] = Field.SHIP;
            }
        } else {
            for (
                    int column = Math.min(start.column(), end.column());
                    column <= Math.max(start.column(), end.column());
                    column++
            ) {
                field[column][start.row()] = Field.SHIP;
            }
        }
    }

    static void showRow(final int row, final Field[][] ownField, final Field[][] otherField) {
        BattleShip.showRowNumber(row);
        System.out.print(" |");
        for (int column = 0; column < BattleShip.SIZE; column++) {
            BattleShip.showField(ownField[column][row], true);
            System.out.print("|");
        }
        System.out.print("   ");
        BattleShip.showRowNumber(row);
        System.out.print(" |");
        for (int column = 0; column < BattleShip.SIZE; column++) {
            BattleShip.showField(otherField[column][row], false);
            System.out.print("|");
        }
        System.out.println();
    }

    static void showFields(final Field[][] ownField, final Field[][] otherField) {
        System.out.println("    A B C D E F G H I J        A B C D E F G H I J ");
        BattleShip.showSeparatorLine();
        for (int row = 0; row < BattleShip.SIZE; row++) {
            BattleShip.showRow(row, ownField, otherField);
            BattleShip.showSeparatorLine();
        }
        System.out.println();
    }

    static boolean shipSunk(final Coordinate shot, final Field[][] field) {
        int column = shot.column();
        while (column < BattleShip.SIZE - 1 && field[column][shot.row()] == Field.SHIP_HIT) {
            column++;
        }
        if (field[column][shot.row()] == Field.SHIP) {
            return false;
        }
        column = shot.column();
        while (column > 0 && field[column][shot.row()] == Field.SHIP_HIT) {
            column--;
        }
        if (field[column][shot.row()] == Field.SHIP) {
            return false;
        }
        int row = shot.row();
        while (row < BattleShip.SIZE -1 && field[shot.column()][row] == Field.SHIP_HIT) {
            row++;
        }
        if (field[shot.column()][row] == Field.SHIP) {
            return false;
        }
        row = shot.row();
        while (row > 0 && field[shot.column()][row] == Field.SHIP_HIT) {
            row--;
        }
        if (field[shot.column()][row] == Field.SHIP) {
            return false;
        }
        return true;
    }

    static void setAllFree(final Field[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = Field.WATER;
            }
        }
    }

    static int countHits(final Field[][] field) {
        int result = 0;
        for (int column = 0; column < field.length; column++) {
            for (int row = 0; row < field[column].length; row++) {
                if (field[column][row] == Field.SHIP_HIT) {
                    result++;
                }
            }
        }
        return result;
    }

    static void fillWaterHits(final Coordinate shot, final Field[][] field) {
        int columnMin = shot.column();
        while (columnMin > 0 && field[columnMin][shot.row()] == Field.SHIP_HIT) {
            columnMin--;
        }
        int columnMax = shot.column();
        while (columnMax < BattleShip.SIZE - 1 && field[columnMax][shot.row()] == Field.SHIP_HIT) {
            columnMax++;
        }
        int rowMin = shot.row();
        while (rowMin > 0 && field[shot.column()][rowMin] == Field.SHIP_HIT) {
            rowMin--;
        }
        int rowMax = shot.row();
        while (rowMax < BattleShip.SIZE - 1 && field[shot.column()][rowMax] == Field.SHIP_HIT) {
            rowMax++;
        }
        for (int column = columnMin; column <= columnMax; column++) {
            for (int row = rowMin; row <= rowMax; row++) {
                if (field[column][row] == Field.WATER) {
                    field[column][row] = Field.WATER_HIT;
                }
            }
        }
    }

    static boolean noConflict(final Coordinate start, final Coordinate end, final Field[][] field) {
        for (
                int column = BattleShip.getMinSurroundingColumn(start, end);
                column <= BattleShip.getMaxSurroundingColumn(start, end);
                column++
        ) {
            for (
                    int row = BattleShip.getMinSurroundingRow(start, end);
                    row <= BattleShip.getMaxSurroundingRow(start, end);
                    row++
            ) {
                if (field[column][row] != Field.WATER) {
                    return false;
                }
            }
        }
        return true;
    }

    static Coordinate readCoordinate(final String prompt) {
        String input = "";
        while (!BattleShip.isValidCoordinate(input)) {
            System.out.print(prompt);
            try {
                input = Utility.readStringFromConsole();
            } catch (final IOException e) {
                input = "";
            }
            if ("exit".equals(input)) {
                System.exit(0);
            }
        }
        return BattleShip.toCoordinate(input);
    }

}