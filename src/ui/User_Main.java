package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import controller.Chat_Info;
import controller.Friend_Base_Info;
import defined_type.Error_code;
import defined_type.UI_code;
import defined_type.User_info;
import url_request.UrlRequest;

public class User_Main extends JFrame implements UI_code {
	
	private String version_serial = "1.0";

	private final String SET_REMARK_NAME = "设置昵称";
	private final String REFRESH_FRIEND_LIST = "刷新好友列表";
	private final String SHOW_REMARK_NAME = "显示昵称";
	private final String SHOW_REMAIK_NAME_AND_USER_NAME = "显示昵称和用户名";

	JFrame _this;
	JMenuBar head_menu;
	JMenu search_menu, logoff_menu,about_menu;
	JScrollPane main_scrollpane;
	Box vBox_all;
	int friend_number;
	User_info user_info = User_info.getInstance();
	boolean show_user_name = true;
	JPopupMenu right_clicked_friend_panel, right_clicked_scrollpanel;

	JMenuItem show_nick_name_1, refresh_1;

	JMenuItem show_nick_name_2, refresh_2, remark_name;

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public User_Main() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("User_Main PID: " + name);
		UrlRequest.Get_User_Friends();
		Init();
		setActions();
		setVisible(true);
	}

	private void Init() {
		_this = this;

		head_menu = new JMenuBar();
		setJMenuBar(head_menu);

		search_menu = new JMenu("查找");
		logoff_menu = new JMenu("注销");
		about_menu = new JMenu("关于");

		head_menu.add(search_menu);
		head_menu.add(logoff_menu);
		head_menu.add(about_menu);
		vBox_all = Box.createVerticalBox();

		right_clicked_friend_panel = new JPopupMenu();
		show_nick_name_1 = new JMenuItem(SHOW_REMARK_NAME);
		refresh_1 = new JMenuItem(REFRESH_FRIEND_LIST);
		remark_name = new JMenuItem(SET_REMARK_NAME);
		right_clicked_friend_panel.add(remark_name);
		right_clicked_friend_panel.add(show_nick_name_1);
		right_clicked_friend_panel.add(refresh_1);

		right_clicked_scrollpanel = new JPopupMenu();
		show_nick_name_2 = new JMenuItem(SHOW_REMARK_NAME);
		refresh_2 = new JMenuItem(REFRESH_FRIEND_LIST);
		right_clicked_scrollpanel.add(show_nick_name_2);
		right_clicked_scrollpanel.add(refresh_2);

		initialize_ui();

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("主界面");
	}

	private void initialize_ui() {
		Map<String, Friend_Base_Info> friend_List = user_info.getfriendList();
		Iterator iterator = friend_List.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Entry) iterator.next();
			Friend_info_panel user_info_panel = new Friend_info_panel((Friend_Base_Info) entry.getValue());
			user_info_panel.setMaximumSize(new Dimension(32767, 40));
			user_info_panel.setPreferredSize(new Dimension(200, 40));
			vBox_all.add(user_info_panel);
		}
		main_scrollpane = new JScrollPane(vBox_all);
		this.getContentPane().add(main_scrollpane);
	}

	private void setActions() {
		main_scrollpane.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					right_clicked_scrollpanel.show(e.getComponent(), e.getX() - 5, e.getY() - 5);
				}

			}
		});

		MouseListener menu_mouse_listener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == logoff_menu) {
					int button_selected = JOptionPane.showConfirmDialog(_this, "是否退出程序？", "退出提示",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (button_selected == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else if (button_selected == JOptionPane.NO_OPTION) {
						User_info.getInstance().restore();
						UrlRequest.Logout();
						dispose();
						new Start_ui(LOGIN_VIEW).start();
					}
				}else if (e.getSource() == search_menu){
					JOptionPane.showMessageDialog(_this, "功能实现中，敬请期待.......", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}else if (e.getSource() == about_menu){
					JOptionPane.showMessageDialog(_this, "软件名：IQC\n作者：张逸清\n版本号：" + version_serial, "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};

		ActionListener popMenu_listener = new ActionListener() {

			public void showFriendList() {
				UrlRequest.Get_User_Friends();
				Map<String, Friend_Base_Info> friend_List = user_info.getfriendList();
				Iterator iterator = friend_List.entrySet().iterator();
				vBox_all.removeAll();
				while (iterator.hasNext()) {
					Map.Entry entry = (Entry) iterator.next();
					Friend_info_panel user_info_panel = new Friend_info_panel((Friend_Base_Info) entry.getValue());
					user_info_panel.setMaximumSize(new Dimension(32767, 40));
					user_info_panel.setPreferredSize(new Dimension(200, 40));
					vBox_all.add(user_info_panel);
				}
				_this.getContentPane().removeAll();
				main_scrollpane.setViewportView(vBox_all);
				_this.add(main_scrollpane);
//				setActions();
				_this.getContentPane().revalidate();
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String actionCommand = e.getActionCommand();
				if (actionCommand.contains(SET_REMARK_NAME)) {
					String result = JOptionPane.showInputDialog(_this, "请输入备注名");
					if (result == null)
						return;
					if (result.isEmpty() || result.trim().isEmpty()) {
						JOptionPane.showMessageDialog(_this, "输入的昵称不合法。\n昵称不能为空或只有空格！");
						return;
					}
					if (UrlRequest.Update_Remark_Name(User_info.getInstance().getID(), actionCommand.split(":")[1],
							result) == Error_code.Success) {
						JOptionPane.showMessageDialog(_this, "设置成功！", "服务器信息", JOptionPane.INFORMATION_MESSAGE);
						showFriendList();
						return;
					} else {
						JOptionPane.showMessageDialog(_this, "设置失败！", "服务器信息", JOptionPane.WARNING_MESSAGE);
					}
				}
				switch (actionCommand) {
				case REFRESH_FRIEND_LIST:
					showFriendList();
					break;
				case SHOW_REMARK_NAME:
					show_user_name = false;
					show_nick_name_1.setText(SHOW_REMAIK_NAME_AND_USER_NAME);
					show_nick_name_2.setText(SHOW_REMAIK_NAME_AND_USER_NAME);
					showFriendList();
					break;
				case SHOW_REMAIK_NAME_AND_USER_NAME:
					show_user_name = true;
					show_nick_name_1.setText(SHOW_REMARK_NAME);
					show_nick_name_2.setText(SHOW_REMARK_NAME);
					showFriendList();
				default:
					break;
				}
			}
		};

		show_nick_name_1.addActionListener(popMenu_listener);
		refresh_1.addActionListener(popMenu_listener);
		show_nick_name_2.addActionListener(popMenu_listener);
		refresh_2.addActionListener(popMenu_listener);
		remark_name.addActionListener(popMenu_listener);

		logoff_menu.addMouseListener(menu_mouse_listener);
		
		search_menu.addMouseListener(menu_mouse_listener);
		about_menu.addMouseListener(menu_mouse_listener);

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int answer = JOptionPane.showConfirmDialog(_this, "确认退出程序？", "退出确认", JOptionPane.WARNING_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					if (UrlRequest.Logout() == "DONE") {
						dispose();
						System.exit(0);
					}
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/* user_info userName userRemarkName userStatus */
	class Friend_info_panel extends JPanel {
		Friend_Base_Info friend_info;
		JLabel friend_user_name, friend_remark_name, friend_status;
		Friend_info_panel _this = this;
		boolean isshown = false;
		JPopupMenu right_clicked;
		MouseMotionListener change_background = new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				_this.setBackground(new Color(168, 233, 240));
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			}
		};
		MouseListener show_chat_frame = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				_this.setBackground(null);
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Show chat frame
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (isshown) {
						JOptionPane.showMessageDialog(_this, "聊天窗口已打开！", "提示", JOptionPane.ERROR_MESSAGE);
						return;
					}
					isshown = true;
					JPanel old = (JPanel) e.getSource();
					Start_ui chat_UI = new Start_ui(CHAT_VIEW);
					String friend_id = old.getName();
					String friend_name = user_info.getfriendList().get(friend_id).getUserName();
					Map<String, String> friend_ip_port = UrlRequest.Query_IP_Port(friend_id);
					chat_UI.setChat_Info_JPanel(
							new Chat_Info(user_info.getUser_name(), user_info.getID(), friend_name, friend_id,
									friend_ip_port.get("ip"), friend_ip_port.get("port"), friend_info.getNick_name()),
							_this);
					chat_UI.start();
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					remark_name.setActionCommand(SET_REMARK_NAME + ":" + friend_info.getID());
					right_clicked_friend_panel.show(e.getComponent(), e.getX() - 5, e.getY() - 5);
				}
			}
		};

		public void setUnshown() {
			isshown = false;
		}

		public Friend_info_panel(Friend_Base_Info friend_info) {
			this.friend_info = friend_info;
			this.setLayout(new FlowLayout(FlowLayout.LEFT));

			if (friend_info.getRemarkName() != null) {
				if (show_user_name) {
					friend_user_name = new JLabel(friend_info.getNick_name());
					this.add(friend_user_name);
				}
				if (friend_info.getRemarkName().length() != 0) {
					friend_remark_name = new JLabel("(" + friend_info.getRemarkName() + ")");
					this.add(friend_remark_name);
				}
			} else {
				friend_user_name = new JLabel(friend_info.getNick_name());
				this.add(friend_user_name);
			}

			friend_status = new JLabel(friend_info.getStatus());
			this.setName(friend_info.getID());
			this.add(friend_status);
			this.addMouseListener(show_chat_frame);
			this.addMouseMotionListener(change_background);
		}
	}

	class GroupJButton extends JButton implements ActionListener {
		boolean isClosed;

		public GroupJButton() {
			super();
			isClosed = true;
			this.setIcon(new ImageIcon("/icon/group_closed.png"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isClosed) {
				isClosed = false;
				this.setIcon(new ImageIcon("/icon/group_opened.png"));
			} else {
				isClosed = true;
				this.setIcon(new ImageIcon("/icon/group_closed.png"));
			}
		}
	}
}
