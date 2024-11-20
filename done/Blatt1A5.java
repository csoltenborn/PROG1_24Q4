package done;

public class Blatt1A5 {

    public static void main(String[] args) {
        System.out.println(product(23, 42));

        System.out.println(squaresum(23, 24));

        output("Eine Ausgabe");

        warning();
    }

    static int product(int x, int y) {
        return x * y;
    }

    static int squaresum(int x, int y) {
        return x * x + y * y;
    }

    static void output(String content) {
        System.out.println(content);
    }

    static void warning() {
        System.out.println("WARNUNG");
    }

}