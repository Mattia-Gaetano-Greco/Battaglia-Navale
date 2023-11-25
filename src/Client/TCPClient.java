//Importo i package necessari 
import java.net.*;
import java.io.*; 
  
public class TCPClient { 
  public void start()throws IOException { 
    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    //Ciclo infinito per inserimento testo del Client
    boolean exit = true;
    while (exit) {
      System.out.print("Inserisci un numero [0 - 100]: "); 
      String userInput = stdIn.readLine();
      os.writeBytes(userInput + '\n');
      String result = is.readLine();
      try{
        int n_tentativi = Integer.parseInt(result);
        System.out.println("Hai indovinato il numero in "+n_tentativi+" tentativi");
        exit = false;
      } catch (NumberFormatException e) {
        String[] split = result.split(":");
        if (split[0].compareTo("Error") == 0)
          System.out.println(split[1]);
        else {
          System.out.println("Tentativo "+split[1]+": "+split[0]);
          if (split[1].compareTo("5") == 0) {
            String[] end_split = is.readLine().split(":");
            System.out.println("Non hai indovinato, il numero da indovinare era "+end_split[1]);
            exit = false;
          }
        }
      }
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
