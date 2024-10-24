import java.io.*;

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

}
