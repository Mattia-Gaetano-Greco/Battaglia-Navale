//Importo i package
import java.net.*;

//Classe Server per attivare la Socket
public class TCPParallelServer {
  public void start() throws Exception {
    ServerSocket serverSocket = new ServerSocket(7777);

    //Ciclo infinito di ascolto dei Client
    boolean repeat = true;
    while(repeat) {
      System.out.println(" Attesa ");
      Socket socket = serverSocket.accept();
      System.out.println("Ricezione una chiamata di apertura da:\n" + socket);
      //avvia il processo per ogni client 
      ServerThread serverThread = new ServerThread(socket);
      serverThread.start();
    }
    serverSocket.close(); // solo per silenziare errore

  }

  public static void main (String[] args) throws Exception {
    TCPParallelServer tcpServer = new TCPParallelServer();
    tcpServer.start();
  }
}
