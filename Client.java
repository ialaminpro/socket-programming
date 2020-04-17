// A Java program for a Client 
import java.net.*; 
import java.io.*; 

public class Client 
{ 
	// initialize socket and input output streams 
	private Socket socket		 = null; 
	private DataInputStream input = null; 
	private DataOutputStream out	 = null; 

	// constructor to put ip address and port 
	public Client(String address, int port) 
	{ 
		// establish a connection 
		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 
			System.out.println("Enter arithmetic operator"); 

			DataInputStream din=new DataInputStream(socket.getInputStream());  
			DataOutputStream dout=new DataOutputStream(socket.getOutputStream());  
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str="",str2=""; 
			int input;

			while(!str.equals("stop")){  
				// input=br.readLine();  
				str=br.readLine(); 
				// input = Integer.parseInt(str.trim()); 
				dout.writeUTF(str);  
				dout.flush();  
				str2=din.readUTF();  
				System.out.println(str2);  
				// System.out.println("Server says: "+str2);  
			}  
			  
			dout.close();  
			socket.close();

			
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Client client = new Client("127.0.0.1", 5005); 
	} 
} 

