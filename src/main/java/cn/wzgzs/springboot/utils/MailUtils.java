package cn.wzgzs.springboot.utils;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	private static String host;
	private static String name;
	private static String password;
	
	/**
	 * @param host  发送邮件服务器地址
	 * @param name	发送者账户
	 * @param password	发送者账户密码
	 * @param sendName	发送者显示名称
	 */
	public MailUtils(String host, String name, String password) {
		super();
		MailUtils.host = host;
		MailUtils.name = name;
		MailUtils.password = password;
	}

	/**
	 * 邮箱工具
	 * @param email,接收者邮箱
	 * @param emailMsg,发送内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendMail(String email, String title, String emailMsg) throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();
		//设置发送的协议
		props.setProperty("mail.transport.protocol", "SMTP");
		
		//设置发送邮件的服务器
		props.setProperty("mail.host", host);	//smtp.163.com
		props.setProperty("mail.smtp.auth", "true");	// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//设置发送人的帐号和密码
				return new PasswordAuthentication(name, password);
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		//设置发送者
		message.setFrom(new InternetAddress(name));

		//设置发送方式与接收者
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); 

		//设置邮件主题
		message.setSubject(title);
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

		//设置邮件内容
		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送
		Transport.send(message);
	}
	
	public static void main(String[] args) {
//		MailUtils mail = new MailUtils(AppProps.getString("EmailSMTPHost"), AppProps.getString("EmailAccount"), AppProps.getString("EmailPassword"));
//		mail.sendMail(user.getTargetAddr(), esDto.getSendTitle(), esDto.getSendContent());
	}
}
