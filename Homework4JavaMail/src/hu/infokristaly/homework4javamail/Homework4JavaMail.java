package hu.infokristaly.homework4javamail;

import java.io.IOException;
import java.security.Security;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

/*
 * https://confluence.atlassian.com/display/JIRA/Connecting+to+SSL+services
 * http://www.oracle.com/technetwork/java/javamail/third-party-136965.html
 * http://stackoverflow.com/questions/9052391/access-restriction-the-constructor-provider-is-not-accessible-due-to-restrict
 * http://www.javaworld.com/article/2077479/java-se/java-tip-115--secure-javamail-with-jsse.html
 * http://www.tutorialspoint.com/javamail_api/javamail_api_useful_resources.htm
 * 
 */
public class Homework4JavaMail {
	static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider());
		Properties props = new Properties();
		props.put("mail.store.protocol", "imaps");
        props.setProperty( "mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty( "mail.imap.socketFactory.fallback", "false");      
        props.setProperty( "mail.imap.port", "993");
        props.setProperty( "mail.imap.socketFactory.port", "993");
        
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        String host = "mail.host.hu";
		String name = "user@host.hu";
		String passwd = "pwd";
		
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			
			store.connect(host, 993, name, passwd);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			// Folder defaultFolder = store.getDefaultFolder();
			// defaultFolder.open(Folder.READ_WRITE);
			String subjSearch = "szia";
			if ((subjSearch != null) && !subjSearch.isEmpty()) {
				SubjectTerm st = new SubjectTerm(subjSearch);
				Message[] found = inbox.search(st);
				for (int i = 1; i < found.length; i++) {
					Message m = found[i];
					log(m);
				}
			} else {
				int count = inbox.getMessageCount();
				for (int i = 1; i <= count; i++) {
					Message m = inbox.getMessage(i);
					log(m);
				}
			}

			Message msg = inbox.getMessage(inbox.getMessageCount());
			Address[] in = msg.getFrom();
			for (Address address : in) {
				System.out.println("FROM:" + address.toString());
			}
			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);
			System.out.println("SENT DATE:" + msg.getSentDate());
			System.out.println("SUBJECT:" + msg.getSubject());
			System.out.println("CONTENT:" + bp.getContent());
		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}

	private static void log(Message m) throws IOException, MessagingException {
		Multipart mp = (Multipart) m.getContent();
		BodyPart bp = mp.getBodyPart(0);
		System.out.println(bp.getContent());
	}

}
