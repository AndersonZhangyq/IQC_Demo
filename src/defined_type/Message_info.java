package defined_type;

import javafx.scene.chart.PieChart.Data;

public class Message_info {
	private String date;
	private String user_name;
	private String message;
	
	public Message_info(String data, String user_name, String message) {
		this.date = data;
		this.user_name = user_name;
		this.message = message;
	}
	
	public String getFullMessage(){
		return date + " " + user_name + "\n" + message;
	}
}
