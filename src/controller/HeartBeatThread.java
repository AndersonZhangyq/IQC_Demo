package controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class HeartBeatThread extends Thread {
	private final String REMOTE_SERVER_ADDRESS = "115.159.74.93";
	private final int SERVER_PORT = 8888;
	private final int TIMER_LIMIT = 6000;
	private boolean isReceived = false;

	public HeartBeatThread() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stu
		try {
			InetAddress remote_server = InetAddress.getByName(REMOTE_SERVER_ADDRESS);
			// Bind the port
			ServerSocket listening = new ServerSocket(SERVER_PORT);
			Socket hearBeat = new Socket(remote_server, SERVER_PORT);
			OutputStream out = hearBeat.getOutputStream();
			InputStream in = hearBeat.getInputStream();
			out.write(new byte[]{00});
			while (true) {
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (isReceived)
							return;
						else
							System.out.println(0000);
					}
				}, TIMER_LIMIT);
				while (in.read() == -1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args){
		new JFrame().setVisible(true);
		new HeartBeatThread().start();
	}
}
