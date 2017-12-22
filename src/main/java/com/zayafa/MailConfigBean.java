package com.zayafa;

import java.util.List;

/**
 * Created by 张云峰 on 2017/12/21.
 */
public class MailConfigBean {
    private String fromEmail;
    private String fromEmailPasswd;
    private List<String> toReceivers;
    private List<String> ccReceivers;
    private String fromUserName;
    private String smtpHost;
    private int smtpPort;


    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromEmailPasswd() {
        return fromEmailPasswd;
    }

    public void setFromEmailPasswd(String fromEmailPasswd) {
        this.fromEmailPasswd = fromEmailPasswd;
    }

    public List<String> getToReceivers() {
        return toReceivers;
    }

    public void setToReceivers(List<String> toReceivers) {
        this.toReceivers = toReceivers;
    }

    public List<String> getCcReceivers() {
        return ccReceivers;
    }

    public void setCcReceivers(List<String> ccReceivers) {
        this.ccReceivers = ccReceivers;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }
}
