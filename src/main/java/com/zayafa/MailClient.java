package com.zayafa;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * Created by 张云峰 on 2017/12/21.
 */
public class MailClient {
    private IMailConfig mailConfig = null;
    private Properties properties = null;
    private Authenticator auth = null;
    private Session session = null;
    private MimeMessage message = null;

    public MailClient(IMailConfig config) {
        mailConfig = config;
        initMailClient();
    }

    // 重置邮件客户端
    public void resetMailClient(IMailConfig config) {
        mailConfig = config;
        initMailClient();
    }

    //    发送邮件
    public void sendMail(Message message) {
        if (message == null) {
            return;
        }
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            System.err.println("发送邮件失败：" + e.getMessage());
        }
    }

    // 发送邮件
    public void sendMail(String subject,
                         IMailContentView view) {
        this.message = null;
        this.message = new MimeMessage(this.session);
        try {
            // 设置邮件标题
            this.message.setSubject(subject);

            // 设置发送人
            this.message.setFrom(new InternetAddress(mailConfig.fromEmail(),
                    mailConfig.fromUserName(), "UTF-8"));

            // 接收邮件的人
            ArrayList<String> toReceivers = this.mailConfig.toReceivers();
            for (String toMail : toReceivers) {
                this.message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toMail, "", "UTF-8"));
            }

            // 抄送人
            ArrayList<String> ccReceivers = this.mailConfig.ccReceivers();
            for (String ccMail : ccReceivers) {
                this.message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(ccMail, "", "UTF-8"));
            }

            // 邮件内容
            this.message.setContent(view.getView(), "text/html;charset=UTF-8");

            // 设置邮件显示的发送时间
            message.setSentDate(new Date());
            message.saveChanges();
            Transport.send(message);

        } catch (UnsupportedEncodingException e) {
            System.err.println("不支持邮箱地址的编码" + e.getMessage());
        } catch (MessagingException e) {
            System.err.println("发送邮件失败：" + e.getMessage());
        } catch (Exception e) {
            System.err.println("发送邮件失败：" + e.getMessage());
        }
    }

    // 接收邮件
    public ArrayList<String> receiveMails() {
        ArrayList<String> mails = new ArrayList<String>();

        return mails;
    }

    // 初始化邮件客户端
    private void initMailClient() {
        if (mailConfig == null) {
            throw new ExceptionInInitializerError("Please init mailConfig first");
        }
        this.initMailProperties();
        this.initMailAuth();
        this.initMailSession();
    }

    private void initMailProperties() {
        if (mailConfig == null) {
            throw new ExceptionInInitializerError("Please init mailConfig first");
        }
        this.properties = null;
        this.properties = new Properties();
        this.properties.put("mail.smtp.host", mailConfig.smtpHost());
        this.properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        this.properties.put("mail.smtp.socketFactory.port", mailConfig.smtpPort());
        this.properties.put("mail.smtp.auth", true);
        this.properties.put("mail.smtp.port", mailConfig.smtpPort());
    }

    private void initMailAuth() {
        if (mailConfig == null) {
            throw new ExceptionInInitializerError("Please init mailConfig first");
        }
        this.auth = null;
        this.auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.fromEmail(), mailConfig.fromEmailPasswd());
            }
        };
    }

    private void initMailSession() {
        if (mailConfig == null) {
            throw new ExceptionInInitializerError("Please init mailConfig first");
        }
        session = Session.getInstance(this.properties, this.auth);
        session.setDebug(true);
    }



}
