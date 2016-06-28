package minhaihuang.javamail.test;

import java.io.UnsupportedEncodingException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 发送一封带附件的邮件
 * 要求：文件已附件的形式显示在邮件中
 * @author 黄帅哥
 *
 */
public class AccessoryMail {

	public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
		//1，获得邮件体
		Message message=MailUtils.CreateMessage();
		
		//2,设置收件人与发件人
		//发件人
		Address from=new InternetAddress("aaa@hhm.com");
		Address to=new InternetAddress("bbb@hhm.com");
		
		//3,将收件人与发件人设置进入到邮件体中
		message.setFrom(from);//设置发件人
		message.setRecipient(Message.RecipientType.TO, to);//设置收件人
		message.setSubject("这是一封带附件的邮件");//设置邮件的标题
		
		//4，邮件体的内容
		//4-1文字部分
		MimeBodyPart text=new MimeBodyPart();
		text.setContent("附件中的美女好漂亮啊", "text/plain;charser=utf-8");
		
		//4-2文件部分
		MimeBodyPart file=new MimeBodyPart();
		file.setDataHandler(new DataHandler(new FileDataSource("sn.jpg")));
		//设置文件的名称，以防出现乱码的情况
		file.setFileName(MimeUtility.encodeText("美女.jpg"));
		
		//5,合并两个文件
		MimeMultipart combine=new MimeMultipart();
		combine.addBodyPart(text);
		combine.addBodyPart(file);
		combine.setSubType("mixed");//指定以附件的形式发送
		
		//6，将合并后的对象作为邮件体的内容，设置进入邮件体
		message.setContent(combine);
		
		//7,发送邮件
		MailUtils.sendMeaage(message);
	}
}
