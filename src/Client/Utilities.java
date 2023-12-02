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
}
