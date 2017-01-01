package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.glass.ui.TouchInputSupport;

import controller.Chat_Info;
import controller.Message_Driver;
import url_request.UrlRequest;

public class Chat extends JFrame {

	Chat_Info chat_Info;
	private static final long serialVersionUID = 1L;
	JTextArea message_textarea, send_message_textarea;
	JButton send_button;
	JComboBox<String> item_combo;
	JScrollPane scrollPane_top, scrollPane_bottom;
	Message_Driver message_Driver = new Message_Driver(this.message_textarea);

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	public Chat(Chat_Info chat_Info) {
		this.chat_Info = chat_Info;
		Init();
		setActions();
		setVisible(true);
	}

	private void setActions() {
		// TODO ʵ�ֵ��������Ϣ��Ϣ�ķ��ͣ�
		send_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message_text = send_message_textarea.getText();
				UrlRequest.sendMessage(chat_Info.getUser_from_id(), chat_Info.getUser_to_id(), message_text);
				//message_Driver.Communicate(message_text.getBytes());
			}
		});
	}

	private void initialize_ui() {

		item_combo.setMaximumSize(new Dimension(100, 27));
		item_combo.setModel(new DefaultComboBoxModel<String>(new String[] { "按 Enter", "按 Ctrl+Enter" }));

		message_textarea.setPreferredSize(new Dimension(4, 130));
		scrollPane_top.setPreferredSize(new Dimension(6, 130));

		Box hBox_button = Box.createHorizontalBox();
		hBox_button.add(Box.createHorizontalGlue());
		hBox_button.add(send_button);
		hBox_button.add(item_combo);

		Box vBox_bottom = Box.createVerticalBox();
		vBox_bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
		vBox_bottom.add(Box.createVerticalStrut(20));
		vBox_bottom.add(scrollPane_bottom);
		vBox_bottom.add(hBox_button);

		Box vBox_all = Box.createVerticalBox();
		vBox_all.add(scrollPane_top);
		vBox_all.add(vBox_bottom);

		// send_button.setAlignmentX(Component.RIGHT_ALIGNMENT);

		this.getContentPane().add(vBox_all);
	}

	private void Init() {
		message_textarea = new JTextArea();
		send_message_textarea = new JTextArea();
		send_button = new JButton("发送");
		item_combo = new JComboBox<>();
		scrollPane_bottom = new JScrollPane(message_textarea);
		scrollPane_top = new JScrollPane(send_message_textarea);

		initialize_ui();

		setResizable(false);
		setSize(570, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}