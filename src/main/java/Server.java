import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements a multi-threaded TCP server. It is able to interact
 * with several clients at the time, as well as to continue listening for
 * connection requests.
 *
 * @author Olivier Liechti
 */
public class Server {
	
	/**
	 * This method initiates the process. The server creates a socket and binds it
	 * to the previously specified port. It then waits for clients in a infinite
	 * loop. When a client arrives, the server will read its input line by line
	 * and send back the data converted to uppercase. This will continue until the
	 * client sends the "BYE" command.
	 */
	public void serveClients() {
		new Thread(new ReceptionistWorker()).start();
	}
	
	/**
	 * This inner class implements the behavior of the "receptionist", whose
	 * responsibility is to listen for incoming connection requests. As soon as a
	 * new client has arrived, the receptionist delegates the processing to a
	 * "servant" who will execute on its own thread.
	 */
	private class ReceptionistWorker implements Runnable {
		
		@Override
		public void run() {
			ServerSocket serverSocket;
			
			try {
				serverSocket = new ServerSocket(Protocol.DEFAULT_PORT);
			} catch (IOException ex) {
				return;
			}
			
			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					new Thread(new ServantWorker(clientSocket)).start();
				} catch (IOException ex) {
				
				}
			}
			
		}
		
		/**
		 * This inner class implements the behavior of the "servants", whose
		 * responsibility is to take care of clients once they have connected. This
		 * is where we implement the application protocol logic, i.e. where we read
		 * data sent by the client and where we generate the responses.
		 */
		private class ServantWorker implements Runnable {
			
			Socket clientSocket;
			BufferedReader in = null;
			PrintWriter out = null;
			
			public ServantWorker(Socket clientSocket) {
				try {
					this.clientSocket = clientSocket;
					in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					out = new PrintWriter(clientSocket.getOutputStream());
				} catch (IOException ex) {
				
				}
			}
			
			@Override
			public void run() {
				String line;
				boolean shouldRun = true;
				
				out.println("Welcome to the Calculator");
				
				out.print("Voici la liste de commande [");
				for(String s : Protocol.LIST_COMMANDS){
					out.print(s + ",");
				}
				out.print("]\n");
				out.flush();
				try {
					
					while ((shouldRun) && (line = in.readLine()) != null) {
						if (line.equalsIgnoreCase(Protocol.CMD_QUIT)) {
							shouldRun = false;
						}
						out.println("> " + line.toUpperCase());
						out.flush();
					}
					
					clientSocket.close();
					in.close();
					out.close();
					
				} catch (IOException ex) {
					if (in != null) {
						try {
							in.close();
						} catch (IOException ex1) {
						
						}
					}
					if (out != null) {
						out.close();
					}
					if (clientSocket != null) {
						try {
							clientSocket.close();
						} catch (IOException ex1) {
						
						}
					}
				}
			}
		}
	}
	
	public static void main(String... args){
		Server multi = new Server();
		multi.serveClients();
	}
}