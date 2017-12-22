package com.zayafa;

import java.io.*;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

/**
 * Created by 张云峰 on 2017/12/21.
 */
public class MailConfigResolver implements IMailConfig {

    private String configPath = null;
    private MailConfigBean mailConfigBean = null;

    public MailConfigResolver(String configPath) {
        this.configPath = configPath;
    }

    // 获取配置的文件
    public IMailConfig resolve() {
        String jsonString = this.readFileContent(this.configPath);
        this.mailConfigBean = JSON.parseObject(jsonString, MailConfigBean.class);
        return this;
    }

    private String readFileContent(String filePath) {
        File file = new File(filePath);
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            //br = new BufferedReader(new FileReader(file));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println("MailConfig 文件没有找到: " + configPath);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void guard() {
        if (this.mailConfigBean == null) {
            throw new RuntimeException("this.mailConfigBean is null, please call resolve method first");
        }
    }

    public String fromEmail() {
        this.guard();
        String fromEmail = this.mailConfigBean.getFromEmail();
        return fromEmail;
    }

    public String fromEmailPasswd() {
        this.guard();
        String fromEmailPasswd = this.mailConfigBean.getFromEmailPasswd();
        return fromEmailPasswd;
    }

    public ArrayList<String> toReceivers() {
        this.guard();
        ArrayList<String> toReceivers = (ArrayList<String>) this.mailConfigBean.getToReceivers();
        return toReceivers;
    }

    public ArrayList<String> ccReceivers() {
        this.guard();
        ArrayList<String> ccReceivers = (ArrayList<String>) this.mailConfigBean.getCcReceivers();
        return ccReceivers;
    }

    public String fromUserName() {
        this.guard();
        String userName = this.mailConfigBean.getFromUserName();
        return userName;
    }

    public String smtpHost() {
        this.guard();
        String host = this.mailConfigBean.getSmtpHost();
        return host;
    }

    public int smtpPort() {
        this.guard();
        int port = this.mailConfigBean.getSmtpPort();
        return port;
    }
}
