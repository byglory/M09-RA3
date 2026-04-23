
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
            socket = new Socket(HOST,PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        }

    }
    public void tanca(){
        try {
            out.close();
            socket.close();    
        } catch (Exception e) {
        }
        
    }
    public void envia(String missatge){
        if (out != null) {
            out.println(missatge);
        }
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.connecta();
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adeu!");
        client.reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            
            client.reader.readLine();
        } catch (IOException e) {
        }
        
        client.tanca();
    }

}
