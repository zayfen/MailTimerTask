package com.zayafa;

import java.util.ArrayList;

/**
 * Created by 张云峰 on 2017/12/21.
 */
public interface IMailConfig {
    public String fromEmail();
    public String fromEmailPasswd();
    public ArrayList<String> toReceivers();
    public ArrayList<String> ccReceivers();
    public String fromUserName();
    public String smtpHost();
    public int smtpPort();
}
