package com.zayafa;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 张云峰 on 2017/12/20.
 */

public class Main {
    public static int IntervalSeconds = 60; // unit: second
    public static String MailConfigPath = "./mail_config.json";
    public static int START_HOUR_OF_DAY = 14; // 24 hour format
    public static int END_HOUR_OF_DAY = 19; // 24hour format

    public static void TimerTask() throws Exception {
        Runnable r = new Runnable() {
            public void run() {
                if (Main.afterEndHourOfDay() || Main.beforeStartHourOfDay()) {
                    // now is not between StartTime and EndTime, so just return
                    return;
                }
                try {
                    IMailConfig config = new MailConfigResolver(Main.MailConfigPath).resolve();
                    MailClient mc = new MailClient(config);

                    IMailContentView view = Main.getMailContent();
                    mc.sendMail("测试邮件", view);
                } catch (Exception e) {
                    System.out.println("Send Email Failed: " + e.getMessage());
                }
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        long delay = getServiceDelay();
        service.scheduleAtFixedRate(r, delay, IntervalSeconds, TimeUnit.SECONDS);
    }

    private static boolean beforeStartHourOfDay() {
        boolean ret = false;
        Date now = new Date();

        Calendar endDate = new GregorianCalendar();
        endDate.set(Calendar.HOUR_OF_DAY, START_HOUR_OF_DAY);
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.MILLISECOND, 0);
        Date dtStartClock = endDate.getTime();
        ret = now.before(dtStartClock);
        return ret;
    }


    private static boolean afterEndHourOfDay() {
        boolean ret = false;
        Date now = new Date();

        Calendar endDate = new GregorianCalendar();
        endDate.set(Calendar.HOUR_OF_DAY, END_HOUR_OF_DAY);
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.MILLISECOND, 0);
        Date dtEndClock = endDate.getTime();
        ret = now.after(dtEndClock);
        return ret;
    }


    private static long getServiceDelay() {
        long delay = 0;
        Date now = new Date();
        Calendar startDate = new GregorianCalendar();
        startDate.set(Calendar.HOUR_OF_DAY, START_HOUR_OF_DAY);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Date dtStartClock = startDate.getTime();

        // 判断当前时间是否超过dtStartClock点
        if (now.before(dtStartClock)) {
            delay = (dtStartClock.getTime() - now.getTime()) / 1000; // seconds
        }
        return delay;
    }

    // 构造MaincontentView对象, 这里只是一个例子
    public static IMailContentView getMailContent() {
        // 这里连接mysql获取数据
        ArrayList<String> menus = new ArrayList(Arrays.asList(new String[]{
                "序号", "业务模块::bussines-module", "资源类别::basic-info", "业务类别::basic-info", "资源IP::basic-info",
                "资源名称::basic-info", "资源描述::basic-info", "数据库类别::res-attr", "数据库名::res-attr", "查询SQL语句::res-attr",
                "巡检时间::check-datetime", "级别/规则配置::level-rules", "巡检人::checker", "检验结果::result"
        }));
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
        ArrayList<String> dataItem = new ArrayList<String>(Arrays.asList(new String[]{
                "1", "应用->BSS->订单中心->SQL记录查询监控", "", "动态号码激活工单", "137.0.0.103", "or_orderline_info", "or_orderline_info", "mysql", "obtsdb2",
                "select  *  from or_orderline_info  where  action_id='13621';", "2017/12/16  7:00:00", "重要：当查询结果有数据，且订单创建时间距离当前时间大于15视为异常",
                "毛璋", "正常"
        }));
        dataList.add(dataItem);
        IMailContentView view = new MailContentView(menus, dataList);
        return view;
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 1) {
            Main.MailConfigPath = args[1];
        }
        System.out.println("开始解析JSON配置文件： " + Main.MailConfigPath);
        Main.TimerTask();
    }

}
