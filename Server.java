// A Java program for a Server 
import java.net.*; 
import java.io.*; 

public class Server 
{ 
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null; 

	// constructor with port 
	public Server(int port) 
	{ 
		// starts server and waits for a connection 
		try
		{ 
			server = new ServerSocket(port); 
			System.out.println("Server started"); 

			System.out.println("Waiting for a client ..."); 

			socket = server.accept(); 
			System.out.println("Client accepted"); 

			DataInputStream din = new DataInputStream(socket.getInputStream());  
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());  
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 

			String str=""; 
			int firstNumber = 0;
			int secondNumber = 0;;
			int result = 0;
			String opt = "";
			int flag = 0;
			while(!str.equals("stop")){  

				str=din.readUTF(); 
				if(flag == 0){
					if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")){
						opt = str;
						flag = 1;
						dout.writeUTF("Enter First Number: ");  
						dout.flush(); 
					}else{
						flag = 0;
						dout.writeUTF("Enter arithmetic operator: ");  
						dout.flush(); 
					}
				}
				else if(flag == 1){
					firstNumber = Integer.parseInt(str.trim());
					dout.writeUTF("Enter Second Number: ");  
					dout.flush();
					flag++;
				}else{
					secondNumber = Integer.parseInt(str.trim());
					
					if(opt.equals("+")){
						result = firstNumber + secondNumber;
					}else if(opt.equals("-")){
						result = firstNumber - secondNumber;
					}else if(opt.equals("*")){
						result = firstNumber * secondNumber;
					}else if(opt.equals("/")){
						result = firstNumber / secondNumber;
					}
					 
					flag = 0;
					dout.writeUTF("Result = "+result);  
					dout.flush();  	
				}
				
				System.out.println(result);  
					// System.out.println("client says: "+input);  
					// str2=br.readLine();  
					// String res = Integer.toString(result);
					// dout.writeUTF("Result = ");  
					// dout.writeUTF(res);  
					// dout.flush(); 				 
			} 
		din.close();  
		socket.close();  
		server.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(5005); 
	} 
} 