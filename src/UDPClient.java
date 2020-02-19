/*

 * This program will be the client side to request quotes from server.

 * UDP Programming Project

 * CS415 - Data Communication and Data Networks.

 * @author Christina Porter & Stephen Love

 * Spring 2020

 * @version 1.0

 */


import java.io.*;
import java.net.*;
import java.util.*;


public class UDPClient {

	public static void main(String[] args) {

		int port = 2005;
		
		String command = "";
		
		do {

			try {
				
				Scanner input = new Scanner(System.in);
				
				System.out.println("Enter a command:");
				
				command = input.nextLine();
				
				if(command.equals("<REQUESTQUOTE>")) {
				
					InetAddress address = InetAddress.getLocalHost();
					DatagramSocket socket = new DatagramSocket();
		
		
					DatagramPacket request = new DatagramPacket(new byte[1], 1, address, port);
					socket.send(request);
		
					byte[] buffer = new byte[512];
					DatagramPacket response = new DatagramPacket(buffer, buffer.length);
		
					socket.receive(response);
		
					String quote = new String(buffer, 0, response.getLength());
		
					System.out.println(quote);
					System.out.println();
				}else if(command.equals("<END>")) {
					System.out.println("Client closed.");
				}else {
					System.out.println("Please enter <REQUESTQUOTE> to request a quote or <END> to close the client.");
				}//end else
		
				} catch (SocketTimeoutException ex) {
					System.out.println("Timeout error: " + ex.getMessage());
					ex.printStackTrace();
				} catch (IOException ex) {
					System.out.println("Client error: " + ex.getMessage());
					ex.printStackTrace();
				}//end catch
		}while(command.equals("<REQUESTQUOTE>"));

	}//end main

}//end UDPClient