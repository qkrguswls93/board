package jmp.spring.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MailService {
	
	public String passwordMailSend() {
		// ���� ���� ����
		Properties prop = System.getProperties();
		prop.put("mail.smtp.starttls.enable", "true"); // �α��ν� TLS�� ����� ������ ����
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");	
		prop.put("mail.smtp.host", "smtp.gmail.com");// SMTP����
		prop.put("mail.smtp.auth", "true");// SMTP ������ ���� ���
		prop.put("mail.smtp.port", "587");// TLS ��Ʈ��ȣ= 587, SSL ��Ʈ��ȣ= 465
		
		String mail_id = "pjjjj865@gmail.com"; //���̵�
		String mail_pw = "saoyjaajmktzqwxg"; //�ۺ��
		
		// ���� ���� ������ ID/PW ����
		Authenticator auth = new MailAuth(mail_id, mail_pw);
		// ���� �� �޼��� ���� (������Ƽ, ����)
		Session session = Session.getDefaultInstance(prop, auth);
		MimeMessage msg = new MimeMessage(session);
		String res = "";
		try {
			// ������ ��¥ ����
			msg.setSentDate(new Date());
			// �߼��� ���� (�߼����� ����, �߼��ڸ�)
			msg.setFrom(new InternetAddress("pjjjj865@gmail.com", "admin"));
             // ������ ���� 
			// Message.RecipientType.TO : �޴� ��� 
			InternetAddress to = new InternetAddress("pjjjj865@gmail.com");
			msg.setRecipient(Message.RecipientType.TO, to);
			
            // ���� ����
			msg.setSubject("비밀번호발급메일입니다.", "UTF-8");
			// ���� ����
			
			res = UUID.randomUUID().toString().substring(0,7);
			msg.setText("임시비밀번호는 "+res+"입니다", "UTF-8");
			
            // ���� �߼�
			Transport.send(msg);
			
			
		} catch (AddressException ae) {// �ּҸ� �Է����� �ʾ��� ���
			System.out.println("AddressException : " + ae.getMessage());
		} catch (MessagingException me) {// �޼����� �̻��� ���� ���
			System.out.println("MessagingException : " + me.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
		}
		return res;
	}
}

class MailAuth extends Authenticator {

	PasswordAuthentication pa;
	
	public MailAuth(String mail_id, String mail_pw) {
		// 사용자 인즌 정보를 담아서 반환
		pa = new PasswordAuthentication(mail_id, mail_pw);
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}

}
