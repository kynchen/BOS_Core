package com.kynchen.utils;/*
* @author kynchen
* 
* @date 2018/7/28 18:23
*
* @version idea
*/

import com.alibaba.fastjson.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class PageForJson {
    public static void write(Page page){
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        Map<String,Object> map =new HashMap();
        map.put("total",page.getTotalElements());
        map.put("rows",page.getContent());
        JSONObject jsonObject = new JSONObject(map);
        JsonUtils.write(jsonObject);
    }
}
