package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import defined_type.UI_code;

public class Forget_Password extends JFrame implements UI_code {

	JLabel user_name_label, new_password_label,password_to_confirm_label;
	JLabel user_name_info_label,new_password_info_label,password_to_confirm_info_label;
	JLabel go_back;
	JTextField user_name_text;
	JPasswordField new_password_text, password_to_confirm_text;
	JButton submit_button;
	JFrame _this;
	String user_name, password, nick_name, password_to_comfirm, encrypted_password;
	boolean user_name_set, password_set, password_to_confirm_set, nick_name_set;

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public Forget_Password() {
		Init();
		setActions();
	}

	private void setActions() {
		// TODO Auto-generated method stub
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
				dispose();
				new Start_ui(LOGIN_VIEW).start();
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
				} else if (source == new_password_text) {
					password = String.valueOf(new_password_text.getPassword());
					new_password_info_label.setText("");
					if (password.isEmpty()) {
						new_password_info_label.setText("请输入密码");
						return;
					}
					if (password.length() < 6) {
						new_password_info_label.setText("密码长度至少为6位！");
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
				} 
				if (user_name_set && password_set && password_to_confirm_set && nick_name_set)
					submit_button.setEnabled(true);
				return;
			}

			@Override
			public void focusGained(FocusEvent arg0) {

			}
		};
		user_name_text.addFocusListener(showInfo);
		new_password_text.addFocusListener(showInfo);
		password_to_confirm_text.addFocusListener(showInfo);
	}

	private void Init() {
		// TODO Auto-generated method stub

		user_name_label = new JLabel("用户名：");
		new_password_label = new JLabel("新密码：");
		password_to_confirm_label = new JLabel("确认新密码：");
		
		user_name_info_label = new JLabel("");
		password_to_confirm_info_label = new JLabel("");
		new_password_info_label = new JLabel("");
		submit_button = new JButton("提交");
		
		user_name_text = new JTextField(10);
		new_password_text = new JPasswordField(10);
		password_to_confirm_text = new JPasswordField(10);
		
		go_back = new JLabel("返回登录界面");
		
		user_name_info_label.setForeground(Color.RED);
		new_password_info_label.setForeground(Color.red);
		password_to_confirm_info_label.setForeground(Color.RED);

		_this = this;

		initial_ui();

		setTitle("重置密码");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void initial_ui() {
		// TODO Auto-generated method stub

		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {30, 100, 180, 120, 30};
		gbl_panel.rowHeights = new int[] { 30, 30, 30, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);

		GridBagConstraints gbc_user_name_label = new GridBagConstraints();
		gbc_user_name_label.anchor = GridBagConstraints.EAST;
		gbc_user_name_label.insets = new Insets(0, 15, 5, 5);
		gbc_user_name_label.gridx = 1;
		gbc_user_name_label.gridy = 0;
		panel.add(user_name_label, gbc_user_name_label);
		GridBagConstraints gbc_user_name_text = new GridBagConstraints();
		gbc_user_name_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_user_name_text.insets = new Insets(0, 0, 5, 5);
		gbc_user_name_text.gridx = 2;
		gbc_user_name_text.gridy = 0;
		panel.add(user_name_text, gbc_user_name_text);

		GridBagConstraints gbc_user_name_info_label = new GridBagConstraints();
		gbc_user_name_info_label.fill = GridBagConstraints.BOTH;
		gbc_user_name_info_label.insets = new Insets(0, 0, 5, 5);
		gbc_user_name_info_label.gridx = 3;
		gbc_user_name_info_label.gridy = 0;
		panel.add(user_name_info_label, gbc_user_name_info_label);
		
		GridBagConstraints gbc_new_password_label = new GridBagConstraints();
		gbc_new_password_label.anchor = GridBagConstraints.EAST;
		gbc_new_password_label.insets = new Insets(0, 15, 5, 5);
		gbc_new_password_label.gridx = 1;
		gbc_new_password_label.gridy = 1;
		panel.add(new_password_label, gbc_new_password_label);
		GridBagConstraints gbc_password_text = new GridBagConstraints();
		gbc_password_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_password_text.insets = new Insets(0, 0, 5, 5);
		gbc_password_text.gridx = 2;
		gbc_password_text.gridy = 1;
		panel.add(new_password_text, gbc_password_text);

		GridBagConstraints gbc_new_password_info_label = new GridBagConstraints();
		gbc_new_password_info_label.fill = GridBagConstraints.BOTH;
		gbc_new_password_info_label.insets = new Insets(0, 0, 5, 5);
		gbc_new_password_info_label.gridx = 3;
		gbc_new_password_info_label.gridy = 1;
		panel.add(new_password_info_label, gbc_new_password_info_label);

		GridBagConstraints gbc_password_to_confirm_label = new GridBagConstraints();
		gbc_password_to_confirm_label.anchor = GridBagConstraints.EAST;
		gbc_password_to_confirm_label.insets = new Insets(0, 15, 5, 5);
		gbc_password_to_confirm_label.gridx = 1;
		gbc_password_to_confirm_label.gridy = 2;
		panel.add(password_to_confirm_label, gbc_password_to_confirm_label);

		GridBagConstraints gbc_password_to_confirm_text = new GridBagConstraints();
		gbc_password_to_confirm_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_password_to_confirm_text.insets = new Insets(0, 0, 5, 5);
		gbc_password_to_confirm_text.gridx = 2;
		gbc_password_to_confirm_text.gridy = 2;
		panel.add(password_to_confirm_text, gbc_password_to_confirm_text);

		GridBagConstraints gbc_password_to_confirm_info_label = new GridBagConstraints();
		gbc_password_to_confirm_info_label.fill = GridBagConstraints.BOTH;
		gbc_password_to_confirm_info_label.insets = new Insets(0, 0, 5, 5);
		gbc_password_to_confirm_info_label.gridx = 3;
		gbc_password_to_confirm_info_label.gridy = 2;
		panel.add(password_to_confirm_info_label, gbc_password_to_confirm_info_label);
		

		GridBagConstraints gbc_go_back = new GridBagConstraints();
		gbc_go_back.insets = new Insets(0, 15, 0, 5);
		gbc_go_back.gridx = 1;
		gbc_go_back.gridy = 3;
		panel.add(go_back, gbc_go_back);
		GridBagConstraints gbc_submit_button = new GridBagConstraints();
		gbc_submit_button.insets = new Insets(0, 0, 0, 5);
		gbc_submit_button.gridx = 2;
		gbc_submit_button.gridy = 3;
		panel.add(submit_button, gbc_submit_button);
	}
}
