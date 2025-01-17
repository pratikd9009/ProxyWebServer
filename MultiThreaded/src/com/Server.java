package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

	public Consumer<Socket> getConsumer(){
		return (clientSocket)->{
			try {
				PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
				toClient.println("hello from the server");
				toClient.close();
				clientSocket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		};
	}

	public static void main(String[] args) {
		
		int port = 8030;
		Server server = new Server();
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);
			System.out.println("Server is listening on port "+port);
			while(true) {
				Socket acceptedConnection = serverSocket.accept();
				System.out.println("connection accepted from client "+acceptedConnection.getRemoteSocketAddress());
				Thread thread = new Thread(()->server.getConsumer().accept(acceptedConnection));
				thread.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

}
