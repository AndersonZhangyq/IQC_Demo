package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import defined_type.UI_code;
import defined_type.User_info;
import sun.misc.BASE64Encoder;
import url_request.UrlRequest;

public class Register extends JFrame implements UI_code {

	JLabel user_name_label, nick_name_label, password_label, password_to_confirm_label, go_back;
	JTextField user_name_text, nick_name_text;
	JPasswordField password_text, password_to_confirm_text;
	JLabel user_name_info_label, nick_name_info_label, password_info_label, password_to_confirm_info_label;
	JButton register_button;
	JFrame _this;
	JPanel top_panel;
	String user_name, password, nick_name, password_to_comfirm, encrypted_password;
	boolean user_name_set, password_set, password_to_confirm_set, nick_name_set;
	
	User_info user_info = User_info.getInstance();

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public Register() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("Register PID: " + name);
		Init();
		setActions();
		setVisible(true);
	}

	private void Init() {

		_this = this;

		go_back = new JLabel("返回登录界面");
		user_name_set = password_set = password_to_confirm_set = nick_name_set = false;
		user_name_label = new JLabel("用户名：");
		nick_name_label = new JLabel("昵称：");
		password_label = new JLabel("密码：");
		password_to_confirm_label = new JLabel("确认密码：");
		user_name_info_label = new JLabel("");
		nick_name_info_label = new JLabel("");
		password_info_label = new JLabel("");
		password_to_confirm_info_label = new JLabel("");
		top_panel = new JPanel();

		user_name_text = new JTextField(10);
		nick_name_text = new JTextField(10);
		password_text = new JPasswordField(10);
		password_to_confirm_text = new JPasswordField(10);

		register_button = new JButton("注册");
		register_button.setEnabled(false);

		user_name_info_label.setForeground(Color.RED);
		nick_name_info_label.setForeground(Color.RED);
		password_info_label.setForeground(Color.red);
		password_to_confirm_info_label.setForeground(Color.RED);

		initialize_ui();

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setTitle("注册");

	}

	private void setActions() {
		register_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Send information to SQL and make a new user
				try {
					encrypted_password = new BASE64Encoder()
							.encode(MessageDigest.getInstance("MD5").digest(password.getBytes("utf-8")));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				switch (UrlRequest.Check_Register(user_name, encrypted_password, nick_name)) {
				case Success:
					JOptionPane.showMessageDialog(_this.getContentPane(), "注册成功\n按下确定后将自动登录！", "注册信息",
							JOptionPane.INFORMATION_MESSAGE);
					user_info.setUser_name(user_name);
					_this.dispose();
					new Start_ui(USER_MAIN_VIEW).start();
					break;
				case Duplicate_Error:
					JOptionPane.showMessageDialog(_this.getContentPane(), "注册失败\n该用户名已被使用！", "注册信息",
							JOptionPane.WARNING_MESSAGE);
					break;
				default:
					JOptionPane.showMessageDialog(_this.getContentPane(), "注册失败\n请稍后重试！", "注册信息",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		FocusListener showInfo = new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				Object source = arg0.getSource();
				if (source == user_name_text) {
					user_name = user_name_text.getText();
					user_name_info_label.setText("");
					if (user_name.isEmpty()) {
						user_name_info_label.setText("请输入用户名！");
						return;
					}
					String emailReg = "(([a-zA-Z]?[0-9]+)|([a-zA-Z]+[0-9]?))@([a-zA-z0-9]{1,}.){1,3}[a-zA-z]{1,}";
					Matcher email_match = Pattern.compile(emailReg).matcher(user_name);
					if (!email_match.find()) {
						user_name_info_label.setText("用户名格式错误！");
					}
					user_name_set = true;
				} else if (source == password_text) {
					password = String.valueOf(password_text.getPassword());
					password_info_label.setText("");
					if (password.isEmpty()) {
						password_info_label.setText("请输入密码");
						return;
					}
					if (password.length() < 6) {
						password_info_label.setText("密码长度至少为6位！");
						return;
					}
					password_set = true;
				} else if (source == password_to_confirm_text) {
					password_to_comfirm = String.valueOf(password_to_confirm_text.getPassword());
					password_to_confirm_info_label.setText("");
					if (password_to_comfirm.isEmpty()) {
						password_to_confirm_info_label.setText("请再次输入密码！");
						return;
					}
					if (!password_to_comfirm.equals(password)) {
						password_to_confirm_info_label.setText("两次密码不一致！");
						return;
					}
					password_to_confirm_set = true;
				} else if (source == nick_name_text) {
					nick_name = nick_name_text.getText();
					nick_name_info_label.setText("");
					if (nick_name.isEmpty()) {
						nick_name_info_label.setText("请输入昵称！");
						return;
					}
					if (nick_name.trim().length() == 0) {
						nick_name_info_label.setText("昵称不能全为空格！");
						return;
					}
					nick_name_set = true;
				}
				if (user_name_set && password_set && password_to_confirm_set && nick_name_set)
					register_button.setEnabled(true);
				return;
			}

			@Override
			public void focusGained(FocusEvent arg0) {

			}
		};
		go_back.addMouseListener(new MouseListener() {
			
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
				new Start_ui(LOGIN_VIEW).start();
			}
		});
		user_name_text.addFocusListener(showInfo);
		password_text.addFocusListener(showInfo);
		password_to_confirm_text.addFocusListener(showInfo);
		nick_name_text.addFocusListener(showInfo);
	}

	private void initialize_ui() {
		this.add(top_panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 100, 180, 145, 0 };
		gbl_panel_1.rowHeights = new int[] { 35, 35, 35, 35, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		top_panel.setLayout(gbl_panel_1);
		user_name_label = new JLabel("用户名：");
		user_name_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_user_name_label = new GridBagConstraints();
		gbc_user_name_label.anchor = GridBagConstraints.EAST;
		gbc_user_name_label.fill = GridBagConstraints.VERTICAL;
		gbc_user_name_label.insets = new Insets(0, 0, 5, 5);
		gbc_user_name_label.gridx = 0;
		gbc_user_name_label.gridy = 0;
		top_panel.add(user_name_label, gbc_user_name_label);

		user_name_text = new JTextField(10);
		GridBagConstraints gbc_user_name_text = new GridBagConstraints();
		gbc_user_name_text.fill = GridBagConstraints.BOTH;
		gbc_user_name_text.insets = new Insets(0, 0, 5, 5);
		gbc_user_name_text.gridx = 1;
		gbc_user_name_text.gridy = 0;
		top_panel.add(user_name_text, gbc_user_name_text);
		user_name_info_label = new JLabel("");
		GridBagConstraints gbc_user_name_info_label = new GridBagConstraints();
		gbc_user_name_info_label.fill = GridBagConstraints.BOTH;
		gbc_user_name_info_label.insets = new Insets(0, 0, 5, 0);
		gbc_user_name_info_label.gridx = 2;
		gbc_user_name_info_label.gridy = 0;
		top_panel.add(user_name_info_label, gbc_user_name_info_label);

		user_name_info_label.setForeground(Color.RED);
		nick_name_label = new JLabel("昵称：");
		GridBagConstraints gbc_nick_name_label = new GridBagConstraints();
		gbc_nick_name_label.anchor = GridBagConstraints.EAST;
		gbc_nick_name_label.fill = GridBagConstraints.VERTICAL;
		gbc_nick_name_label.insets = new Insets(0, 0, 5, 5);
		gbc_nick_name_label.gridx = 0;
		gbc_nick_name_label.gridy = 1;
		top_panel.add(nick_name_label, gbc_nick_name_label);
		nick_name_text = new JTextField(10);
		GridBagConstraints gbc_nick_name_text = new GridBagConstraints();
		gbc_nick_name_text.fill = GridBagConstraints.BOTH;
		gbc_nick_name_text.insets = new Insets(0, 0, 5, 5);
		gbc_nick_name_text.gridx = 1;
		gbc_nick_name_text.gridy = 1;
		top_panel.add(nick_name_text, gbc_nick_name_text);
		nick_name_info_label = new JLabel("");
		GridBagConstraints gbc_nick_name_info_label = new GridBagConstraints();
		gbc_nick_name_info_label.fill = GridBagConstraints.BOTH;
		gbc_nick_name_info_label.insets = new Insets(0, 0, 5, 0);
		gbc_nick_name_info_label.gridx = 2;
		gbc_nick_name_info_label.gridy = 1;
		top_panel.add(nick_name_info_label, gbc_nick_name_info_label);
		nick_name_info_label.setForeground(Color.RED);
		password_label = new JLabel("密码：");
		GridBagConstraints gbc_password_label = new GridBagConstraints();
		gbc_password_label.anchor = GridBagConstraints.EAST;
		gbc_password_label.fill = GridBagConstraints.VERTICAL;
		gbc_password_label.insets = new Insets(0, 0, 5, 5);
		gbc_password_label.gridx = 0;
		gbc_password_label.gridy = 2;
		top_panel.add(password_label, gbc_password_label);
		password_text = new JPasswordField(10);
		GridBagConstraints gbc_password_text = new GridBagConstraints();
		gbc_password_text.fill = GridBagConstraints.BOTH;
		gbc_password_text.insets = new Insets(0, 0, 5, 5);
		gbc_password_text.gridx = 1;
		gbc_password_text.gridy = 2;
		top_panel.add(password_text, gbc_password_text);
		password_info_label = new JLabel("");
		GridBagConstraints gbc_password_info_label = new GridBagConstraints();
		gbc_password_info_label.fill = GridBagConstraints.BOTH;
		gbc_password_info_label.insets = new Insets(0, 0, 5, 0);
		gbc_password_info_label.gridx = 2;
		gbc_password_info_label.gridy = 2;
		top_panel.add(password_info_label, gbc_password_info_label);
		password_info_label.setForeground(Color.red);
		password_to_confirm_label = new JLabel("确认密码：");
		GridBagConstraints gbc_password_to_confirm_label = new GridBagConstraints();
		gbc_password_to_confirm_label.anchor = GridBagConstraints.EAST;
		gbc_password_to_confirm_label.fill = GridBagConstraints.VERTICAL;
		gbc_password_to_confirm_label.insets = new Insets(0, 0, 0, 5);
		gbc_password_to_confirm_label.gridx = 0;
		gbc_password_to_confirm_label.gridy = 3;
		top_panel.add(password_to_confirm_label, gbc_password_to_confirm_label);
		password_to_confirm_info_label = new JLabel("");
		password_to_confirm_text = new JPasswordField(10);
		GridBagConstraints gbc_password_to_confirm_text = new GridBagConstraints();
		gbc_password_to_confirm_text.fill = GridBagConstraints.BOTH;
		gbc_password_to_confirm_text.insets = new Insets(0, 0, 0, 5);
		gbc_password_to_confirm_text.gridx = 1;
		gbc_password_to_confirm_text.gridy = 3;
		top_panel.add(password_to_confirm_text, gbc_password_to_confirm_text);
		GridBagConstraints gbc_password_to_confirm_info_label = new GridBagConstraints();
		gbc_password_to_confirm_info_label.fill = GridBagConstraints.BOTH;
		gbc_password_to_confirm_info_label.gridx = 2;
		gbc_password_to_confirm_info_label.gridy = 3;
		top_panel.add(password_to_confirm_info_label, gbc_password_to_confirm_info_label);
		password_to_confirm_info_label.setForeground(Color.RED);

		Box vBox_all = Box.createVerticalBox();

		getContentPane().add(vBox_all, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		vBox_all.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel buttom_jpanel = new JPanel();
		panel.add(buttom_jpanel, BorderLayout.NORTH);
		GridBagLayout gbl_buttom_jpanel = new GridBagLayout();
		gbl_buttom_jpanel.rowHeights = new int[] { 27 };
		gbl_buttom_jpanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_buttom_jpanel.rowWeights = new double[] { 0.0 };
		buttom_jpanel.setLayout(gbl_buttom_jpanel);
		
		GridBagConstraints gbc_go_back = new GridBagConstraints();
		gbc_go_back.weightx = 15.0;
		gbc_go_back.fill = GridBagConstraints.BOTH;
		gbc_go_back.insets = new Insets(0, 0, 0, 5);
		gbc_go_back.gridx = 0;
		gbc_go_back.gridy = 0;
		buttom_jpanel.add(go_back, gbc_go_back);


		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.weightx = 20.0;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		buttom_jpanel.add(register_button, gbc_btnNewButton);

		Component horizontalGlue = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.weightx = 65.0;
		gbc_horizontalGlue.gridwidth = 2;
		gbc_horizontalGlue.gridx = 2;
		gbc_horizontalGlue.gridy = 0;
		buttom_jpanel.add(horizontalGlue, gbc_horizontalGlue);
	}
}
