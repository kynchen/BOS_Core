package com.kynchen.action.take_delivery;/*
* @author kynchen
* 
* @date 2018/8/3 19:25
*
* @version idea
*/

import com.alibaba.fastjson.JSONObject;
import com.kynchen.action.common.BaseAction;
import com.kynchen.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class ImageAction extends BaseAction<Object> {

    private File imgFile;
    private String imgFileFileName;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    @Action(value = "image_upload")
    public String image_upload() throws Exception {

        //绝对路径
        String savePath = ServletActionContext.getServletContext().getRealPath("/upload");
//        String savePath = "F:/upload";
        //相对路径
        String loadPath = ServletActionContext.getRequest().getContextPath()+"/upload/";
//        String loadPath ="/upload/";
        UUID uuid = UUID.randomUUID();

        String fileName = uuid+imgFileFileName.substring(imgFileFileName.lastIndexOf("."));

        String desFile = savePath+"/"+fileName;
        //保存图片
        FileUtils.copyFile(imgFile,new File(desFile));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error",0);
        jsonObject.put("url",loadPath+fileName);
        JsonUtils.write(jsonObject);
        return NONE;
    }

    @Action(value = "image_manage")
    public  String image_manage(){
        // 根目录路径，可以指定绝对路径，比如 d:/xxx/upload/xxx.jpg
        String rootPath = ServletActionContext.getServletContext().getRealPath(
                "/")
                + "upload/";
//        String rootPath = "F:/upload/";
        // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl = ServletActionContext.getRequest().getContextPath()
                + "/upload/";
//        String rootUrl ="/upload/";
        // 遍历目录取的文件信息
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        // 当前上传目录
        File currentPathFile = new File(rootPath);
        // 图片扩展名
        String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Map<String, Object> hash = new HashMap<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(
                            fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String> asList(fileTypes)
                            .contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
                                .lastModified()));
                fileList.add(hash);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("moveup_dir_path", "");
        result.put("current_dir_path", rootPath);
        result.put("current_url", rootUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        JSONObject jsonObject = new JSONObject(result);
        JsonUtils.write(jsonObject);
        return NONE;
    }
}
