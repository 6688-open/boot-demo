package com.dj.boot.controller.file;

import com.dj.boot.common.enums.file.FileType;
import com.dj.boot.common.enums.file.ImageEnum;
import com.dj.boot.common.file.FileUtil;
import com.dj.boot.common.util.UUIDUtil;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.controller.file.dto.JsonWriter;
import com.dj.boot.controller.file.util.FSObjectKeyUtil;
import com.dj.boot.controller.file.util.ThumbnailUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.file
 * @Author: wangJia
 * @Date: 2021-05-26-14-17
 */
@RestController
@RequestMapping("/file/")
public class FileController extends BaseController {


    /**
     * 上传
     * @param modelMap
     * @param file
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonWriter uploadFile(ModelMap modelMap, MultipartFile file, String mergeItemId, HttpServletRequest request) {
        JsonWriter jsonWriter = new JsonWriter();
        Map<String, String> map = new HashMap<String, String>();
        String originName = file.getOriginalFilename();
        String uuid = UUIDUtil.getTerseUuid();

        String objectKey = FSObjectKeyUtil.generateObjectKey(originName, uuid);
        String thumbObjectKey = FSObjectKeyUtil.generateObjectKeyWithThumbnail(originName, uuid);

        String url = FSObjectKeyUtil.getObjectURL(objectKey);
        url = "https://i.shangc.net/2017/0918/20170918095640882.jpg";
        String thumbUrl = FSObjectKeyUtil.getObjectURL(thumbObjectKey);
        thumbUrl = "https://i.shangc.net/2017/0918/20170918095640882.jpg";

        String fileTypeStr = FileUtil.getFileType(originName);
        String fileType = String.valueOf(FileType.FILE.getIndex());
        for (ImageEnum ft : ImageEnum.values()) {
            if (fileTypeStr.equalsIgnoreCase(ft.getName())) {
                fileType = String.valueOf(FileType.IMAGE.getIndex());
                break;
            }
        }
        try {
            //上传文件存储
            logger.error(FSObjectKeyUtil.FS_FILE_BUCKET_NAME+"-"+file.getInputStream()+"-"+objectKey);
            logger.error(FSObjectKeyUtil.FS_FILE_BUCKET_NAME+"-"+ThumbnailUtil.getThumbnailInputStream(file.getInputStream())+"-"+thumbObjectKey);
            jsonWriter.setSuccess(true);
            jsonWriter.setMsg("上传成功");

            map.put("name", originName);
            map.put("objectKey", objectKey);
            map.put("url", url);
            map.put("fileType", fileType);
            map.put("thumbUrl", thumbUrl);
            jsonWriter.setData(map);
        } catch (Exception e) {
            logger.error("上传失败：", e);
            jsonWriter.setSuccess(false);
            jsonWriter.setMsg("上传失败");
        }
        logger.info("uploadFile[name={}; URL={}", originName, url);
        return jsonWriter;
    }








    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JsonWriter deleteFile(ModelMap modelMap, String url) {
        logger.info("deleteFile[url={}]", url);
        JsonWriter jsonWriter = new JsonWriter();
        try {
            String objectKey = FSObjectKeyUtil.parseObjectKey(url);
            //删除原图
            //deleteFile

            //缩略图与原图的地址区别是在日期后面加了一个thumb
            String[] strArr = objectKey.split("/");
            String thumbObjectKey = strArr[0] + "/thumb/" + strArr[1];

            String fileType = FileUtil.getFileType(url);
            for (ImageEnum imageEnum : ImageEnum.values()) {
                if (imageEnum.name().toString().equalsIgnoreCase(fileType)) {
                    //删除缩略图
                }
            }
            jsonWriter.setSuccess(true);
            jsonWriter.setMsg("删除成功");

        } catch (Exception e) {
            jsonWriter.setSuccess(false);
            jsonWriter.setMsg("删除失败");
        }
        return jsonWriter;
    }
}
