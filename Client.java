// File Name Client.java
import java.net.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

   public static void main(String [] args) {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);

         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);

         Scanner input = new Scanner(client.getInputStream());
         PrintWriter output = new PrintWriter(client.getOutputStream(), true);

         //Set up stream for keyboard entry
         Scanner userEntry = new Scanner(System.in);
         String opt;
         String firstInt, secondInt, answer;

         System.out.print("Please input the arithmetic operator: ");
         opt = userEntry.nextLine();

         System.out.print("Please input the first number: ");
         firstInt = userEntry.nextLine();

         System.out.print("Please input the second number: ");
         secondInt = userEntry.nextLine();

         //send the numbers
         output.println(opt);
         output.println(firstInt);
         output.println(secondInt);
         answer = input.nextLine(); //getting the answer from the server
         System.out.println("\nSERVER> " + answer);
         System.out.println(input.nextLine());
         System.out.println(input.nextLine());

         
         
         
         // out.writeUTF("Hello from " + client.getLocalSocketAddress());
         // InputStream inFromServer = client.getInputStream();
         // DataInputStream in = new DataInputStream(inFromServer);
         
         // System.out.println("Server says " + in.readUTF());
         input.close();
         output.close();
         client.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
