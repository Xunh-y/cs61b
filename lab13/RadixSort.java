/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    public static void main(String[] args) {
        String[] s1 = {"dwa", "1ad2", "sad2", "8s8", "2222", "2221", "uu2"};
        for (String s : s1) {
            System.out.println(s);
        }
        s1 = sort(s1);
        for (String s : s1) {
            System.out.println(s);
        }
        String[] s2 = {"11", "1004", "2199", "22", "208888", "234", "2341"};
        for (String s : s2) {
            System.out.println(s);
        }
        s2 = sort(s2);
        for (String s : s2) {
            System.out.println(s);
        }
    }
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int maxPos = 0;
        for (String s : asciis) {
            if (s.length() > maxPos) {
                maxPos = s.length();
            }
        }
        for (int d = 0; d < maxPos; d++) {
            sortHelperLSD(asciis, d);
        }
        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int r = 256;
        int[] cnt = new int[r + 1];
        for (String item: asciis) {
            int c = getchar(item, index);
            cnt[c]++;
        }

        int[] starts = new int[r + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += cnt[i];
        }

        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            String a = asciis[i];
            int item = getchar(a, index);
            int place = starts[item];
            sorted[place] = a;
            starts[item] += 1;
        }
        for (int i = 0; i < sorted.length; ++i) {
            asciis[i] = sorted[i];
        }
        return;
    }

    private static int getchar(String item, int index) {
        if (item.length() > index) {
            return item.charAt(index) + 1;
        } else {
            return 0;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
