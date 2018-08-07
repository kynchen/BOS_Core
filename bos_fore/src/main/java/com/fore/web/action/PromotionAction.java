package com.fore.web.action;/*
* @author kynchen
* 
* @date 2018/8/4 11:56
*
* @version idea
*/

import bos.domain.constans.Constans;
import bos.domain.page.PageBean;
import bos.domain.take_delivery.Promotion;
import com.alibaba.fastjson.JSONObject;
import com.fore.utils.JsonUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {

    /** 宣传活动分页
     * @return
     */
    @Action(value = "promotion_pageQuery")
    public String promotion_pageQuery(){

        PageBean<Promotion> pageBean=WebClient.create(Constans.BOS_MANAGEMENT_URL+"/services/promotionService/pageQuery?page="+page+"&rows="+rows).accept(MediaType.APPLICATION_JSON).get(PageBean.class);

        Map<String,Object> map =new HashMap<>();
        map.put("pageData",pageBean.getPageData());
        map.put("totalCount",pageBean.getTotalCount());
        JSONObject jsonObject = new JSONObject(map);
        JsonUtils.write(jsonObject);
        return NONE;
    }

    /** 使用freemarker引擎生成静态页面
     * @return
     * @throws Exception
     */
    @Action(value="promotion_showDetail")
    public String promotion_showDetail() throws Exception {

        //获取freemarker的绝对路径
        String path = ServletActionContext.getServletContext().getRealPath("/freemarker");

        File htmlFile = new File(path+"/"+model.getId()+".html");
        //如果文件不存在，则使用模板引擎freemarker生成html页面
        if(!htmlFile.exists()){
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(new File(ServletActionContext.getServletContext().getRealPath("/WEB-INF/freemarker_template")));
            Template template = configuration.getTemplate("promotion_detail.ftl");

            Promotion promotion=WebClient.create(Constans.BOS_MANAGEMENT_URL+"/services/promotionService/promotion/"+model.getId()).accept(MediaType.APPLICATION_JSON).get(Promotion.class);

            Map<String,Object> map =new HashMap<>();
            map.put("promotion",promotion);
            template.process(map,new OutputStreamWriter(new FileOutputStream(htmlFile),"utf-8"));
        }

        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        //获取流，写回html文件
        FileUtils.copyFile(htmlFile,ServletActionContext.getResponse().getOutputStream());

        return NONE;
    }
}
