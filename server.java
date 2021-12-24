package qqchat;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;

public class server {
	ArrayList<Socket> socketList;
	ServerSocket serverSocket;
	Socket socket;
	public server()throws IOException{
		serverSocket=new ServerSocket(10124);
		socketList=new ArrayList<>();
		socket=null;
		while(true) {
			socket=serverSocket.accept();//循环监听
			System.out.println("Client connected to the server!");
			socketList.add(socket);
			new Listening(socket,socketList).start();
		}
	}
	
	public static void main(String[] args) throws IOException{
		new server();
	}
}
class Listening extends Thread{
	private Socket socket;
	private ArrayList<Socket> socketList;
	Listening(Socket socket,ArrayList<Socket> socketList){
		this.socket=socket;
		this.socketList=socketList;
	}
	@Override
	public void run() {
		DataInputStream in;
		DataOutputStream out;
		try {
			in=new DataInputStream(socket.getInputStream());
			String news;
			while(true) {
				news=in.readUTF();
				System.out.println("Client: "+news);
				for(int i=0;i<socketList.size();++i) {
					out=new DataOutputStream(socketList.get(i).getOutputStream());
					out.writeUTF(news);
					out.flush();
				}
			}
		}catch(IOException e) {
			//TODP:handle exception
		}
	}
}