package Shared;
import java.io.BufferedReader;
import java.io.IOException;

public class Utilities {
    public static int getInputNumber(BufferedReader input, String reqmsg) {
        try {
            System.out.print(reqmsg);
            int number = Integer.parseInt(input.readLine());
            return number;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Il valore inserito non è un numero!");
            return getInputNumber(input, reqmsg);
        }
    }

    public static int getInputNumberLowerThan(BufferedReader input, String reqmsg, int lower_than) {
        int number = getInputNumber(input, reqmsg);
        if (number >= lower_than) {
            System.out.println("Il numero deve essere minore di "+lower_than+ " !");
            return getInputNumberLowerThan(input, reqmsg, lower_than);
        } else
            return number;
    }

    public static int getInputNumberFromList(BufferedReader input, String reqmsg, int list[]) {
        int number = getInputNumber(input, reqmsg);
        for (int i = 0; i < list.length; i++)
            if (list[i] == number)
                return number;
        System.out.print("Il numero deve essere uno tra i seguenti: ");
        for (int i = 0; i < list.length; i++)
            System.out.print(list[i]+"; ");
        System.out.print("\n");
        return getInputNumberFromList(input, reqmsg, list);
    }

    /*
     * Returns 0 if the number is not a barca number, the number of the barca if the number is the number of a valid barca 
     */
    public static int isValidBarcaNumber(int num) {
        String s = String.valueOf(num);
        String sub = s.substring(s.length() - 2);
        if (sub.compareTo("1") == 0 || sub.compareTo("2") == 0)
            return Integer.parseInt(s.substring(0, s.length() - 2));
        return 0;
    }

    /*
     * Returns 1 if not hit, 2 if hit
     */
    public static int getBarcaStatus(int num) {
        String s = String.valueOf(num);
        return Integer.parseInt(s.substring(s.length() - 2));
    }


    public static String getResponseHeader(String response) {
        return response.split(":")[0];
    }

    public static String getResponseValue(String response) {
        return response.split(":")[1];
    }
}
