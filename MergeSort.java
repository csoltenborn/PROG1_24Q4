import java.util.Arrays;

public class MergeSort {
    public static int[] mergeSort(final int[] array) {
        if (array.length <= 1) return array;

        final int middle = array.length / 2;
        final int[] left = mergeSort(Arrays.copyOfRange(array, 0, middle));
        final int[] right = mergeSort(Arrays.copyOfRange(array, middle, array.length));

        return merge(left, right);
    }

    public static int[] merge(final int[] a, final int[] b) {
        final int[] result = new int[a.length + b.length];

        for (int aIndex = 0, bIndex = 0; aIndex + bIndex < a.length + b.length;) {
            final int resultIndex = aIndex + bIndex;

            if (aIndex == a.length) {
                System.arraycopy(b, bIndex, result, resultIndex, b.length - bIndex);
                break;
            }

            if (bIndex == b.length) {
                System.arraycopy(a, aIndex, result, resultIndex, a.length - aIndex);
                break;
            }

            result[resultIndex] = a[aIndex] < b[bIndex] ? a[aIndex++] : b[bIndex++];
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeSort(new int[]{})));
        System.out.println(Arrays.toString(mergeSort(new int[]{42})));
        System.out.println(Arrays.toString(mergeSort(new int[]{3, -7})));
        System.out.println(Arrays.toString(mergeSort(new int[]{1, 2, 3})));
        System.out.println(Arrays.toString(mergeSort(new int[]{3, 2, 1})));
        System.out.println(Arrays.toString(mergeSort(new int[]{1, 3, 2})));
        System.out.println(Arrays.toString(mergeSort(new int[]{44, 9, 2, 4, -2, 4, 12})));
    }

}
