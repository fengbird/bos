package com.itheima.bos.web.action.take_delivery;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图片上传Action
 *
 * @author tengchao
 * @create 2018-01-27-11:23.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class ImageAction extends ActionSupport {
    private File imgFile;
    private String imgFileFileName;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    @Action("imageAction_upload")
    public String upload() throws IOException {
        Map<String, Object> map = new HashMap<>();
        //图片保存文件夹
        String saveDir = "upload";
        //upload文件夹的完整路径
        String realPath = ServletActionContext.getRequest().getRealPath("upload");
        //获取文件后缀名
        String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
        //新的文件名称
        String newFileName = UUID.randomUUID().toString().replace("-","").toUpperCase()+suffix;
        //新的文件完整路径
        String newFilePath = realPath+File.separator+newFileName;
        //创建新的文件
        File file = new File(newFilePath);
        try {
            FileUtils.copyFile(imgFile,file);
            String contextPath = ServletActionContext.getRequest().getContextPath();
            String url = contextPath+File.separator+saveDir+File.separator+newFileName;
            map.put("error", 0);
            map.put("url", url);
        } catch (Exception e) {
            map.put("error", 1);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.getWriter().print(JSON.toJSONString(map));
        return NONE;
    }

    @Action("imageAction_manager")
    public String manager() throws IOException {
        //获取保存文件的绝对磁盘路径
        String saveDirPath = "upload";
        String saveDirRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);
        File saveDir = new File(saveDirRealPath);
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
        //遍历目录取文件信息
        List<Hashtable> fileList = new ArrayList<>();
        if (saveDir.listFiles() != null) {
            for (File file : saveDir.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
                            .toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo",Arrays.asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        //封装写回客户端的数据
        Map<String, Object> map = new HashMap<>();
        //指定所有文件的信息
        map.put("file_list", fileList);
        //指定保存文件的文件夹的路径
        map.put("current_url", ServletActionContext.getServletContext().getContextPath()
                + File.separator + saveDirPath + File.separator);
        //向客户端写回数据
        String json = JSON.toJSONString(map);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
}
