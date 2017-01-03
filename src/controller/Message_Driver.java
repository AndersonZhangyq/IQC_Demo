package controller;

import java.io.IOException;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import defined_type.Message_info;
import defined_type.User_info;
import ui.Chat;
import ui.Chat.For_message;
import url_request.UrlRequest;

public class Message_Driver {

	private String USER_ADDRESS;
	private int USER_PORT = 8888;
	byte[] buff_receiver, buff_sender;
	Chat_Info from_Chat;
	DatagramPacket sender_Packet, receiver_Packet;
	String to_send, received;
	InetAddress address;
	Receiver receiver;
	For_message for_message;
	DatagramSocket datagramSocket;

	public Message_Driver(String to_ip, For_message in, Chat_Info from_chat) {
		USER_ADDRESS = to_ip;
		for_message = in;
		this.from_Chat = from_chat;
		try {
			String name = ManagementFactory.getRuntimeMXBean().getName();
			datagramSocket = new DatagramSocket(USER_PORT);
			datagramSocket.setReuseAddress(true);
			address = InetAddress.getByName(USER_ADDRESS);
			buff_receiver = new byte[10240];
			buff_sender = new byte[10240];
			UrlRequest.Update_Port(User_info.getInstance().getID(), datagramSocket.getLocalPort());
			start_receiving();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void start_receiving() {
		// TODO Auto-generated method stub
		try {
			receiver = new Receiver();
			receiver.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void sendMessage(String row_message) throws InterruptedException {
		to_send = row_message;
		new Sender().start();
	}

	public void exitReceiver() {
		receiver = null;
	}

	class Sender extends Thread {
		public Sender() {
			// TODO Auto-generated constructor stub

		}

		@Override
		public void run() {
			try {
				String send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				Message_info message_info = new Message_info(send_time,
						User_info.getInstance().getUser_name(), to_send);
				UrlRequest.sendMessage(from_Chat.getUser_from_id(), from_Chat.getUser_to_id(), to_send, send_time);
				Gson gson = new Gson();
				String message_jsoned = gson.toJson(message_info, Message_info.class);
				byte[] message_to_send = message_jsoned.getBytes();

				sender_Packet = new DatagramPacket(message_to_send, message_to_send.length, address,
						datagramSocket.getLocalPort());
				datagramSocket.send(sender_Packet);
				for_message.appendMessage(message_info.getFullMessage());
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

					Gson gson = new Gson();
					JsonReader jsonReader = new JsonReader(new StringReader(jsoned_message));
					jsonReader.setLenient(true);
					Message_info arrived_message = gson.fromJson(jsonReader, Message_info.class);
					for_message.appendMessage(arrived_message.getFullMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
