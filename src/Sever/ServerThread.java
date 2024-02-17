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
      if (Utilities.responseHeaderEquals(response, "GRID_SIZE"))
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
      for (int i = 0; i < navi.length; i++) {
        boolean valid = false;
        while (!valid) {
          int nave = navi[i];
          int row = 0, col = 0, rot = 0;
          os.writeUTF("GRID_REPR:"+clientGriglia.toString());
          os.writeUTF("SHIP_SIZE:"+String.valueOf(nave));
          row = getValueIfHeader(is, os, "INIT_ROW");
          col = getValueIfHeader(is, os, "INIT_COL");
          rot = getValueIfHeader(is, os, "INIT_ROT");
          if (clientGriglia.placeBarca(row, col, rot, nave, i))
            valid = true;
          else
            os.writeUTF("BAD_PLACE:_");
        }
      }
      os.writeUTF("END_SHIPS");
      
      // invia al client la sua griglia con tutte le barche posizionate
      os.writeUTF("GRID_REPR:"+clientGriglia.toString());

      // TODO posiziona le navi del server in maniera casuale

      // TODO ciclo di tentativi per affondaggio navi client
    }
    catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }

  private int getValueIfHeader(DataInputStream is, DataOutputStream os, String expected_header) {
    String response = "";
    try {
      response = is.readUTF();
    } catch (Exception e) {}
    if (Utilities.responseHeaderEquals(response, expected_header))
      return Integer.parseInt(Utilities.getResponseValue(response));
    try {
      os.writeUTF("ERROR:Atteso header \""+expected_header+"\", ma ottenuto header \"" + Utilities.getResponseHeader(response) + "\"");
    } catch (Exception e) {}
    return -1;
  }

}