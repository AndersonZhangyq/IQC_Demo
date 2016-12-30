package url_request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import controller.Friends_Base_Info;
import controller.User_info;
import sun.misc.BASE64Encoder;
import ui.Error_code;

class Friend_Relation {
	String user_origin;
	String user_friend;
}

class Query_Friends_Result {

	private String result;
	private Friend_Relation[] data;

	public String getResult() {
		return result;
	}

	public Map<String, Friends_Base_Info> getFriendsList() {
		int length = data.length;
		System.out.println(this);
		Map<String, Friends_Base_Info> friends_list = new HashMap<String, Friends_Base_Info>();
		for (int i = 0; i < length; i++) {
			Map<String, String> info = UrlRequest.Query_info(data[0].user_friend);
			Friends_Base_Info example = new Friends_Base_Info(info.get("user_name"), info.get("status"),
					data[i].user_friend);
			friends_list.put(data[i].user_friend, example);
		}
		return friends_list;
	}

	@Override
	public String toString() {
		String result = null;
		for (Friend_Relation aBase_Info : data) {
			System.out.println(aBase_Info.user_origin + "  " + aBase_Info.user_friend);
		}
		return null;
	}
}

public class UrlRequest {
	static User_info instance = User_info.getInstance();

	private static final String SITE_URL = "http://localhost/java_web/index.php/";
	private static final String Check_Login = "login/index/";
	private static final String Check_Register = "register/index/";
	private static final String Query_friends = "friends/index/";
	private static final String Logout = "logout/index/";
	private static final String UserInfo = "userinfo/";

	private static final String State_Fail = "Failed"; // Useless
	private static final String State_SQL_Failure = "SQL_Failure";
	private static final String State_Success = "Success";
	private static final String State_Mismatch = "Mismatch";
	private static final String State_No_user = "No User";
	private static final String State_Unknow_Error = "Unknow_Error";
	private static final String State_Duplicate_Error = "Duplicate Error";

	private static Error_code getReturn(String in) {
		switch (in) {
		case State_Success:
			return Error_code.Success;
		case State_Mismatch:
			return Error_code.Mismatch;
		case State_SQL_Failure:
			return Error_code.SQL_failure;
		case State_No_user:
			return Error_code.No_user;
		case State_Unknow_Error:
			return Error_code.Unknow_Error;
		case State_Duplicate_Error:
			return Error_code.Duplicate_Error;
		default:
			return Error_code.Unknow_Error;
		}
	}

	private static String process_URL(URL to_connect) {
		HttpURLConnection url_connection;
		String line = null;
		try {
			url_connection = (HttpURLConnection) to_connect.openConnection();
			url_connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url_connection.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			line = buffer.toString();
			System.out.println(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}

	public static Map<String, String> Query_info(String id) {
		URL query_id = null;
		Map<String, String> result = null;
		try {
			query_id = new URL(SITE_URL + UserInfo + "info/" + id);
			Gson gson = new Gson();
			result = gson.fromJson(process_URL(query_id), new TypeToken<HashMap<String, String>>() {
			}.getType());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static Error_code Check_login(String user_name, String password_encoded) {

		HashMap<String, String> result = null;
		user_name = Base64.encode(user_name.getBytes());

		try {
			URL login = new URL(SITE_URL + Check_Login + user_name + '/' + password_encoded);
			System.out.println(login);
			Gson jGson = new GsonBuilder().create();
			result = jGson.fromJson(process_URL(login), HashMap.class);
		} catch (IOException e) {
			// TODO: handle exception
			return Error_code.Unknow_Error;
		}
		Error_code to_return = getReturn(result.get("result"));
		if (to_return == Error_code.Success) {
			instance.setID(result.get("id"));
		}
		return to_return;
	}

	public static Error_code Check_Register(String user_name, String password, String nick_name) {

		HashMap<String, String> result = null;
		user_name = Base64.encode(user_name.getBytes());
		nick_name = Base64.encode(nick_name.getBytes());
		try {
			password = new BASE64Encoder().encode(MessageDigest.getInstance("MD5").digest(password.getBytes("utf-8")));
			URL register = new URL(SITE_URL + Check_Register + user_name + '/' + password + '/' + nick_name);
			Gson jGson = new GsonBuilder().create();
			result = jGson.fromJson(process_URL(register), HashMap.class);
		} catch (Exception e) {
			// TODO: handle exception
			return Error_code.Unknow_Error;
		}
		return getReturn(result.get("result"));
	}

	public static Error_code Get_User_Friends() {

		Query_Friends_Result query_Result = null;
		try {
			URL query_friends = new URL(SITE_URL + Query_friends + instance.getID());
			Gson jGson = new GsonBuilder().create();
			query_Result = jGson.fromJson(process_URL(query_friends), Query_Friends_Result.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Error_code to_return = getReturn(query_Result.getResult());
		if (to_return == Error_code.Success)
			instance.setfrientList(query_Result.getFriendsList());
		return to_return;
	}

	public static void Logout() {
		try {
			URL logout = new URL(SITE_URL + Logout + instance.getID());
			process_URL(logout);
			instance.restore();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
