package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Message_Driver {
	private static class Message_Driver_holder {
		private static final Message_Driver INSTANCE = new Message_Driver();
	}

	private Message_Driver() {
		Server();
	}

	public static Message_Driver getInstance() {
		return Message_Driver_holder.INSTANCE;
	}

	static String IpAddress = "115.159.74.93";
	static int servPort = 830;
	private static int recvMsgSize;
	private static byte[] receiveBuf;

	class Server extends Thread {

		@Override
		public void run() {
			try {
				ServerSocket servSock = new ServerSocket(servPort);
				while (true) {
					// 2.调用accept方法，建立和客户端的连接
					Socket clntSock = servSock.accept();
					SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
					System.out.println("Handling client at " + clientAddress);

					// 3. 获取连接的InputStream,OutputStream来进行数据读写
					InputStream in;
					OutputStream out;
					in = clntSock.getInputStream();
					out = clntSock.getOutputStream();

					while ((recvMsgSize = in.read(receiveBuf)) != -1) {
						out.write(receiveBuf, 0, recvMsgSize);
					}
					// 4.操作结束，关闭socket.
					clntSock.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	public void Server() {
		new Server().start();
	}

	public void Communicate(byte[] buff) {
		try {
			ServerSocket servSock = new ServerSocket(servPort);
			Socket clntSock = servSock.accept();
			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();

			out.write(buff);// 把buff内容写入到输出流里
			in.read(buff);// 把输入流中的内容放到buff里
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
