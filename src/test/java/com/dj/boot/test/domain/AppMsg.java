package com.dj.boot.test.domain;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-06-01-10-45
 */
public class AppMsg implements Serializable {
    private static final long serialVersionUID = 6993826603834989745L;
    private String token;
    private String title;
    private String content;
    private Integer type;
    private Integer totalNum;
    private Date sendTime;
    private Date expireTime;
    private Integer platform;
    private String url;
    private String imgUrl;
    private String pinStr;
    private String extras;
    private String resType;
    private Integer badge;
    private String sound;
    private Integer groupType;
    private Integer mutableContent;

    public AppMsg() {
    }

    private AppMsg(AppMsg.Builder builder) {
        this.token = builder.token;
        this.title = builder.title;
        this.content = builder.content;
        this.type = builder.type;
        this.totalNum = builder.totalNum;
        this.sendTime = builder.sendTime;
        this.expireTime = builder.expireTime;
        this.platform = builder.platform;
        this.url = builder.url;
        this.imgUrl = builder.imgUrl;
        this.pinStr = builder.pinStr;
        this.extras = builder.extras;
        this.resType = builder.resType;
        this.badge = builder.badge;
        this.sound = builder.sound;
        this.groupType = builder.groupType;
        this.mutableContent = builder.mutableContent;
    }

    public String getToken() {
        return this.token;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Integer getType() {
        return this.type;
    }

    public Integer getTotalNum() {
        return this.totalNum;
    }

    public Date getSendTime() {
        return this.sendTime;
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public Integer getPlatform() {
        return this.platform;
    }

    public String getUrl() {
        return this.url;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public String getPinStr() {
        return this.pinStr;
    }

    public String getExtras() {
        return this.extras;
    }

    public String getResType() {
        return this.resType;
    }

    public Integer getBadge() {
        return this.badge;
    }

    public String getSound() {
        return this.sound;
    }

    public Integer getGroupType() {
        return this.groupType;
    }

    public Integer getMutableContent() {
        return this.mutableContent;
    }

    public static class Builder {
        private String token;
        private String title;
        private String content;
        private Integer type;
        private Integer totalNum;
        private Date sendTime;
        private Date expireTime;
        private Integer platform;
        private String url;
        private String imgUrl;
        private String pinStr;
        private String extras;
        private String resType;
        private Integer badge;
        private String sound;
        private Integer groupType;
        private Integer mutableContent;

        public Builder() {

        }

        public Builder(String token, String title, String content, Integer type) {
            this.token = token;
            this.title = title;
            this.content = content;
            this.type = type;
        }

        public AppMsg.Builder totalNum(Integer totalNum) {
            this.totalNum = totalNum;
            return this;
        }

        public AppMsg.Builder sendTime(Date sendTime) {
            this.sendTime = sendTime;
            return this;
        }

        public AppMsg.Builder expireTime(Date expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public AppMsg.Builder platform(Integer platform) {
            this.platform = platform;
            return this;
        }

        public AppMsg.Builder url(String url) {
            this.url = url;
            return this;
        }

        public AppMsg.Builder imgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public AppMsg.Builder pinStr(String pinStr) {
            this.pinStr = pinStr;
            return this;
        }

        public AppMsg.Builder extras(String extras) {
            this.extras = extras;
            return this;
        }

        public AppMsg.Builder resType(String resType) {
            this.resType = resType;
            return this;
        }

        public AppMsg.Builder badge(Integer badge) {
            this.badge = badge;
            return this;
        }

        public AppMsg.Builder sound(String sound) {
            this.sound = sound;
            return this;
        }

        public AppMsg.Builder groupType(Integer groupType) {
            this.groupType = groupType;
            return this;
        }

        public AppMsg.Builder mutableContent(Integer mutableContent) {
            this.mutableContent = mutableContent;
            return this;
        }

        public AppMsg build() {
            return new AppMsg(this);
        }
    }
}
