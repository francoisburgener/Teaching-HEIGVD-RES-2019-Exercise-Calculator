import java.io.*;

import static java.lang.Double.NaN;


public class Handler {
	
	private void help( PrintWriter writer){
		writer.print("Command : [");
		
		for(String cmd : Protocol.LIST_COMMANDS){
			writer.print(cmd + ",");
		}
		writer.println("]");
		writer.flush();
	}
	
	public void clientConnectionhandler(InputStream is, OutputStream os) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		
		String command;
		boolean done = false;
		
		writer.println("Welcome to our calcular ! ");
		writer.flush();
		
		while(!done && ((command = reader.readLine()) != null)){
			
			writer.print("> ");
			writer.flush();
			String[] cmd = command.split(" ");
			float number1 = 0;
			float number2 = 0;
			double calcule = 0;
			Operation op = new Operation();
			
			if(cmd.length == 3 && Integer.parseInt(cmd[1]) != NaN && Integer.parseInt(cmd[2]) != NaN  ){
				number1 = Integer.parseInt(cmd[1]);
				number2 = Integer.parseInt(cmd[2]);
			}
			
			switch (cmd[0].toUpperCase()){
				case Protocol.CMD_ADD :
					calcule = op.add(number1,number2);
					writer.println(Double.toString(calcule));
					writer.flush();
					break;
				case Protocol.CMD_SUB :
					calcule = op.sub(number1,number2);
					writer.println(Double.toString(calcule));
					writer.flush();
					break;
				case Protocol.CMD_DIV :
					if(number2 == 0){
						writer.println("Impossible divide per 0");
						writer.flush();
					}else {
						calcule = op.div(number1, number2);
						writer.println(Double.toString(calcule));
						writer.flush();
					}
					break;
				case Protocol.CMD_MUL :
					calcule = op.mul(number1,number2);
					writer.println(Double.toString(calcule));
					writer.flush();
					break;
				case Protocol.CMD_HELP :
					help(writer);
					break;
				case Protocol.CMD_QUIT :
					writer.println("Bye");
					writer.flush();
					done = true;
					break;
				default:
					writer.println("This command doesn't exist ! Please do the command help to know which command is possible");
					writer.flush();
					break;
			}
			writer.flush();
		}
	}
}
