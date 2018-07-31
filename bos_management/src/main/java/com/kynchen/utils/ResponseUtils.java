package com.kynchen.utils;/*
* @author kynchen
* 
* @date 2018/7/14 15:18
*
* @version idea
*/

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtils {
    public static void write(HttpServletResponse response,Object josnString){
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(josnString.toString());
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
