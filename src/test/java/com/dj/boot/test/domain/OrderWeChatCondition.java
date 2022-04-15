package com.dj.boot.test.domain;

import java.io.Serializable;

/**
 * 条件构造器
 */
public class OrderWeChatCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String FONT_COLOR = "#173177";

    private String toUser;
    private String template_id;
    private String url;
    private OrderWeChatCondition.MiniProgram miniProgram;
    private OrderWeChatCondition.Data data;

    public OrderWeChatCondition() {
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MiniProgram getMiniProgram() {
        if(miniProgram == null){
            miniProgram = new MiniProgram();
        }
        return miniProgram;
    }

    public void setMiniProgram(MiniProgram miniProgram) {
        this.miniProgram = miniProgram;
    }

    public OrderWeChatCondition.Data getData() {
        if(data == null){
            data = new Data();
        }
        return data;
    }

    public void setData(OrderWeChatCondition.Data data) {
        this.data = data;
    }

    public static class MiniProgram{
        private String appId;
        private String pagePath;

        public MiniProgram() {
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPagePath() {
            return pagePath;
        }

        public void setPagePath(String pagePath) {
            this.pagePath = pagePath;
        }
    }

    public static class Data{
        private OrderWeChatCondition.DataMap first;
        private OrderWeChatCondition.DataMap HandleType;
        private OrderWeChatCondition.DataMap Status;
        private OrderWeChatCondition.DataMap RowCreateDate;
        private OrderWeChatCondition.DataMap LogType;
        private OrderWeChatCondition.DataMap remark;

        public Data() {
        }

        public DataMap getFirst() {
            return first;
        }

        public void setFirst(DataMap first) {
            this.first = first;
        }

        public void setFirst(String value){
            this.first = new DataMap();
            first.setValue(value);
        }

        public DataMap getHandleType() {
            return HandleType;
        }

        public void setHandleType(DataMap handleType) {
            HandleType = handleType;
        }

        public void setHandleType(String value){
            this.HandleType = new DataMap();
            HandleType.setValue(value);
        }

        public DataMap getStatus() {
            return Status;
        }

        public void setStatus(DataMap status) {
            Status = status;
        }

        public void setStatus(String value){
            this.Status = new DataMap();
            Status.setValue(value);
        }

        public DataMap getRowCreateDate() {
            return RowCreateDate;
        }

        public void setRowCreateDate(DataMap rowCreateDate) {
            RowCreateDate = rowCreateDate;
        }

        public void setRowCreateDate(String value){
            this.RowCreateDate = new DataMap();
            RowCreateDate.setValue(value);
        }

        public DataMap getLogType() {
            return LogType;
        }

        public void setLogType(DataMap logType) {
            LogType = logType;
        }

        public void setLogType(String value){
            this.LogType = new DataMap();
            LogType.setValue(value);
        }

        public DataMap getRemark() {
            return remark;
        }

        public void setRemark(DataMap remark) {
            this.remark = remark;
        }

        public void setRemark(String value){
            this.remark = new DataMap();
            remark.setValue(value);
        }
    }

    public static class DataMap{
        private String value;
        private String color;

        public DataMap() {
            this.color = FONT_COLOR;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
