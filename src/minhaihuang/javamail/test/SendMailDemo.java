package minhaihuang.javamail.test;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 熟悉发送简单邮件的流程
 * @author 黄帅哥
 *
 */
public class SendMailDemo {
	
	/**
	 * 创建一个含有配置信息的Properties对象
		创建Session对象,把Properties中的信息传入
		编写自己的Message邮件对象
		使用Session获得Transport邮件发送对象
		使用Transport对象发送Message邮件对象

	 * @param args
	 * @throws MessagingException 
	 */
	public static void main(String[] args) throws MessagingException {
		//1,创建一个properties，包含配置信息
		Properties props=new Properties();
		props.put("mail.transport.protocol","smtp");
		props.put("mail.smtp.host", "localhost");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		
		//2,创建session对象
		Session session=Session.getDefaultInstance(props, new Authenticator() {
			//邮箱认证
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication("aaa", "123");
			}
		});
		
		// 3、编写邮件Message
		//发件人与收件人的地址
		Address from = new InternetAddress("aaa@hhm.com");
		Address to = new InternetAddress("bbb@hhm.com");
		
		Message message=new MimeMessage(session);
		message.setFrom(from);
		message.setRecipient(Message.RecipientType.TO, to);
		message.setSubject("邮件标题-简单邮件示例");
		
		message.setContent("这是简单的文本邮件...","text/html;charset=utf-8");
		//4,创建邮件发送对象
		Transport transport=session.getTransport();
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
}
