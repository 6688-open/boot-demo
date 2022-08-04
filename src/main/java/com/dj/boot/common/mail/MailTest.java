package com.dj.boot.common.mail;

import com.alibaba.excel.EasyExcel;
import com.dj.boot.common.excel.easyExcel.User;
import com.dj.boot.common.file.FileCreateUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * 描述信息
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-08-04-11-36
 */
public class MailTest {

    @Autowired
    private EmailService emailService;

    private void sendEmail(List<String> billIds) {
        File file = FileCreateUtil.fileExcelCreate("账单详情");
        try {
            List<User> list = Lists.newArrayList();
            User e = new User();
            e.setName("wj");
            list.add(e);
            EasyExcel.write(file,User.class).sheet().doWrite(list);
            String content = "账单内容见附件, 请查收";
            emailService.sendAttachmentsMail("1225328916@qq.com","账单信息",content, file);
        } finally {
            FileCreateUtil.deleteFile(file);
        }

    }
}
