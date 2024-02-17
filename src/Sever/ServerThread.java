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

      for (int i = 0; i < navi.length; i++) {
        boolean valid = false;
        while (!valid) {
          int nave = navi[i];
          os.writeUTF("SHIP_SIZE:"+String.valueOf(nave));
          os.writeUTF("GRID_REPR:"+clientGriglia.toString());
          int row = Integer.parseInt(Utilities.getResponseValue(is.readUTF())); // check header == INIT_ROW ???
          int col = Integer.parseInt(Utilities.getResponseValue(is.readUTF())); // check header == INIT_COL ???
          int rot = Integer.parseInt(Utilities.getResponseValue(is.readUTF())); // check header == INIT_ROT ???
          if (clientGriglia.placeBarca(row, col, rot, nave, i))
            valid = true;
          else
            os.writeUTF("BAD_PLACE:_");
        }
      }
      os.writeUTF("END_SHIPS");
      os.writeUTF("GRID_REPR:"+clientGriglia.toString());

      // TODO posiziona le navi del server in maniera casuale

      // TODO ciclo di tentativi per affondaggio navi client
    }
    catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }
}