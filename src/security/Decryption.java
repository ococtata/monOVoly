package security;

import config.SecurityConfig;

public class Decryption {
	
	public static String passwordDecrypt(String password) {
        String shifted = shiftLeft(password, SecurityConfig.SHIFT_AMOUNT);
        String swapped = swapAdjacent(shifted);
        String original = reverseString(swapped);
        return original;
    }

    private static String reverseString(String string) {
        char[] array = string.toCharArray();
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            char temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }

        return new String(array);
    }

    private static String swapAdjacent(String string) {
        char[] array = string.toCharArray();

        for (int i = 0; i < array.length - 1; i += 2) {
            char temp = array[i];
            array[i] = array[i + 1];
            array[i + 1] = temp;
        }

        return new String(array);
    }

    private static String shiftLeft(String string, int amount) {
        char[] array = string.toCharArray();

        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] - amount);
        }

        return new String(array);
    }
}
