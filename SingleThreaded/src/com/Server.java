package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	
	public void run() throws IOException,UnknownHostException {
		int port = 8020;
		ServerSocket socket = new ServerSocket(port);
		socket.setSoTimeout(100000);
		
		while(true) {
			try {
				System.out.println("server is listening on "+port);
				Socket acceptedConnection = socket.accept();
				System.out.println("connection accepted from client "+acceptedConnection.getRemoteSocketAddress());
				PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
				BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
				toClient.println("hello from the server");
				toClient.close();
				fromClient.close();
				acceptedConnection.close();
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.run();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
