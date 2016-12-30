package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sun.misc.BASE64Encoder;
import url_request.UrlRequest;

public class Register extends JFrame implements UI_code {

	JLabel user_name_label, nick_name_label, password_label, password_to_confirm_label;
	JTextField user_name_text, nick_name_text;
	JPasswordField password_text, password_to_confirm_text;
	JLabel user_name_info_label, nick_name_info_label, password_info_label, password_to_confirm_info_label;
	JButton register_button;
	JFrame _this;
	String username, password, nick_name, password_to_comfirm, encrypted_password;
	boolean user_name_set, password_set, password_to_confirm_set, nick_name_set;

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public Register() {
		Init();
		setActions();
		setVisible(true);
	}

	private void Init() {

		_this = this;

		user_name_set = password_set = password_to_confirm_set = nick_name_set = false;
		user_name_label = new JLabel("用户名：");
		nick_name_label = new JLabel("昵称：");
		password_label = new JLabel("密码：");
		password_to_confirm_label = new JLabel("确认密码：");
		user_name_info_label = new JLabel("");
		nick_name_info_label = new JLabel("");
		password_info_label = new JLabel("");
		password_to_confirm_info_label = new JLabel("");

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
				switch (UrlRequest.Check_Register(username, encrypted_password, nick_name)) {
				case Success:
					JOptionPane.showMessageDialog(_this.getContentPane(), "注册成功\n按下确定后将自动登录！", "注册信息",
							JOptionPane.INFORMATION_MESSAGE);
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
					username = user_name_text.getText();
					user_name_info_label.setText("");
					if (username.isEmpty()) {
						user_name_info_label.setText("请输入用户名！");
						return;
					}
					String emailReg = "(([a-zA-Z]?[0-9]+)|([a-zA-Z]+[0-9]?))@([a-zA-z0-9]{1,}.){1,3}[a-zA-z]{1,}";
					Matcher email_match = Pattern.compile(emailReg).matcher(username);
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
		user_name_text.addFocusListener(showInfo);
		password_text.addFocusListener(showInfo);
		password_to_confirm_text.addFocusListener(showInfo);
		nick_name_text.addFocusListener(showInfo);
	}

	private void initialize_ui() {
		Box hBox_user = Box.createHorizontalBox();
		hBox_user.add(user_name_label);
		hBox_user.add(user_name_text);
		hBox_user.add(user_name_info_label);

		Box hBox_nickname = Box.createHorizontalBox();
		hBox_nickname.add(nick_name_label);
		hBox_nickname.add(nick_name_text);
		hBox_nickname.add(nick_name_info_label);

		Box hbox_password = Box.createHorizontalBox();
		hbox_password.add(password_label);
		hbox_password.add(password_text);
		hbox_password.add(password_info_label);

		Box hBox_password_to_confirm = Box.createHorizontalBox();
		hBox_password_to_confirm.add(password_to_confirm_label);
		hBox_password_to_confirm.add(password_to_confirm_text);
		hBox_password_to_confirm.add(password_to_confirm_info_label);

		Box hBox_bottom = Box.createHorizontalBox();
		hBox_bottom.add(register_button);

		Box vBox_all = Box.createVerticalBox();
		vBox_all.add(hBox_user);
		vBox_all.add(hBox_nickname);
		vBox_all.add(hbox_password);
		vBox_all.add(hBox_password_to_confirm);
		vBox_all.add(hBox_bottom);

		this.add(vBox_all, BorderLayout.NORTH);
	}

	public static void main(String[] args) {
		new Register();
	}

}
