package minhaihuang.javamail.test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * 写一个包含图片与文件的mail
 * 要求：图片直接显示，文件与附件的形式显示
 * @author 黄帅哥
 *
 */
public class PictureAndAccessoryMail {

	public static void main(String[] args) throws MessagingException {
		//1，获取邮件体
		Message message=MailUtils.CreateMessage();
		
		//2，设置发件人与收件人
		Address from=new InternetAddress("aaa@hhm.com");
		Address to=new InternetAddress("bbb@hhm.com");
		
		//3,将发件人与收件人设置进入到邮件体中
		message.setFrom(from);//发件人
		message.setRecipient(Message.RecipientType.TO, to);//收件人
		
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
		
		//6,合并两个MIME，第一次合并
		MimeMultipart combine=new MimeMultipart();
		combine.addBodyPart(content);//加入第一个
		combine.addBodyPart(pic);//加入第二个
		combine.setSubType("related");//内嵌式，直接打开
		
		//7，新开一个文件
		MimeBodyPart newFile=new MimeBodyPart();
		newFile.setContent(combine);//将第一次合并的对象给新开的文件
		
		//8,新开一个附件
		MimeBodyPart accessory=new MimeBodyPart();
		accessory.setDataHandler(new DataHandler(new FileDataSource("a.mp3")));
		//设置附件的文件名
		accessory.setFileName("a.mp3");
		
		//9,第二次合并
		MimeMultipart combine2=new MimeMultipart();
		combine2.addBodyPart(newFile);
		combine2.addBodyPart(accessory);
		//指定类型
		combine2.setSubType("mixed");
		
		//将第二次合并后的对象设置进入邮件体
		message.setContent(combine2);
		//7，发送邮件
		MailUtils.sendMeaage(message);
		
		
	}
}
