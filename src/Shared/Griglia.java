package Shared;

public class Griglia {
    int griglia[][]; // griglia [riga][colonna]
    // 0 = vuoto; n-1 = barca; n-2 = colpito; n-3 colpito e affondato (?)
    int currentBarca = 1;

    public Griglia(int dimensione) {
        griglia = new int[dimensione][];
        for (int i = 0; i < griglia.length; i++)
            griglia[i] = new int[dimensione];
    }

    public String toString() {
        String s = "";
        for (int j = 0; j < griglia[0].length; j++) {
            for (int i = 0; i < griglia.length; i++) {
                if (griglia[i][j] == 0)
                    s += "[ ]";
                else if(Utilities.getBarcaStatus(griglia[i][j]) == 1)
                    s += "[O]";
                else if(Utilities.getBarcaStatus(griglia[i][j]) == 2)
                    s += "[X]";
            }
            s += "\n";
        }
        return s;
    }

    // rotation: [Nord: 1; Est: 2; Sud: 3; Ovest: 4]
    public boolean placeBarca(int row, int column, int rotation, int length) {
        int xAdd = 0;
        int yAdd = 0;
        if (rotation == 0)
            yAdd = -1;
        else if (rotation == 1)
            xAdd = 1;
        else if (rotation == 2)
            yAdd = 1;
        else if (rotation == 3)
            xAdd = -1;

        // "la barca può essere posizionata senza uscire dai bordi o sovrapporsi ad un'altra barca?"
        for (int i = 0; i < length; i++) {
            int currRow = row + yAdd * i;
            int currCol = column + xAdd * i;
            if (currRow >= griglia.length || currCol >= griglia[0].length || griglia[currRow][currCol] != 0)
                System.out.println(currRow + " " + currCol);
                return false;
        }

        // posizionamento barca
        for (int i = 0; i < length; i ++) {
            int currRow = row + yAdd * i;
            int currCol = column + xAdd * i;
            griglia[currRow][currCol] = currentBarca;
        }
        currentBarca += 1;
        return true;

    }
}
