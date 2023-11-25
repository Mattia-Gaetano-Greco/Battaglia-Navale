//Importo i package
import java.net.*;
import java.io.*;

//Creazione di una classe per il Multrithreading
class ServerThread extends Thread {
  private Socket socket;
  public ServerThread (Socket socket) {
    this.socket = socket;
    System.out.println("  Stato    Tipo Richiesta  Porta Server  Porta Client  Indirizzo Cliernt\n");
  }

  //esecuzione del Thread sul Socket
  public void run() {
    try {
      BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      DataOutputStream os = new DataOutputStream(socket.getOutputStream());
      int n_tentativi = 1;
      int numero_da_indovinare = (int)(Math.random() * 100);
      while(n_tentativi <= 5) {
        String userInput = is.readLine();
        int numero_utente;
        try{
          numero_utente = Integer.parseInt(userInput);
          if (numero_utente == numero_da_indovinare) {
            os.writeBytes(n_tentativi+"\n");
            os.close();
            is.close();
          } else {
            if (numero_utente > numero_da_indovinare)
              os.writeBytes("Minore:"+n_tentativi+"\n");
            else
              os.writeBytes("Maggiore:"+n_tentativi+"\n");
            n_tentativi += 1;
          }
          System.out.println(socket.getInetAddress() +" "
          + socket.getPort() + " "
          + socket.getLocalPort() + " ha provato a indovinare il numero [" + numero_da_indovinare + "] con [" + userInput + "].");
        } catch (NumberFormatException e) {
          os.writeBytes("Error:Attenzione! Il valore inserito non è riconosciuto come un numero!\n");
        }
      }
      os.writeBytes("End:"+numero_da_indovinare+"\n");
      os.close();
      is.close();
      socket.close();
    }
    catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }
}