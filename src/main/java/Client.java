import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public void startConnection(String ip, int port) {
            try {
                clientSocket = new Socket(ip, port);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public String sendMessage(String msg) {
            String resp = "";
            try {
                out.println(msg);
                resp = in.readLine();
            }catch (Exception e){
                e.printStackTrace();
            }
            return resp;
        }

        public void stopConnection() {
            try {
                in.close();
                out.close();
                clientSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void communicationWithServer() throws IOException {
            String msg = "";
            startConnection("localhost", 1515);
            
            System.out.println(in.readLine());
            Scanner sc = new Scanner(System.in);
            while (!(msg = sc.nextLine()).equals("QUIT")){
                System.out.println(sendMessage(msg));
            }
            stopConnection();
        }

}
