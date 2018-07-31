package com.kynchen.utils;/*
* @author kynchen
* 
* @date 2018/7/26 20:19
*
* @version idea
*/

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonUtils {
    public static void write(Object jsonString){
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(jsonString.toString());
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
