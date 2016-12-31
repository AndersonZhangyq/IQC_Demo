package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.User_info;
import sun.misc.BASE64Encoder;
import url_request.*;

public class Login extends JFrame implements UI_code {

	JPanel login_top_panel, login_buttom_panel;

	JLabel user_name_label, password_label;
	JLabel register_label, foreget_password_label;
	JTextField user_name_text;
	JPasswordField password_text;
	JButton login_button;
	JCheckBox remenber_password_checkbox, auto_login_checkbox;
	JFrame _this;
	String username, password, encrypted_password;
	boolean autoLogin = false;
	
	User_info user_info = User_info.getInstance();

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public Login() {
		Init();
		setActions();

		if (!autoLogin)
			setVisible(true);
	}

	public void Init() {

		_this = this;

		login_top_panel = new JPanel(null);
		login_buttom_panel = new JPanel();

		user_name_label = new JLabel("用户名：");
		password_label = new JLabel("密码：");
		user_name_text = new JTextField(10);
		password_text = new JPasswordField(10);
		login_button = new JButton("登录");
		register_label = new JLabel("用户注册");
		foreget_password_label = new JLabel("忘记密码");
		remenber_password_checkbox = new JCheckBox("记住密码");
		auto_login_checkbox = new JCheckBox("自动登录");

		checkAutoLogin();

		initialize_ui();

		this.setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initialize_ui() {
		Box hBox_user = Box.createHorizontalBox();
		hBox_user.add(user_name_label);
		hBox_user.add(user_name_text);
		hBox_user.add(register_label);

		Box hBox_pass = Box.createHorizontalBox();
		hBox_pass.add(password_label);
		hBox_pass.add(password_text);
		hBox_pass.add(foreget_password_label);

		Box hBox_radio = Box.createHorizontalBox();
		hBox_radio.add(remenber_password_checkbox);
		hBox_radio.add(Box.createGlue());
		hBox_radio.add(auto_login_checkbox);

		Box hBox_bottom = Box.createHorizontalBox();
		hBox_bottom.add(login_button);

		Box vBox = Box.createVerticalBox();
		vBox.add(hBox_user);
		vBox.add(hBox_pass);
		vBox.add(hBox_radio);
		vBox.add(hBox_bottom);

		this.add(vBox, BorderLayout.SOUTH);
	}

	private void setActions() {
		login_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				username = user_name_text.getText();
				password = String.valueOf(password_text.getPassword());
				showResponse();
			}
		});

		auto_login_checkbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (auto_login_checkbox.isSelected()) {
					remenber_password_checkbox.setSelected(true);
				} else {
					remenber_password_checkbox.setSelected(false);
				}
			}
		});

		MouseListener label_mouse_listener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == foreget_password_label) {
					_this.dispose();
					new Start_ui(-1).start();
				} else if (e.getSource() == register_label) {
					_this.dispose();
					new Start_ui(REGISTER_VIEW).start();
				}
			}
		};
		foreget_password_label.addMouseListener(label_mouse_listener);
		register_label.addMouseListener(label_mouse_listener);
	}

	private void checkAutoLogin() {
		try {
			File last_user = new File("./last.in");
			if (last_user.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(last_user));
				username = bufferedReader.readLine().trim();
				bufferedReader.close();
				if (new File("./user_setting" + username.hashCode() + ".in").exists()) {
					bufferedReader = new BufferedReader(
							new FileReader(new File("./user_setting" + username.hashCode() + ".in")));
					username = bufferedReader.readLine().trim();
					password = bufferedReader.readLine().trim();
					showResponse();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showResponse() {
		switch (verifyData()) {
		case Empty_password:
			JOptionPane.showMessageDialog(_this.getContentPane(), "请输入密码！", "登陆失败", 0);
			break;
		case Empty_userName:
			JOptionPane.showMessageDialog(_this.getContentPane(), "请输入用户名！", "登陆失败", 0);
			break;
		case No_user:
			JOptionPane.showMessageDialog(_this.getContentPane(), "没有该用户！", "登陆失败", 0);
			break;
		case Mismatch:
			JOptionPane.showMessageDialog(_this.getContentPane(), "用户名或密码错误！", "登陆失败", 0);
			break;
		case Success:
			try {
				FileWriter fileWriter = new FileWriter("./last.in");
				fileWriter.write(username);
				fileWriter.close();
				if (remenber_password_checkbox.isSelected()) {
					/*
					 * MessageDigest md5 = MessageDigest.getInstance("MD5");
					 * BASE64Encoder base64 = new BASE64Encoder(); String
					 * endcoded_password =
					 * base64.encode(md5.digest(password.getBytes("utf-8")));
					 */
					encrypted_password = new BASE64Encoder()
							.encode(MessageDigest.getInstance("MD5").digest(password.getBytes("utf-8")));
					fileWriter = new FileWriter(new File("./user_setting" + username.hashCode() + ".in"));
					fileWriter.write(username + '\n');
					fileWriter.write(encrypted_password);
					fileWriter.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// TODO Login
			autoLogin = true;
			user_info.setUser_name(username);
			_this.dispose();
			Start_ui aStart_ui = new Start_ui(USER_MAIN_VIEW);
			aStart_ui.start();
		default:
			break;
		}
	}

	public Error_code verifyData() {
		if (username.isEmpty())
			return Error_code.Empty_userName;
		if (password.isEmpty())
			return Error_code.Empty_password;
		// TODO Check online!
		return UrlRequest.Check_login(username, password);
	}

	public static void main(String[] args) {
		new Login();
	}
}
