import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientXat {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connecta() {
        try {
            // Obre el socket al servidor
            socket = new Socket(ServidorXat.HOST, ServidorXat.PORT);
            System.out.println("Client connectat a " + ServidorXat.HOST + ":" + ServidorXat.PORT);

            // Crea els streams d'entrada i sortida
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Flux d'entrada i sortida creat.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMissatge(String missatge) {
        try {
            out.writeObject(missatge);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tancarClient() {
        try {
            System.out.println("Tancant client...");
            if (socket != null) socket.close();
            System.out.println("Client tancat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientXat client = new ClientXat();
        
        // Connecta
        client.connecta();

        System.out.println("Fil de lectura iniciat");
        System.out.println("Escriu el teu nom: ");
        
        // Instancia i inicia el fil lector.
        // Nota: Passem el flux d'entrada (ObjectInputStream) per poder llegir missatges.
        FilLectorCX filLector = new FilLectorCX(client.in);
        filLector.start();

        Scanner scanner = new Scanner(System.in);
        
        // Llegir el nom per consola i enviar-lo
        String nom = scanner.nextLine();
        System.out.println("Enviant missatge: " + nom);
        client.enviarMissatge(nom);

        String missatge = "";
        
        // Envia missatges rebuts per consola fins a escriure "sortir"
        while (!missatge.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
            System.out.print("Missatge ('sortir' per tancar): \n");
            missatge = scanner.nextLine();
            System.out.println("Enviant missatge: " + missatge);
            client.enviarMissatge(missatge);
        }

        try {
            filLector.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Tanca Scanner i Client
        scanner.close();
        client.tancarClient();
    }
}