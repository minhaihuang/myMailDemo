package minhaihuang.javamail.test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * 发送一个html格式的邮件
 * 要求：在邮箱中直接显示图片
 * @author 黄帅哥
 *
 */
public class HtmlMail {

	public static void main(String[] args) throws MessagingException {
		htmlMail();
	}
	
	/**
	 * 发送邮件的方法
	 * @throws MessagingException 
	 */
	public static void htmlMail() throws MessagingException{
		//1，邮件体
		Message message=MailUtils.CreateMessage();
		
		//2,设置发件人与收件人
		Address from=new InternetAddress("aaa@hhm.com");//发件人
		Address to=new InternetAddress("bbb@hhm.com");//收件人
		
		//3，设置邮件体的内容
		message.setFrom(from);//设置邮件体的发件人
		message.setRecipient(Message.RecipientType.TO, to);//设置邮件体收件人
		message.setSubject("这是一封html格式的邮件");//设置邮件标题
		
		//4，设置第一个MIME消息
		MimeBodyPart content=new MimeBodyPart();
		content.setContent("这是html的内容<br/><img src='cid:inner'/>",
				"tect/html;charset=utf-8");
		
		//5,设置第二个MIME信息
		MimeBodyPart pic=new MimeBodyPart();
		//文件数据源
		FileDataSource  fileDataSource=new FileDataSource("sn.jpg");
		//将数据交给数据处理器
		DataHandler dataHandler=new DataHandler(fileDataSource);
		//将数据处理器设置进入pic
		pic.setDataHandler(dataHandler);
		//设置图片的ID
		pic.setContentID("inner");
		
		//6,合并两个MIME
		MimeMultipart combine=new MimeMultipart();
		combine.addBodyPart(content);//加入第一个
		combine.addBodyPart(pic);//加入第二个
		combine.setSubType("related");//内嵌式，直接打开
		
		//将合并后的对象设置进入邮件体
		message.setContent(combine);
		//7，发送邮件
		MailUtils.sendMeaage(message);
	}
}
