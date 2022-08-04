package com.dj.boot.common.mail;

import java.io.File;

/**
 * 发送邮件服务
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-08-04-09-39
 */
public interface EmailService {
    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param file 附件
     */
    void sendAttachmentsMail(String to, String subject, String content, File file);
}
