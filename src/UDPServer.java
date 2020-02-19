/*
 * This program will be the server side that listens for requests from the client.
 * UDP Programming Project
 * CS415 - Data Communication and Data Networks.
 * @author Christina Porter & Stephen Love
 * Spring 2020
 * @version 1.0
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class UDPServer {
	
	private DatagramSocket socket;
	private ArrayList<String> quotesList = new ArrayList<String>();
	private Random randomInt;
	int portNumber = 2005;
	
	public UDPServer(int port) throws SocketException{
		
		port = portNumber;
		socket = new DatagramSocket(port);
		randomInt = new Random();
		
	}//end constructor
	
	private void dataExchange() throws IOException{
		
		while(true){
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("MM/dd/yyyy h:ma", Locale.US);
			
			DatagramPacket incomingRequest = new DatagramPacket(new byte[1],1);
			socket.receive(incomingRequest);
			
			System.out.println("Request received from " + incomingRequest.getAddress() + ": " + incomingRequest.getPort() + " "+ formatterDateTime.format(time));
			
			String quote = getQuote();
			byte[] buffer = quote.getBytes();
			
			InetAddress clientIPAddress = incomingRequest.getAddress();
			int clientPort = incomingRequest.getPort();
			
			DatagramPacket serverResponse = new DatagramPacket(buffer, buffer.length, clientIPAddress, clientPort);
			socket.send(serverResponse);
			
		}//end while
		
	}//end dataExchange
	
	private void readQuotes(String file) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String quote;
		
		while((quote = reader.readLine()) != null) {
			quotesList.add(quote);
		}//end while
		
	}//end readQuotes
	
	private String getQuote() {
		
		int random = randomInt.nextInt(quotesList.size());
		String quote = quotesList.get(random);
		return quote;
		
	}//end getQuote
	
	public static void main(String[] args) {
		
		LocalDateTime startTime = LocalDateTime.now();
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("h:ma", Locale.US);
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US);
		
		System.out.println("Server Started at "+ formatterTime.format(startTime)+ " on " + formatterDate.format(startTime));
		
			String file = "quote.csv";
			int port = 2005;
			
			try {
				UDPServer server = new UDPServer(port);
				server.readQuotes(file);
				server.dataExchange();
			} catch (SocketException ex) {
	            System.out.println("Socket error: " + ex.getMessage());
	        } catch (IOException ex) {
	            System.out.println("I/O error: " + ex.getMessage());
	        }//end catch
		}//end main

}//end UDPServer

