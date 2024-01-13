package Shared;

public class Griglia {
    int griglia[][]; // griglia [riga][colonna]
    // 0 = vuoto; n-1 = barca; n-2 = colpito; n-3 colpito e affondato (?)

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
}
