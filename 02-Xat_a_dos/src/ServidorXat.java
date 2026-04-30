import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorXat {
    public static final int PORT = 9999;
    public static final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void iniciarServidor() {
        try {
            // Obre la connexió amb ServerSocket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciat a " + HOST + ":" + PORT);

            // Accepta la connexió
            clientSocket = serverSocket.accept();
            System.out.println("Client connectat: /" + clientSocket.getInetAddress().getHostAddress());

            // Crea els Streams
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Obtenir el nom del client
            String nomClient = getNom();

            System.out.println("Fil de xat creat.");
            
            // Instancia i inicia el fil
            FilServidorXat fil = new FilServidorXat(in, nomClient);
            System.out.println("Fil de " + nomClient + " iniciat");
            fil.start();

            // Envia missatges des de la consola
            Scanner scanner = new Scanner(System.in);
            String missatge = "";

            while (!missatge.equalsIgnoreCase(MSG_SORTIR)) {
                missatge = scanner.nextLine();
                out.writeObject(missatge);
                out.flush();
            }

            // Espera al fil i tanca connexions
            fil.join();
            scanner.close();
            pararServidor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pararServidor() {
        try {
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
            System.out.println("Servidor aturat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNom() {
        try {
            String nom = (String) in.readObject();
            System.out.println("Nom rebut: " + nom);
            return nom;
        } catch (Exception e) {
            e.printStackTrace();
            return "Desconegut";
        }
    }

    public static void main(String[] args) {
        ServidorXat servidor = new ServidorXat();
        servidor.iniciarServidor();
    }
}