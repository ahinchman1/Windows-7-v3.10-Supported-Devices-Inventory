package inventory_app;
import java.util.HashMap;


public class Authenticator {
	private static final HashMap<String, String> USERS = new HashMap<String, String>();
	
	static boolean validate(String user, String password) {
		String validUserPassword = USERS.get(user);
		return validUserPassword != null && validUserPassword.equals(password);
	}
}
