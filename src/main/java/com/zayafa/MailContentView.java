package com.zayafa;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;

/**
 * Created by 张云峰 on 2017/12/20.
 */

public class MailContentView implements IMailContentView {
//    菜单列表, 格式 menuName::clsName1::clsName2 如果 没有::分割则表式只有menuName
    private ArrayList<String> menus = null;
    private ArrayList<ArrayList<String>> dataList;

    MailContentView(ArrayList<String> menus, ArrayList<ArrayList<String>> dataList) {
        if (menus != null) {
            this.menus = menus;
        } else {
            this.menus = new ArrayList<String>();
        }

        if (dataList != null) {
            this.dataList = dataList;
        } else {
            this.dataList = new ArrayList<ArrayList<String>>();
        }
    }

    public void setMenus(ArrayList<String> menus) {
        this.menus = null;
        this.menus = menus;
    }

    public void addMenu(String menu) {
        if (this.menus == null) {
            throw new RuntimeErrorException(new Error("this.menus is null"));
        }
        this.menus.add(menu);
    }

    public void setDataList(ArrayList<ArrayList<String>> dataList) {
        this.dataList = null;
        this.dataList = dataList;
    }

    public void addDataItem(ArrayList<String> dataItem) {
        if (dataItem == null) {
            return;
        }
        this.dataList.add(dataItem);
    }



    // 构建邮件内容
    public String getView() {
        String view = "";
        view += this.buildTableStyle();
        view += this.buildTableView();
        return view;
    }

    private String buildTableStyle() {
        String style = "";
        style = "<style>th {\n" +
                "  padding: 5px 5px;\n" +
                "  min-width: 80px;\n" +
                "}\n" +
                "\n" +
                ".bussines-module {\n" +
                "  color: red;\n" +
                "}\n" +
                ".basic-info {\n" +
                "  color: orange;\n" +
                "}\n" +
                ".res-attr {\n" +
                "  color: green;\n" +
                "}\n" +
                ".check-datetime {\n" +
                "  color: blue;\n" +
                "}\n" +
                ".level-rules {\n" +
                "  color: purple;\n" +
                "}\n" +
                ".checker {\n" +
                "  color: pink;\n" +
                "}\n" +
                "\n" +
                ".result {\n" +
                "  color: red;\n" +
                "}\n" +
                "\n" +
                "tbody {\n" +
                "  font-size: 12px;\n" +
                "}\n" +
                "\n" +
                "tbody .c-fmt {\n" +
                "  color: #3c3c3c;\n" +
                "} </style>";
        return style;
    }

    private String buildTableView() {
        String tableView = "";
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=1 cellspacing=0><thead>");
        sb.append(this.buildTableMenuView());
        sb.append("</thead><tbody>");
        sb.append(this.buildTableBodyView());
        sb.append("</body></table>");
        tableView = sb.toString();
        return tableView;
    }

    private String buildTableBodyView() {
        String bodyView = "";
        if (this.dataList == null) {
            return bodyView;
        }
        StringBuilder sb = new StringBuilder();
        for (ArrayList<String> dataItem: this.dataList) {
            sb.append("<tr>");
            if (dataItem == null) {
                continue;
            }
            for (String itemValue: dataItem) {
                sb.append("<td>");
                sb.append(itemValue);
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        bodyView = sb.toString();
        return bodyView;
    }

    private String buildTableMenuView() {
        String menuView = "";
        if (this.menus == null) {
            return menuView;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        for (String menu : this.menus) {
            String menuName = "";
            String classes = "";
            String[] arr = menu.split("::");
            menuName = arr[0];
            for (int i = 1; i < arr.length; i++) {
                classes += arr[i];
                classes += " ";
            }
            sb.append("<th class=\"" + classes + "\">");
            sb.append(menuName);
            sb.append("</th>");
        }
        sb.append("</tr>");
        menuView = sb.toString();
        return menuView;
    }
}
