import java.net.*;
import java.io.*;
  
public class TCPClient { 
  public void start()throws IOException { 
    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    DataInputStream is = new DataInputStream(socket.getInputStream());
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    // input dimensione iniziale tabella
    os.writeUTF("GRID_SIZE:"+Utilities.getInputNumberLowerThan(stdIn, "Inserire la lunghezza del lato della griglia (max 10) : ", 11));

    // richiesta delle navi (quante navi, dimensione ciascuna nave)
    String resp = is.readUTF();
    String response[] = resp.split(":");
    int[] navi = null;
    // controlla se c'è stato un errore
    if (response[0].compareTo("ERROR")==0) {
      System.out.println(response[1]);
      os.close();
      is.close();
      socket.close();
      return;
    } else if (response[0].compareTo("SHIPS_NUMBER")==0) {
      navi = new int[Integer.parseInt(response[1])];
      for (int i = 0; i < navi.length; i++) {
        response = is.readUTF().split(":");
        if (response[0].compareTo("SHIP_SIZE")==0) {
          navi[i] = Integer.parseInt(response[1]);
        } else {
          System.out.println("Il programma ha riscontrato un problema. Termino l'esecuzione.");
          socket.close();
          os.close();
          is.close();
          return;
        }
      }
    } else {
      System.out.println("Il programma ha riscontrato un problema. Termino l'esecuzione.");
      socket.close();
      os.close();
      is.close();
      return;
    }

    // TODO inserimento navi all'interno della griglia

    // TODO invio posizione navi al server

    // TODO ciclo di tentavi di affondaggio navi server
    boolean exit = true;
    while (exit) {
      exit = false;
    }
    
    //Chiusura dello Stream e del Socket 
    os.close();
    is.close();
    socket.close(); 
  } 
  public static void main (String[] args) throws Exception { 
    TCPClient tcpClient = new TCPClient(); 
    tcpClient.start(); 
  } 
} 
