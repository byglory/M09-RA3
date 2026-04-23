
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private final int PORT = 7777;
    private final String HOST = "localhost";
    
    ServerSocket srvSocket = null;
    Socket clientSocket = null;
    public void connecta(){
        try {
            srvSocket = new ServerSocket(PORT);
            clientSocket = srvSocket.accept();
        } catch (Exception e) {
        }
    }
    public void repDades(){
        try {
            clientSocket = new Socket(HOST,PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String missatge = in.readLine();
            System.out.println("Rebut: " + missatge);
            in.close();
        } catch (Exception e) {
        }
        
    }
    public void tanca(){
        try {
            clientSocket.close();
            srvSocket.close();
        } catch (Exception e) {
        }
    }
    public static void main(String[] args) {
        Servidor srv = new Servidor();
        srv.connecta();
        srv.repDades();
        srv.tanca();
    }
    
    

}
