//Importo i package
import java.net.*;
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
      // input - output
      DataInputStream is = new DataInputStream(socket.getInputStream());
      DataOutputStream os = new DataOutputStream(socket.getOutputStream());
      
      // prende l'input della dimensione della tabella dal client
      String msg = is.readUTF();
      String[] response = msg.split(":");
      int size_tabella;
      if (response[0].compareTo("GRID_SIZE")==0)
        try {
          size_tabella = Integer.parseInt(response[1]);
        } catch (Exception e) {
          System.out.println("Errore nel parsing! Assegno numero casuale a size_tabella");
          size_tabella = 5;
        }
      else {
        os.writeUTF("ERROR:Il programma ha riscontrato un problema. Termino l'esecuzione.");
        return;
      }
      System.out.println("Dimensione griglia: "+size_tabella);
      
      // invia al client il numero di navi e la dimensione di ciascuna
      os.writeUTF("SHIPS_NUMBER:"+navi.length);
      for (int nave : navi) {
        os.writeUTF("SHIP_SIZE:"+String.valueOf(nave));
      }
      
      // TODO riceve dal client il posizionamento di ciascuna nave

      // TODO posiziona le navi del server in maniera casuale

      // TODO ciclo di tentativi per affondaggio navi client
    }
    catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }
}