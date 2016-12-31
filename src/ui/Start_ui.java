package ui;

import controller.Chat_Info;

public class Start_ui extends Thread implements UI_code{
	
	private int requested_id;
	public Chat_Info chat_Info;
	
	public void setChat_Info(Chat_Info chat_Info) {
		this.chat_Info = chat_Info;
	}

	public Start_ui(int id){
		requested_id = id;
	}
	
	public void start_ui(){
		switch (requested_id) {
		case UI_code.LOGIN_VIEW:
			new Login();
			break;
		case UI_code.REGISTER_VIEW:
			new Register();
			break;
		case UI_code.USER_MAIN_VIEW:
			new User_Main();
			break;
		case UI_code.CHAT_VIEW:
			new Chat(chat_Info);;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void run(){
		start_ui();
	}
}
