package controller;

import java.util.Map;

public class User_info {
		
	private static class User_info_holder{
		private static final User_info INSTANCE = new User_info();
	}
	
	public String USER_ID = null;
	public Map<String, Friends_Base_Info> friendList = null;
	
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
	
	public void setfrientList(Map<String, Friends_Base_Info> map){
		friendList = map;
	}
	
	public Map<String, Friends_Base_Info> getfriendList() {
		return friendList;
	}
	
	/* When user log off, it must be restored! */
	public void restore(){
		USER_ID = null;
		friendList = null;
	}
}
