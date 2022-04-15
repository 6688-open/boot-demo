package com.dj.boot.controller.file.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.io.*;

/**
 * 缩略图生成工具类
 */
public final class ThumbnailUtil {

    private static final int WIDTH = 114;

    private static final int HEIGHT = 74;

    private static final String IMAGE_TYPE = "jpg";

    private ThumbnailUtil() {
    }

    public static InputStream getThumbnailInputStream(InputStream is) {
        InputStream inputStream = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            Thumbnails.of(is).size(WIDTH, HEIGHT).outputFormat(IMAGE_TYPE).toOutputStream(os);
            inputStream = new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 转换输出流为输入流
     *
     * @param os
     * @return
     */
    public static InputStream convertOutputStreamToInputStream(OutputStream os) {
        InputStream inputStream = null;
        inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
        return inputStream;
    }

    public static String getAvatarUrl(String erpCode) {
        String objectKey = "avatar/" + erpCode + "/cut/" + MD5Util.md5Hex(erpCode) + ".jpg";
        String url = FSObjectKeyUtil.getObjectURL(objectKey);
        return url;
    }

    public static void getAvatarUrl(ModelMap modelMap, String erpCode) {
        String url = getAvatarUrl(erpCode);
        modelMap.put("avatar", url);
    }

    public static void getAvatarUrl(Model modelMap, String erpCode) {
        String url = getAvatarUrl(erpCode);
        modelMap.addAttribute("avatar", url);
    }

}
