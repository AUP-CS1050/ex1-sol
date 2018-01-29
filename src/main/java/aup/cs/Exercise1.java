package aup.cs;

import java.util.Arrays;

/**
 * The class is defined as final since utility classes should not be extensible.
 */

public final class Exercise1 {

    /**
    * Utility classes should not have a public or default constructor.
    */
    private Exercise1() { }

    /**
     * Print 1 character to System.out.
     * For example printCharInt('*') prints 42
     * @param c the character to print
     */

    static void printCharInt(char c) {
        int i = (int)c;
        System.out.println(i);
    }

    /**
     * Print the base 2 of an integer.
     * For example printIntBase2(42) should print 101010
     * @param n the number to print
     */
    static void printIntBase2(int n) {
        while (n > 0) {
            System.out.println(n % 2);
            n = n / 2;
        }
    }

    /**
     * Print the given bit array to System.out.
     * It should print 1 for each true value and 0 for a false one
     * @param arr the boolean array to print
     */
    static void printBitArray(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == true) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
    }

    // part 2

    /**
     * Convert a char into a 16 bits array.
     * @param c the char to print
     * @return a boolean array of length 16 representing the bit presentation of the character
     */
    static boolean[] char2bitArray(char c) {
        int s = (int)c;
        int i = 0;
        boolean[] arr1 = new boolean[16];
        while (s > 0) {
            if (s % 2 == 0) {
                arr1[i] = false;
            } else {
                arr1[i] = true;
            }
            s = s / 2;
            i++;
        }
        return arr1;
    }


    /**
     * Convert a 16 bits array into a char.
     * @param arr a boolean array of length 16 representing a bit array
     * @return the character denoted by the bit array
     */
    static char bitArray2char(boolean[] arr) {
        int power = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == true) {
                sum += (1 * Math.pow(2, power));
            }
            power++;
        }
        char c = (char)sum;
        return c;
    }

    /**
     * Test the previous two coding functions by converting the text into an array of characters.
     * Then for each character, it converts it into a bit array and
     * then converts the bit array back into a character.
     * Finally, the program constructs a new String given an array of all the generated characters.
     * Print the text before and after the transformations.
     * @param text an input string to test
     */
    static void testCoding(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            boolean[] binary = char2bitArray(chars[i]);
            char c = bitArray2char(binary);
            if (c != chars[i]) {
                System.out.println("error");
            }
        }
    }

    // part 3

    /**
     * Computes the next bit of LFSR.
     * @param arr the bit array (initially the seed)
     * @param coefs the binary coefficients
     * @return a boolean the combination of adding the multiplication of each bit with a coefficient
     */
    static boolean feedbackLfsr(boolean[] arr, boolean[] coefs) {
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] && coefs[coefs.length - 1 - i] == true) {
                counter++;
            }
        }
        boolean comb = counter % 2 == 1;
        return comb;
    }

    /**
     * Computes the next state of the bit array.
     * @param arr the bit array (initially the seed)
     * @param coefs the binary coefficients
     * @return the next bit to use for encoding.
     */
    static boolean feedbackUpdateLfsr(boolean[] arr, boolean[] coefs) {
        boolean next = feedbackLfsr(arr, coefs);
        boolean ret = arr[arr.length - 1];
        for (int i = arr.length - 1; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = next;
        return ret;
    }

    /**
     * Encode the char by XORing each bit with the returned one from feedbackUpdateLFSR.
     * @param c the char to encode
     * @param arr the bit array (initially the seed)
     * @param coefs the binary coefficients
     * @return the encoded char
     */
    static char encodeCharLfsr(char c, boolean[] arr, boolean[] coefs) {
        boolean[] charArray = char2bitArray(c);
        boolean update;
        for (int i = 0; i < charArray.length; i++) {
            update = feedbackUpdateLfsr(arr, coefs);
            if (charArray[i] == update) {
                charArray[i] = false;
            } else {
                charArray[i] = true;
            }
        }
        char charReturn = bitArray2char(charArray);
        return charReturn;
    }

    /**
     * Encode the char array by encoding each char in it.
     * @param chars the char array to encode
     * @param arr the bit array (initially the seed)
     * @param coefs the binary coefficients
     * @return the encoded char
     */
    static char[] encodeCharArrayLfsr(char[] chars, boolean[] arr, boolean[] coefs) {
        char[] encodedChar = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            encodedChar[i] = encodeCharLfsr(chars[i], arr, coefs);
        }
        return encodedChar;
    }

    /**
     * Test the LFSR encoding and decoding.
     * 1. copy the bit array (the seed) and out aside
     * 2. Transform the text into a char array
     * 3. Encode using encodeCharArrayLFSR and print
     * 4. Decode the encoded message using the copied seed and encodeCharArrayLFSR and print
     * @param text the text to test
     * @param arr the bit array (initially the seed)
     * @param coefs the binary coefficients
     */
    static void testLfsr(String text, boolean[] arr, boolean[] coefs) {
        boolean[] seedCopy = java.util.Arrays.copyOf(arr,arr.length);
        char[] chars = text.toCharArray();
        char[] encodedChar = encodeCharArrayLfsr(chars, arr, coefs);
        String encodedMessage = new String(encodedChar);
        System.out.println(encodedMessage);
        char[] decodedMessage = encodeCharArrayLfsr(encodedChar, seedCopy, coefs);
        String message = new String(decodedMessage);
        System.out.println(message);
    }

}
