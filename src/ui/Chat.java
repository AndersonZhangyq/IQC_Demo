package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.management.ManagementFactory;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Chat_Info;
import controller.Message_Driver;
import ui.User_Main.Friend_info_panel;
import url_request.UrlRequest;

public class Chat extends JFrame {

	public class For_message {
		public JTextArea show_message_textarea = new JTextArea();

		public void appendMessage(String message) {
			show_message_textarea.append(message);
		}
	}

	JFrame _this;
	Chat_Info chat_Info;
	JTextArea show_message_textarea, send_message_textarea;
	JButton send_button;
	JComboBox<String> item_combo;
	JScrollPane scrollPane_top, scrollPane_bottom;
	JPanel top_panel, buttom_panel;
	Message_Driver message_Driver;
	For_message for_message;
	Friend_info_panel origin;
	boolean use_Ctrl = false;

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public Chat(Chat_Info chat_Info, Friend_info_panel origin) {

		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("Chat PID: " + name);

		this.chat_Info = chat_Info;
		this.origin = origin;
		Init();
		setActions();
		message_Driver = new Message_Driver(chat_Info.getTo_ip(), for_message);
		setVisible(true);
	}

	private void sendMessage() {
		String message_text = send_message_textarea.getText();
		// Verify
		if (message_text.isEmpty()) {
			JOptionPane.showMessageDialog(_this, "无法发送空消息！", "消息发送错误", JOptionPane.ERROR_MESSAGE);
			return;
		} else if (message_text.trim().isEmpty()) {
			JOptionPane.showMessageDialog(_this, "至少含有一个非空格字符！", "消息发送错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		UrlRequest.sendMessage(chat_Info.getUser_from_id(), chat_Info.getUser_to_id(), message_text);
		send_message_textarea.setText("");
		try {
			message_Driver.sendMessage(message_text);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setActions() {

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
				origin.setUnshown();
				message_Driver.exitReceiver();
				_this.dispose();
				Thread current = Thread.currentThread();
				current = null;
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

		send_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});

		send_message_textarea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (use_Ctrl && (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER)) {
					sendMessage();
				} else if ((!use_Ctrl) && e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		send_message_textarea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String text = send_message_textarea.getText();
				if (text.length() > 120) {
					JOptionPane.showMessageDialog(_this, "输入字符总数必须在120个字以下", "提示", JOptionPane.WARNING_MESSAGE);
					// Must be Thread!!
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							send_message_textarea.setText(text.substring(0, 120));
						}
					}).start();
					;
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		});

		item_combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String item = (String) e.getItem();
					if (item.contains("Ctrl")) {
						use_Ctrl = true;
					} else {
						use_Ctrl = false;
					}
				}
			}
		});
	}

	private void initialize_ui() {

		send_message_textarea.setLineWrap(true);
		send_message_textarea.setWrapStyleWord(true);

		item_combo.setMaximumSize(new Dimension(100, 27));
		item_combo.setModel(new DefaultComboBoxModel<String>(new String[] { "按 Enter", "按 Ctrl+Enter" }));

		Box hBox_button = Box.createHorizontalBox();
		hBox_button.add(Box.createHorizontalGlue());
		hBox_button.add(send_button);
		hBox_button.add(item_combo);

		Box vBox_bottom = Box.createVerticalBox();
		vBox_bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
		vBox_bottom.add(Box.createVerticalStrut(20));
		vBox_bottom.add(buttom_panel);
		vBox_bottom.add(hBox_button);

		Box vBox_all = Box.createVerticalBox();
		vBox_all.add(top_panel);
		vBox_all.add(vBox_bottom);

		// send_button.setAlignmentX(Component.RIGHT_ALIGNMENT);

		this.getContentPane().add(vBox_all);
	}

	private void Init() {
		_this = this;
		for_message = new For_message();

		show_message_textarea = for_message.show_message_textarea;
		show_message_textarea.setEditable(false);

		System.out.println("for_message from Chat: " + for_message);

		send_message_textarea = new JTextArea();
		send_button = new JButton("发送");
		item_combo = new JComboBox<>();
		scrollPane_top = new JScrollPane(show_message_textarea);
		scrollPane_bottom = new JScrollPane(send_message_textarea);
		top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout(0, 0));
		top_panel.setPreferredSize(new Dimension(20, 130));
		top_panel.add(scrollPane_top);
		buttom_panel = new JPanel();
		buttom_panel.setLayout(new BorderLayout(0, 0));
		buttom_panel.add(scrollPane_bottom);

		initialize_ui();

		setResizable(false);
		setSize(570, 400);
		setTitle("与  " + chat_Info.getNick_name() + " 聊天中");
	}
}