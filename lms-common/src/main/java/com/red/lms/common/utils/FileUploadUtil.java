package com.red.lms.common.utils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @create: 2019-08-22 11:00
 **/
public class FileUploadUtil {

    /**
     * 上传文件工具类
     * @param uploadFile 文件
     * @param fileDir 文件目录名
     * @param request
     * @return
     */
    public static boolean uploadUtil(MultipartFile uploadFile,String fileDir, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String realPath = request.getSession().getServletContext().getRealPath("/upload/" + fileDir+"/");
        System.out.println("文件上传："+realPath);
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        //是否目录
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String filePath = "";

        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            //用于图片上传时，把内存中图片写入磁盘
            uploadFile.transferTo(new File(folder, newName));
            filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload/" +fileDir+"/" + format +"/"+ newName;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
