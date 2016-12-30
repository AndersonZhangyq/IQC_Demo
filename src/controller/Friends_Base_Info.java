package controller;

public class Friends_Base_Info {
	private String user_name;
	private String status;
	private String id;

	public String getUserName() {
		return user_name;
	}

	public String getStatus() {
		return status;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public Friends_Base_Info(String user_name, String status, String id) {
		this.id = id;
		this.user_name = user_name;
		this.status = status;
	}
}
