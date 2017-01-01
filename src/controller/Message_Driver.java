package controller;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import defined_type.Message_info;
import defined_type.User_info;

public class Message_Driver extends Thread {

	private String USER_ADDRESS = "127.0.0.1";
//	private final int Communication_Port = 8888;
	byte[] buff_receiver, buff_sender;
	DatagramSocket datagramSocket;
	DatagramPacket sender_Packet, receiver_Packet;
	String to_send, received;
	InetAddress address;
	Receiver receiver;
	JTextArea message_show;

	public Message_Driver(JTextArea message_show) {
		try {
			address = InetAddress.getByName(USER_ADDRESS);
			buff_receiver = new byte[10240];
			buff_sender = new byte[10240];
			datagramSocket = new DatagramSocket();
			this.message_show = message_show;
			start_receiving();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void start_receiving() {
		// TODO Auto-generated method stub
		try {
			receiver = new Receiver();
			receiver.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void sendMessage(String row_message) {
		to_send = row_message;
		new Sender().start();
	}

	class Sender extends Thread {

		@Override
		public void run() {
			try {
				Message_info message_info = new Message_info(
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
						User_info.getInstance().getUser_name(), to_send);
				Gson gson = new Gson();
				String message_jsoned = gson.toJson(message_info, Message_info.class);
				System.out.println("Message sent: " + message_jsoned);
				byte[] message_to_send = message_jsoned.getBytes();

				sender_Packet = new DatagramPacket(message_to_send, message_to_send.length, address,
						datagramSocket.getLocalPort());
				datagramSocket.send(sender_Packet);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	class Receiver extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					receiver_Packet = new DatagramPacket(buff_receiver, buff_receiver.length);
					datagramSocket.receive(receiver_Packet);
					buff_receiver = receiver_Packet.getData();
					String jsoned_message = new String(buff_receiver);
					System.out.println("Message received: " + jsoned_message);
					
					Gson gson = new Gson();
					JsonReader jsonReader = new JsonReader(new StringReader(jsoned_message));
					jsonReader.setLenient(true);
					Message_info arrived_message = gson.fromJson(jsonReader, Message_info.class);
					message_show.append(message_show.getText() + "\n" + arrived_message.getFullMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
