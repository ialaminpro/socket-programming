// A Java program for a Client 
import java.net.*; 
import java.io.*; 

public class Client 
{ 
	// initialize socket and input output streams 
	private Socket socket		 = null; 
	private BufferedReader input = null; 
	private DataOutputStream out = null; 
	private DataInputStream din = null;

	// constructor to put ip address and port 
	public Client(String address, int port) 
	{ 
		// establish a connection 
		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 
			 
			out = new DataOutputStream(socket.getOutputStream());  
			input = new BufferedReader(new InputStreamReader(System.in));
			din = new DataInputStream(socket.getInputStream());
		}
		catch(UnknownHostException u)
		{
			System.out.println(u);
		}
		catch(IOException i)
		{
			System.out.println(i);
		}

		System.out.println("Enter arithmetic operator"); 
		String line = "";
		String line2 = "";

		while(!line.equals("stop")){  
			try
			{
				line = input.readLine(); 
				out.writeUTF(line);  
				out.flush(); 
				line2 = din.readUTF(); 
				System.out.println(line2); 
				if(line2.contains("The") == true){
					System.out.println("Disconnected!");
				}
				 

			}
			catch(IOException i)
			{
				System.out.println(i);
			}  
		}
		// close the connection
		try
		{
			input.close();
			out.close();
			socket.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	} 

	public static void main(String args[]) 
	{ 
		Client client = new Client("127.0.0.1", 5031); 
	} 
} 

