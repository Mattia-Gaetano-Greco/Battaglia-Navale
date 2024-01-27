//Importo i package
import java.net.*;

import Shared.Griglia;
import Shared.Utilities;

import java.io.*;

//Creazione di una classe per il Multrithreading
class ServerThread extends Thread {
  private Socket socket;

  private int[] navi = new int[]{5, 5, 4, 3, 2, 2};

  public ServerThread (Socket socket) {
    this.socket = socket;
    System.out.println("  Stato    Tipo Richiesta  Porta Server  Porta Client  Indirizzo Cliernt\n");
  }

  //esecuzione del Thread sul Socket
  public void run() {
    try {
      Griglia serverGriglia;
      Griglia clientGriglia;
      // input - output
      DataInputStream is = new DataInputStream(socket.getInputStream());
      DataOutputStream os = new DataOutputStream(socket.getOutputStream());
      
      // prende l'input della dimensione della griglia dal client
      String response = is.readUTF();
      int size_griglia;
      if (Utilities.getResponseHeader(response).compareTo("GRID_SIZE")==0)
        try {
          size_griglia = Integer.parseInt(Utilities.getResponseValue(response));
        } catch (Exception e) {
          System.out.println("Errore nel parsing! Assegno numero casuale a size_griglia");
          size_griglia = 5;
        }
      else {
        os.writeUTF("ERROR:Il programma ha riscontrato un problema. Termino l'esecuzione.");
        return;
      }

      // crea la griglia del server e del client
      serverGriglia = new Griglia(size_griglia);
      clientGriglia = new Griglia(size_griglia);
      System.out.println("Griglia: ");
      System.out.println(serverGriglia.toString());
      os.writeUTF("GRID_REPR:"+clientGriglia.toString());

      // invia al client il numero di navi e la dimensione di ciascuna

      /*
     * Per la richiesta navi: (ADATTARLO AL SERVER, QUESTA è LA VERSIONE DEL CLIENT)
     * ciclo finchè il server non mi restituisce "END_SHIPS"
     * 1. il server mi da la dimensione della nave insieme all'immagine della griglia attuale
     * 2. inserisco in input riga e colonna iniziale della nave e orientamento (Nord - sud - ovest - est)
     * 3. il server mi restituisce:
     *  3a. posizione non valida! La barca esce dai confini della griglia
     *  3b. posizione non valida! La barca si sovrappone ad un'altra barca
     *  3c. posizione confermata   
     */

      //os.writeUTF("END_SHIPS:"+navi.length);
      for (int nave : navi) {
        boolean valid = false;
        while (!valid) {
          os.writeUTF("SHIP_SIZE:"+String.valueOf(nave));
          os.writeUTF("GRID_REPR:"+clientGriglia.toString());
          int row = Integer.parseInt(Utilities.getResponseValue(is.readUTF())); // check header == INIT_ROW ???
          int col = Integer.parseInt(Utilities.getResponseValue(is.readUTF())); // check header == INIT_COL ???
          int rot = Integer.parseInt(Utilities.getResponseValue(is.readUTF())); // check header == INIT_ROT ???
          if (clientGriglia.placeBarca(row, col, rot, nave))
            valid = true;
          else
            os.writeUTF("BAD_PLACE:_");
        }
      }
      os.writeUTF("END_SHIPS");

      // TODO posiziona le navi del server in maniera casuale

      // TODO ciclo di tentativi per affondaggio navi client
    }
    catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }
}