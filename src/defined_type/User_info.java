package defined_type;

import java.util.Map;

import controller.Friend_Base_Info;

public class User_info {
		
	private static class User_info_holder{
		private static final User_info INSTANCE = new User_info();
	}
	
	public String user_id;
	public String user_name;

	public Map<String, Friend_Base_Info> friendList = null;

	private User_info() {
	}

	public static User_info getInstance() {
		return User_info_holder.INSTANCE;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public void setID(String id) {
		user_id = id;
	}
	
	public String getID(){
		return user_id;
	}
	
	public void setfrientList(Map<String, Friend_Base_Info> map){
		friendList = map;
	}
	
	public Map<String, Friend_Base_Info> getfriendList() {
		return friendList;
	}
	
	/* When user log off, it must be restored! */
	public void restore(){
		user_id = null;
		friendList = null;
	}
}
