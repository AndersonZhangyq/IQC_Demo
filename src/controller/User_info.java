package controller;

import java.util.HashMap;

public class User_info {
	
	public class Frieds_Base_Info{
		public String user_name;
		public String status;
	}
		
	private static class User_info_holder{
		private static final User_info INSTANCE = new User_info();
	}
	
	public String USER_ID = null;
	public HashMap<String, Frieds_Base_Info> friendList = null;
	
	User_info instance = null;

	private User_info() {
	}

	public static User_info getInstance() {
		return User_info_holder.INSTANCE;
	}
	
	public void setID(String id) {
		USER_ID = id;
	}
	
	public String getID(){
		return USER_ID;
	}
	
	public void setfrientList(HashMap<String, Frieds_Base_Info> friend_list_queried){
		friendList = friend_list_queried;
	}
	
	public HashMap<String, Frieds_Base_Info> getfriendList() {
		return friendList;
	}
	
	/* When user log off, it must be restored! */
	public void restore(){
		USER_ID = null;
	}
}
