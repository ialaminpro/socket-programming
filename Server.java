// A Java program for a Server 
import java.net.*; 
import java.io.*; 
import java.math.*;

public class Server 
{ 
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null; 

	public static boolean isNumber(String num){
		try {  
	    	Double.parseDouble(num);  
	    	return true;
	  	} catch(NumberFormatException e){  
	    	return false;  
	  	}  
	}

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
			String firstNumber = "";
			String secondNumber = "";
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
						dout.writeUTF("Unknown operation");  
						dout.flush(); 
					}
				}
				else if(flag == 1){
					firstNumber = str;
					// firstNumber = Integer.parseInt(str.trim());
					if(isNumber(firstNumber)==false){
						dout.writeUTF("First value: not a number");  
						dout.flush();
						flag++;
					}else{
						
						dout.writeUTF("Enter Second Number: ");  
						dout.flush();
						flag++;
					}
					
				}else{
					secondNumber = str;
					// secondNumber = Integer.parseInt(str.trim());
					if(isNumber(firstNumber)==false && isNumber(secondNumber) == false){
						dout.writeUTF("Both values: not numbers");  
						dout.writeUTF("Enter First Number: ");
						dout.flush();
						flag = 1;
					}
					else if(isNumber(secondNumber) == false){
						dout.writeUTF("Second value: not a number");  
						dout.flush();
						flag++;
					}else{
						
						flag++;
						if(opt.equals("+")){
							BigInteger i1 = new BigInteger(firstNumber);
							BigInteger i2 = new BigInteger(secondNumber);
							BigInteger result;
							result = i1.add(i2);
							flag = 0;
							dout.writeUTF("The sum is  "+result);  
							dout.flush();

						}else if(opt.equals("-")){
							BigInteger i1 = new BigInteger(firstNumber);
							BigInteger i2 = new BigInteger(secondNumber);
							BigInteger result;
							result = i1.subtract(i2);
							flag = 0;
							dout.writeUTF("The subtract is  "+result);  
							dout.flush();
						}else if(opt.equals("*")){
							BigInteger i1 = new BigInteger(firstNumber);
							BigInteger i2 = new BigInteger(secondNumber);
							BigInteger result;
							result = i1.multiply(i2);
							flag = 0;
							dout.writeUTF("The multiply is  "+result);  
							dout.flush();
						}else if(opt.equals("/")){
							if(secondNumber.equals("0")){
								dout.writeUTF("Divide by 0");  
								dout.flush();
							}else{
								BigInteger i1 = new BigInteger(firstNumber);
								BigInteger i2 = new BigInteger(secondNumber);
								BigInteger result;
								result = i1.divide(i2);
								flag = 0;
								dout.writeUTF("The divide is  "+result);  
								dout.flush();
							}
							
						}
						 
						  
					}	
				}
				
				// System.out.println(result);  
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