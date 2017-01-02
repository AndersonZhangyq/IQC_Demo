package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import defined_type.Error_code;
import defined_type.UI_code;
import defined_type.User_info;
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
	boolean allow_auto_login = true;

	User_info user_info = User_info.getInstance();

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public Login(boolean allow_auto_login) {
		this.allow_auto_login = allow_auto_login;
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("Login PID: " + name);
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
		setTitle("登录");
	}

	private void initialize_ui() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel,BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 30, 60, 200, 60, 30 };
		gbl_panel.rowHeights = new int[] { 27, 27, 27, 30, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);

		GridBagConstraints gbc_user_name_label = new GridBagConstraints();
		gbc_user_name_label.fill = GridBagConstraints.BOTH;
		gbc_user_name_label.insets = new Insets(0, 0, 5, 5);
		gbc_user_name_label.gridx = 1;
		gbc_user_name_label.gridy = 0;
		panel.add(user_name_label, gbc_user_name_label);
		user_name_text = new JTextField(10);
		GridBagConstraints gbc_user_name_text = new GridBagConstraints();
		gbc_user_name_text.fill = GridBagConstraints.BOTH;
		gbc_user_name_text.insets = new Insets(0, 0, 5, 5);
		gbc_user_name_text.gridx = 2;
		gbc_user_name_text.gridy = 0;
		panel.add(user_name_text, gbc_user_name_text);
		GridBagConstraints gbc_register_label = new GridBagConstraints();
		gbc_register_label.fill = GridBagConstraints.BOTH;
		gbc_register_label.insets = new Insets(0, 0, 5, 0);
		gbc_register_label.gridx = 3;
		gbc_register_label.gridy = 0;
		panel.add(register_label, gbc_register_label);
		GridBagConstraints gbc_password_label = new GridBagConstraints();
		gbc_password_label.fill = GridBagConstraints.BOTH;
		gbc_password_label.insets = new Insets(0, 0, 5, 5);
		gbc_password_label.gridx = 1;
		gbc_password_label.gridy = 1;
		panel.add(password_label, gbc_password_label);
		password_text = new JPasswordField(10);
		GridBagConstraints gbc_password_text = new GridBagConstraints();
		gbc_password_text.fill = GridBagConstraints.BOTH;
		gbc_password_text.insets = new Insets(0, 0, 5, 5);
		gbc_password_text.gridx = 2;
		gbc_password_text.gridy = 1;
		panel.add(password_text, gbc_password_text);
		GridBagConstraints gbc_foreget_password_label = new GridBagConstraints();
		gbc_foreget_password_label.fill = GridBagConstraints.BOTH;
		gbc_foreget_password_label.insets = new Insets(0, 0, 5, 0);
		gbc_foreget_password_label.gridx = 3;
		gbc_foreget_password_label.gridy = 1;
		panel.add(foreget_password_label, gbc_foreget_password_label);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		panel_1.add(remenber_password_checkbox);
		panel_1.add(auto_login_checkbox);
		GridBagConstraints gbc_login_button = new GridBagConstraints();
		gbc_login_button.fill = GridBagConstraints.VERTICAL;
		gbc_login_button.insets = new Insets(0, 0, 5, 5);
		gbc_login_button.gridx = 2;
		gbc_login_button.gridy = 3;
		panel.add(login_button, gbc_login_button);
	}

	private void setActions() {
		login_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				username = user_name_text.getText();
				password = String.valueOf(password_text.getPassword());
				showResponse(false);
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
					new Start_ui(FORGET_PASSWORD_VIEW).start();
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
		if (allow_auto_login) {
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
						bufferedReader.close();
						showResponse(true);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void showResponse(boolean isAuto) {
		switch (verifyData(isAuto)) {
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
		case Onlined:
			JOptionPane.showMessageDialog(_this.getContentPane(), "请勿重复登录！", "登陆失败", 0);
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

	public Error_code verifyData(boolean isAuto) {
		if (username.isEmpty())
			return Error_code.Empty_userName;
		if (password.isEmpty())
			return Error_code.Empty_password;
		// TODO Check online!
		return UrlRequest.Check_login(username, password, isAuto);
	}

	public static void main(String[] args) {
		new Login(true);
	}
}
