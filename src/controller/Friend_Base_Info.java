package controller;

public class Friend_Base_Info {
	private String user_name;
	private String status;
	private String id;
	private String remark_name;

	public String getUserName() {
		return user_name;
	}

	public String getStatus() {
		return status;
	}
	public String getRemarkName() {
		return remark_name;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public Friend_Base_Info(String user_name, String status, String id, String remark_name) {
		this.id = id;
		this.user_name = user_name;
		this.status = status;
		this.remark_name = remark_name;
	}
}
