package ui;

import java.lang.management.ManagementFactory;

import controller.Chat_Info;
import defined_type.UI_code;
import ui.User_Main.Friend_info_panel;

public class Start_ui extends Thread implements UI_code {

	private int requested_id;
	public Chat_Info chat_Info;
	private Friend_info_panel origin;
	private boolean allow_auto_login = false;

	public void setChat_Info_JPanel(Chat_Info chat_Info, Friend_info_panel origin) {
		this.chat_Info = chat_Info;
		this.origin = origin;
	}

	public void setAllow_auto_login(boolean allow_auto_login) {
		this.allow_auto_login = allow_auto_login;
	}

	public Start_ui(int id) {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("Start_ui PID: " + name);
		requested_id = id;
	}

	public void start_ui() {
		switch (requested_id) {
		case UI_code.LOGIN_VIEW:
			new Login(allow_auto_login);
			allow_auto_login = false;
			break;
		case UI_code.REGISTER_VIEW:
			new Register();
			break;
		case UI_code.USER_MAIN_VIEW:
			new User_Main();
			break;
		case UI_code.CHAT_VIEW:
			new Chat(chat_Info, origin);
			break;
		case FORGET_PASSWORD_VIEW:
			new Forget_Password();
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {
		start_ui();
	}
}
