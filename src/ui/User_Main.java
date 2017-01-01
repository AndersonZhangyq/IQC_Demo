package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import controller.Chat_Info;
import controller.Friend_Base_Info;
import defined_type.UI_code;
import defined_type.User_info;
import url_request.UrlRequest;

public class User_Main extends JFrame implements UI_code {

	JMenuBar head_menu;
	JMenu search_menu, settings_menu, logoff_menu;
	JScrollPane main_scrollpane;
	Box vBox_all;
	int friend_number;
	User_info user_info = User_info.getInstance();
	boolean isshown = true;
	JPopupMenu right_clicked;

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public User_Main() {
		UrlRequest.Get_User_Friends();
		Init();
		setActions();
		setVisible(true);
	}

	private void Init() {
		head_menu = new JMenuBar();
		setJMenuBar(head_menu);

		search_menu = new JMenu("查找");
		settings_menu = new JMenu("设置");
		logoff_menu = new JMenu("注销");

		head_menu.add(search_menu);
		head_menu.add(settings_menu);
		head_menu.add(logoff_menu);
		vBox_all = Box.createVerticalBox();

		initialize_ui();

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		this.add(main_scrollpane);

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
					right_clicked = new JPopupMenu();
					JMenuItem show_nick_name = new JMenuItem("显示昵称");
					JMenuItem show_online_friends = new JMenuItem("显示在线好友");
					JMenuItem refresh = new JMenuItem("刷新好友列表");
					right_clicked.add(show_nick_name);
					right_clicked.add(show_online_friends);
					right_clicked.add(refresh);
					right_clicked.show(e.getComponent(), e.getX() - 5, e.getY() - 5);
				}

			}
		});
	}

	/* user_info userName userRemarkName userStatus */
	class Friend_info_panel extends JPanel {
		Friend_Base_Info friend_info;
		JLabel friend_user_name, friend_remark_name, friend_status;
		JPanel _this = this;
		boolean isshown = true;
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
					JPanel old = (JPanel) e.getSource();
					Start_ui chat_UI = new Start_ui(CHAT_VIEW);
					String friend_id = old.getName();
					String friend_name = user_info.getfriendList().get(friend_id).getUserName();
					String friend_ip = UrlRequest.Query_IP(friend_id);
					chat_UI.start();
					chat_UI.setChat_Info(new Chat_Info(user_info.getUser_name(), user_info.getID(), friend_name,
							friend_id, friend_ip));
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					right_clicked = new JPopupMenu();
					JMenuItem remark_name = new JMenuItem("设置昵称");
					JMenuItem show_nick_name = new JMenuItem("显示昵称");
					JMenuItem show_online_friends = new JMenuItem("显示在线好友");
					JMenuItem refresh = new JMenuItem("刷新好友列表");
					right_clicked.add(remark_name);
					right_clicked.add(show_nick_name);
					right_clicked.add(show_online_friends);
					right_clicked.add(refresh);
					right_clicked.show(e.getComponent(), e.getX() - 5, e.getY() - 5);
				}
			}
		};

		public Friend_info_panel(Friend_Base_Info friend_info) {
			this.friend_info = friend_info;
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			friend_user_name = new JLabel(friend_info.getUserName());
			this.add(friend_user_name);

			if (friend_info.getRemarkName() != null) {
				if (friend_info.getRemarkName().length() != 0) {
					friend_remark_name = new JLabel("(" + friend_info.getRemarkName() + ")");
					this.add(friend_remark_name);
				}
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
