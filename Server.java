// File Name Server.java
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.math.*;

public class Server extends Thread {
   private ServerSocket serverSocket;
   
   public static boolean isNumber(String num){
      try {  
         Double.parseDouble(num);  
         return true;
      } catch(NumberFormatException e){  
         return false;  
      }  
   }

   public Server(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      // serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());

            Scanner input = new Scanner(server.getInputStream());
            PrintWriter output = new PrintWriter(server.getOutputStream(), true);
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
            input.close();
            output.close();
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      try {
         Thread t = new Server(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}