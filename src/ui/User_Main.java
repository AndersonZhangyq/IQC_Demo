package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
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

import controller.User_info;
import controller.User_info.Frieds_Base_Info;

public class User_Main extends JFrame {

	/* user_info userName userSetName userState */
	class User_info_panel extends JPanel {
		String[] user_info;
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
					JOptionPane.showMessageDialog(getComponent(0), "点击成功", "点击事件",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
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
		};

		public User_info_panel(String[] user_info) {
			this.user_info = user_info.clone();
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel user_name = new JLabel(user_info[0]);
			JLabel user_set_name = new JLabel("(" + user_info[1] + ")");
			JLabel user_state = new JLabel(user_info[2]);
			this.add(user_name);
			this.add(user_set_name);
			this.add(user_state);
			this.add(user_state);
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

	JMenuBar head_menu;
	JMenu search_menu, settings_menu, logoff_menu;
	JScrollPane main_scrollpane;
	// GroupJButton[] Group_and_friends;
	// Box[] vBox;
	Box vBox_all;
	// int group_number;
	int friend_number;
	User_info user_info = User_info.getInstance();

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	/*
	 * public user_main_ui(int group_number, Vector[] friends_info) {
	 * this.group_number = group_number; this.friends_info = friends_info;
	 * Init(); setActions(); }
	 */

	public User_Main() {
		Init();
		setActions();
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

		main_scrollpane = new JScrollPane();

		initialize_ui();

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initialize_ui() {
		/*
		 * Group_and_friends = new GroupJButton[group_number]; for (int i = 0; i
		 * < group_number; i++){ vBox[i] = Box.createVerticalBox();
		 * Group_and_friends[i] = new GroupJButton();
		 * Group_and_friends[i].setText((String) friends_info.elementAt(0)); Box
		 * group_members_vBox = Box.createVerticalBox();
		 * 
		 * }
		 */
		HashMap<String, Frieds_Base_Info> friend_List = user_info.getfriendList();
		Iterator iterator = friend_List.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Entry) iterator.next();
			User_info_panel user_info_panel = new User_info_panel(new String[] { "  ", "  " });
			vBox_all.add(user_info_panel);
		}
		this.add(vBox_all);
	}

	private void setActions() {
	}

}
