package com.dj.boot.common.mail;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.excel.EasyExcel;
import com.dj.boot.common.excel.easyExcel.User;
import com.dj.boot.common.file.FileCreateUtil;
import com.dj.boot.common.util.base.SystemUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述信息
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-08-04-11-36
 */
@Component
public class MailTest {

    @Autowired
    private EmailService emailService;

    public void sendEmail(List<String> billIds) {
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
            List<File> tempList = new ArrayList<>(2);
            tempList.add(file);

            // 压缩文件
            String zipPath = new StringBuffer().append(FileCreateUtil.TMP_DIR).append("账单信息").append(SystemUtils.uuid()).append(".zip").toString();
            File zipFile = ZipUtil.zip(FileUtil.file(zipPath),StandardCharsets.UTF_8, false, tempList.toArray(new File[tempList.size()]));
            //emailService.sendAttachmentsMail("1225328916@qq.com","账单信息压缩文件","账单内容见附件, 请查收", zipFile);

            FileCreateUtil.deleteFile(file);
            FileCreateUtil.deleteFile(zipFile);
        }

    }
}
