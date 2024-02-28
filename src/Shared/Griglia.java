package Shared;

public class Griglia {
    String griglia[][]; // griglia [riga][colonna] -> [[0-0, 0-1, 0-2],[1-0, 1-1, 1-2],[2-0, 2-1, 2-2]]
    // Possibili valori nella griglia [n è il valore della barca]:
    // 000 = vuoto; n-1 = barca; n-2 = colpito; n-3 colpito e affondato; TTT = attaccato ma non c'era niente

    public Griglia(int dimensione) {
        griglia = new String[dimensione][];
        for (int i = 0; i < griglia.length; i++) {
            griglia[i] = new String[dimensione];
            for (int j = 0; j < griglia[i].length; j++) {
                griglia[i][j] = "000";
            }
        }
    }

    public int getSize() {
        return griglia.length;
    }

    public String toString() {
        String s = "   ";
        for (int i = 0; i < griglia.length; i++)
            s += i + "  ";
        s += "\n";
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                // scrittura indice righe - colonne
                if (j == 0)
                    s += i + " ";
                // scrittura barche
                if (griglia[i][j].compareTo("000") == 0)
                    s += "[ ]";
                else if(griglia[i][j].compareTo("TTT") == 0)
                    s += "[-]";
                else if (Utilities.getBarcaStatus(griglia[i][j]) == 1)
                    s += "[O]";
                else if (Utilities.getBarcaStatus(griglia[i][j]) == 2)
                    s += "[x]";
                else if (Utilities.getBarcaStatus(griglia[i][j]) == 3)
                    s += "[X]";
            }
            s += "\n";
        }
        return s;
    }

    public String toString(boolean hideship) {
        if (!hideship)
            return toString();
        String s = "   ";
        for (int i = 0; i < griglia.length; i++)
            s += i + "  ";
        s += "\n";
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                // scrittura indice righe - colonne
                if (j == 0)
                    s += i + " ";
                // scrittura barche
                if (griglia[i][j].compareTo("000") == 0)
                    s += "[ ]";
                else if(griglia[i][j].compareTo("TTT") == 0)
                    s += "[-]";
                else if (Utilities.getBarcaStatus(griglia[i][j]) == 2)
                    s += "[x]";
                else if (Utilities.getBarcaStatus(griglia[i][j]) == 3)
                    s += "[X]";
            }
            s += "\n";
        }
        return s;
    }

    // rotation: [Nord: 1; Est: 2; Sud: 3; Ovest: 4]
    public boolean placeBarca(int row, int column, int rotation, int length, int num_barca) {
        int xAdd = 0;
        int yAdd = 0;
        if (rotation == 1)
            yAdd = -1;
        else if (rotation == 2)
            xAdd = 1;
        else if (rotation == 3)
            yAdd = 1;
        else if (rotation == 4)
            xAdd = -1;

        // "la barca può essere posizionata senza uscire dai bordi o sovrapporsi ad un'altra barca?"
        for (int i = 0; i < length; i++) {
            int currRow = row + yAdd * i;
            int currCol = column + xAdd * i;
            if (currRow < 0 || currCol < 0 || currRow >= griglia.length || currCol >= griglia[0].length || griglia[currRow][currCol] != "000") {
                return false;
            }
        }

        // posizionamento barca
        for (int i = 0; i < length; i ++) {
            int currRow = row + yAdd * i;
            int currCol = column + xAdd * i;
            griglia[currRow][currCol] = String.valueOf(num_barca)+"-1";
        }
        return true;

    }

    public int colpisci(int row, int column) {
        String val = griglia[row][column];
        if (val.compareTo("000") == 0) {
            griglia[row][column] = "TTT";
            return 0; // non c'era niente
        } else if (Utilities.getBarcaStatus(val) != 1) {
            return -1; // ha già provato a colpire questa posizione
        } else {
            // TODO: dare un codice diverso in base se è colpito o colpito e affondato
            griglia[row][column] = Utilities.getBarcaNumber(val) + "-" + 1;
            return 1;
        }
    }

}
