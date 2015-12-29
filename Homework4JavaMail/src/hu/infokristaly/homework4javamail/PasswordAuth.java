package hu.infokristaly.homework4javamail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PasswordAuth extends Authenticator {
		String username, password;

		public PasswordAuth(String name, String passwd) {
			username = name;
			password = passwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}

}
