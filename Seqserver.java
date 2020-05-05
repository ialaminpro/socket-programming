import java.util.*;
import java.net.*;
import java.io.*;
import java.math.*;
  

public class Seqserver
{
  public static boolean isNumber(String num){
      try {  
         Double.parseDouble(num);  
         return true;
      } catch(NumberFormatException e){  
         return false;  
      }  
   }

 public static void main(String[] args)
 {

    ServerSocket serverSocket = null;
    Socket server = null;
  	int port;
  	boolean listening = true; // assume serverSocket creation
                                    // was OK

	   // get port # from command-line

	   port = Integer.parseInt(args[0]);

        // try to create a server socket

    try
    {
        serverSocket = new ServerSocket(port);
    }
  	catch(IOException e)
  	{
  	    System.out.println(e);
  	    listening = false;
  	}

   if(listening) // i.e., serverSocket successfully created
   {
     // continue to:
     //
     //   (1) Listen for a client request
     //   (2) Read data from the client
     //   (3) Process the request: do calculation and return value
     //

     while(true) // main processing loop
     {
        try
      	{

              // Listen for a connection request from a client

              server = serverSocket.accept();
              System.out.println("Just connected to " + server.getRemoteSocketAddress());

              // Establish the input and output streams on the socket

              PrintWriter output = new
                           PrintWriter(server.getOutputStream(), true);
              Scanner input = new Scanner(new
      	           InputStreamReader(server.getInputStream()));
              
            String opt = input.nextLine();
            String firstNumber = input.nextLine();
            String secondNumber = input.nextLine();
            BigInteger answer;

            System.out.println("Your are selected "+ opt +" operation");
            System.out.println("Your first Number is "+firstNumber);
            System.out.println("Your second Number is "+secondNumber);


            if(isNumber(firstNumber)==false && isNumber(secondNumber) == false){
               output.println("Both values: not numbers");
            }else if(isNumber(firstNumber)==false){
               output.println("First value: not a number");
            }else if(isNumber(secondNumber)==false){
               output.println("Second value: not a number");
            }else{

               if(opt.equals("+") || opt.equals("-") || opt.equals("*") || opt.equals("/") || opt.equals("gcd")){
                  if(opt.equals("+")){

                     BigInteger i1 = new BigInteger(firstNumber);
                     BigInteger i2 = new BigInteger(secondNumber);
                     answer = i1.add(i2);
                     output.println("The sum is  "+ answer);

                  }else if(opt.equals("-")){

                     BigInteger i1 = new BigInteger(firstNumber);
                     BigInteger i2 = new BigInteger(secondNumber);
                     answer = i1.subtract(i2);
                     output.println("The subtract is  "+ answer);

                  }else if(opt.equals("*")){

                     BigInteger i1 = new BigInteger(firstNumber);
                     BigInteger i2 = new BigInteger(secondNumber);
                     answer = i1.multiply(i2);
                     output.println("The multiply is  "+ answer);

                  }else if(opt.equals("/")){

                     if(secondNumber.equals("0")){
                        output.println("Divide by 0"); 
                     }else{
                        BigInteger i1 = new BigInteger(firstNumber);
                        BigInteger i2 = new BigInteger(secondNumber);
                        answer = i1.divide(i2);
                        output.println("The divide is  "+ answer);
                     }
                  }else if(opt.equals("gcd")){

                     BigInteger i1 = new BigInteger(firstNumber);
                     BigInteger i2 = new BigInteger(secondNumber);
                     answer = i1.gcd(i2);
                     output.println("The gcd is  "+ answer);
                  }

               }else{
                  output.println("Unknown operation");  
               }
            }

         
            //System.out.println(in.readUTF());
            // DataOutputStream out = new DataOutputStream(server.getOutputStream());
            output.println("Thank you for connecting to " + server.getLocalSocketAddress());
            output.println("Goodbye!");

      	// Read data from the client, do calculation(s),
      	// return data value(s)

      	   

      	// close connection to client

                 output.close();
                 input.close();
                 server.close();

      	}
      	catch(IOException e)
      	{
      	   System.out.println(e);
      	}

     } // end while (main processing loop)

   } // end if listening

 } // end main

} // end seqserver