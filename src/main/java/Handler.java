import java.io.*;

public class Handler {
	
	public void clientConnectionhandler(InputStream is, OutputStream os) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		
		String command;
		boolean done = false;
		
		while(!done && ((command = reader.readLine()) != null)){
			String[] cmd = command.split(" ");
			
			switch (cmd[0]){
				case Protocol.CMD_ADD :
					break;
				case Protocol.CMD_SUB :
					break;
				case Protocol.CMD_DIV :
					break;
				case Protocol.CMD_MUL :
					break;
				case Protocol.CMD_HELP :
					break;
				case Protocol.CMD_QUIT :
					break;
				default:
					break;
			}
		}
	}
}
