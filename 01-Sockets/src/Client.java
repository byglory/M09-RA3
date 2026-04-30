import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int PORT = 7777;
    private final String HOST = "localhost";
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader reader = null;

    public void connecta(){
        try {
            socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connectat a servidor en " + HOST + ":" + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tanca(){
        try {
            if (out != null) out.close();
            if (socket != null) socket.close();    
            if (reader != null) reader.close();
            System.out.println("Client tancat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void envia(String missatge){
        if (out != null) {
            out.println(missatge);
            System.out.println("Enviat al servidor: " + missatge);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connecta();
        
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adéu!");
        
        System.out.println("Prem Enter per tancar el client...");
        client.reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Esperem que l'usuari premi ENTER
            client.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        client.tanca();
    }
}