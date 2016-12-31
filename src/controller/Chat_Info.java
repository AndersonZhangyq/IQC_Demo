package controller;

public class Chat_Info {
	String user_from;
	String user_from_id;
	String user_to;
	String user_to_id;
	String to_ip;

	public String getTo_ip() {
		return to_ip;
	}

	public void setTo_ip(String to_ip) {
		this.to_ip = to_ip;
	}

	public Chat_Info(String user_from, String user_from_id, String user_to, String user_to_id, String to_ip) {
		// TODO Auto-generated constructor stub
		this.user_from = user_from;
		this.user_from_id = user_from_id;
		this.user_to = user_to;
		this.user_to_id = user_to_id;
		this.to_ip = to_ip;
	}

	public String getUser_from() {
		return user_from;
	}

	public String getUser_from_id() {
		return user_from_id;
	}

	public String getUser_to() {
		return user_to;
	}

	public String getUser_to_id() {
		return user_to_id;
	}
}
