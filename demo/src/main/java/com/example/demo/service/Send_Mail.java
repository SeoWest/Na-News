package com.example.demo.service;

import com.example.demo.common.CommandMap;
import com.example.demo.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class Send_Mail {
    String Sender = "";
    List<Map<String, Object>> Reciver = new ArrayList<>();
    String Content = "";
    String Title = "";
    String File_id = "";



    public Send_Mail(){}

    public Send_Mail(String Sender, List<Map<String, Object>> Reciver, String Content, String Title, String File_id) {
        this.Sender = Sender;
        this.Reciver = Reciver;
        this.Content = Content;
        this.Title = Title;
        this.File_id = File_id;
    }

    @Autowired
    JavaMailSenderImpl mailSender; //20210607

    @Autowired
    BoardMapper boardMapper;

    public void Send_News_Mail(CommandMap commandMap) throws MessagingException {
        /*Properties props = new Properties();

        //SSL 설정
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");//465
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //로그인 설정
        Session session = Session.getInstance(props,  new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //return new PasswordAuthentication("icnetwebmaster","icnet789456");
                return new PasswordAuthentication("nemocompany2009@gmail.com", "Icnet2009#!");
            }
        });*/

        final String FROM = this.Sender;
        final String FROMNAME = "유니";
        final String SMTP_USERNAME = "nemocompany2009";
        final String SMTP_PASSWORD = "Icnet2009#!";
        final String HOST = "smtp.gmail.com";
        final int PORT = 587;
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "false");
        Session session = Session.getDefaultInstance(props);

        try {

            //String filename = "F:\\test1.txt"; //20210607

            MimeMessage message = new MimeMessage(session);
            //final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            //message.setFrom(new InternetAddress(FROM, FROMNAME, "UTF-8"));
            message.setFrom(new InternetAddress(FROM, null, "UTF-8"));

            //message.setFrom(new InternetAddress(this.Sender)); //보내는 사람 이름
            //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.Reciver));// 받는 사람
            //String addrArr = this.Reciver.toString().replaceAll(";",",");
            //Address[] parse = InternetAddress.parse(addrArr);
            //message.setRecipients(Message.RecipientType.TO, parse); //받는 사람(다중 사용자) 원래 이거 !!!!!!!
            for(int i=0; i<this.Reciver.size(); i++) { //메일 수신 시 받는 사람 이름 포함
                if (this.Reciver.get(i).get("name") == null)
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.Reciver.get(i).get("email").toString()));
                else
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.Reciver.get(i).get("email").toString(), this.Reciver.get(i).get("name").toString(), "UTF-8"));
            }

            message.setSubject(this.Title); //제목
            message.setText(this.Content); //내용

            /*String pathfile="C:\\dev\\demo\\demo\\src\\main\\resources\\templates\\20210629aaa.jpg";
            FileDataSource fds = new FileDataSource(pathfile);
            String originalFileNm = "학습사진.jpg";
            helper.addAttachment(MimeUtility.encodeText(originalFileNm, "UTF-8", "B"), fds);*/

            //message.setFileName(this.File_id); //파일 첨부
            //message.setFileName(this.File_id);


            //이메일 헤더
            message.setHeader("content-Type", "text/html; charset=utf-8");
            //Transport.send(message);
            Transport transport = session.getTransport();
            try {
                System.out.println("Sending...");
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
                transport.sendMessage(message, message.getAllRecipients());
                System.out.println("Done!");
            } catch (AddressException ad) {
                ad.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
