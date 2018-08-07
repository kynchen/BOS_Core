package bos.domain.test;/*
* @author kynchen
* 
* @date 2018/8/4 20:08
*
* @version idea
*/

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerTest {

    @Test
    public void test1() throws Exception {
        //配置对象
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        //配置文件位置
        configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/template"));
        //生产模板
        Template template = configuration.getTemplate("hello.ftl");
        Map<String,Object> map = new HashMap<>();
        map.put("title","kynchen");
        map.put("message","alwayse love you");
        //输出
        template.process(map,new PrintWriter(System.out));
    }
}

