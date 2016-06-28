package minhaihuang.javamail.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * 这是一个邮件的工具类
 * @author 黄帅哥
 *
 */
public class MailUtils {
	
	private static Session session;
	/**
	 * 由于配置信息都差不多，所以用静态代码块封装
	 */
	static{
		Properties props=new Properties();
		props.put("mail.transport.protocol","smtp");//要装入smtp协议
		props.put("mail.smtp.host", "localhost");	//以本地为主机
		props.put("mail.smtp.auth", "true");	//使用auth命令认证用户
		props.put("mail.debug", "true");		//开启调试模式
		
		session=Session.getInstance(props, new Authenticator() {
			//需要用户验证，需要验证用户名与密码
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("aaa", "123");
			}
		});
	}

	
	/**
	 * 获取邮件体的方法
	 * @return
	 */
	public static Message CreateMessage(){
		//使用的是Mime协议的邮件体
		return (new MimeMessage(session));
	}
	
	public static void sendMeaage(Message message) throws MessagingException{
		//通过回话找到传输器
		Transport transport=session.getTransport();
		//设置传输信息
		transport.connect();//获取连接
		transport.sendMessage(message, message.getAllRecipients());//发送
		transport.close();//关闭传输器
	}
}
