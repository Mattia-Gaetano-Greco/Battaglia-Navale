import java.net.*;

import Shared.Utilities;

import java.io.*;
  
public class TCPClient { 
  
  DataOutputStream os;
  DataInputStream is;
  Socket socket;

  public void start()throws IOException { 
    //Connessione della Socket con il Server 
    socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    os = new DataOutputStream(socket.getOutputStream()); 
    is = new DataInputStream(socket.getInputStream());
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    // input dimensione iniziale tabella
    os.writeUTF("GRID_SIZE:"+Utilities.getInputNumberLowerThan(stdIn, "Inserire la lunghezza del lato della griglia (max 10) : ", 11));

    // griglia
    String response = is.readUTF();
    if (Utilities.responseHeaderEquals(response, "GRID_REPR")){
      System.out.println(Utilities.getResponseValue(response));
    } else {
      closeConnectionError();
      return;
    }
    
    // richiesta delle navi (quante navi, dimensione ciascuna nave)
    response = is.readUTF();
    while (!Utilities.responseHeaderEquals(response, "END_SHIPS")) {
      // controlla se c'è stato un errore
      if (Utilities.responseHeaderEquals(response, "ERROR")) {
        System.out.println(Utilities.getResponseValue(response));
        closeConnection();
        return;
      } else if (Utilities.responseHeaderEquals(response, "GRID_REPR")) {
        System.out.println(Utilities.getResponseValue(response));
      } else if (Utilities.responseHeaderEquals(response, "SHIP_SIZE")) {
        System.out.println("Dimensione barca da inserire: "+Integer.parseInt(Utilities.getResponseValue(response)));
        os.writeUTF("INIT_ROW:"+Utilities.getInputNumber(stdIn, "Inserire la riga iniziale della nave: "));
        os.writeUTF("INIT_COL:"+Utilities.getInputNumber(stdIn, "Inserire la colonna iniziale della nave: "));
        os.writeUTF("INIT_ROT:"+Utilities.getInputNumberFromList(stdIn, "Inserire la rotazione della nave [Nord: 1; Est: 2; Sud: 3; Ovest: 4]: ", new int[]{1,2,3,4})); 
      } else if (Utilities.responseHeaderEquals(response, "BAD_PLACE")) {
        System.out.println("La barca non può essere posizionata con questi parametri!");
      } else {
        closeConnectionError();
        return;
      }
      response = is.readUTF();
    }

    // rappresentazione griglia con tutte le barche posizionate
    response = is.readUTF();
    if (Utilities.responseHeaderEquals(response, "GRID_REPR")) {
      System.out.println("\n\nGriglia completa:\n");
      System.out.println(Utilities.getResponseValue(response));
    } else {
      closeConnectionError();
      return;
    }

    // TODO ciclo di tentavi di affondaggio navi server
    boolean exit = true;
    while (exit) {
      exit = false;
    }
    
    //Chiusura dello Stream e del Socket 
    closeConnection();
  } 

  private void closeConnection() {
    try {
      socket.close();
      os.close();
      is.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  private void closeConnectionError() {
    System.out.println("Il programma ha riscontrato un problema. Termino l'esecuzione.");
    closeConnection();
  }

  public static void main (String[] args) throws Exception { 
    TCPClient tcpClient = new TCPClient(); 
    tcpClient.start(); 
  } 
} 
