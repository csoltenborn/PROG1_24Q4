package done;

public class Blatt4A1 {

    public static void main(String[] args) {
        System.out.println(max(new int[]{-5, 3, 6, 1, -7}));
    }

    static int max(final int[] array) {
        int result = array[0];
        for (int i = 1; i < array.length; i++) {
            result = Math.max(result, array[i]);
        }
        return result;
    }

}