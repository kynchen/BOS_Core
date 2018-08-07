package com.kynchen.action.take_delivery;/*
* @author kynchen
* 
* @date 2018/8/3 21:51
*
* @version idea
*/

import bos.domain.take_delivery.Promotion;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.take_delivery.PromotionService;
import com.kynchen.utils.PageForJson;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {

    @Autowired
    private PromotionService promotionService;

    private File upload;
    private String uploadFileName;

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /** 添加宣传
     * @return
     * @throws Exception
     */
    @Action(value = "promotion_save",results = {@Result(name = "success",type = "redirect",location = "./pages/take_delivery/promotion.html")})
    public String promotion_save() throws Exception {

        String savePath="F:/upload/";
        String url="/upload/";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid+uploadFileName.substring(uploadFileName.lastIndexOf("."));

        String desFile = savePath+fileName;
        FileUtils.copyFile(upload,new File(desFile));

        model.setTitleImg(url+fileName);

        promotionService.save(model);

        return SUCCESS;
    }
    @Action(value = "promotion_pageQuery")
    public String promotion_pageQuery(){

        Pageable pageable = new PageRequest(page-1,rows);
        Page<Promotion> page =promotionService.findAll(pageable);

        PageForJson.write(page);

        return NONE;
    }
}
