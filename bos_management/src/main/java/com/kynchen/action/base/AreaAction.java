package com.kynchen.action.base;/*
* @author kynchen
* 
* @date 2018/7/27 21:42
*
* @version idea
*/

import com.alibaba.fastjson.JSONArray;
import com.kynchen.action.common.BaseAction;
import com.kynchen.domain.base.Area;
import com.kynchen.service.base.AreaService;
import com.kynchen.utils.JsonUtils;
import com.kynchen.utils.PageForJson;
import com.kynchen.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

    @Autowired
    private AreaService areaService;

    private File file;
    private String fileFileName;
    public void setFile(File file) {
        this.file = file;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    /** 解析Excel文件
     * @return
     * @throws Exception
     */
    @Action(value = "area_ocupload",results = {@Result(name="success",location = "./pages/base/area.html",type = "redirect")})
    public String area_ocupload() throws Exception {
        List<Area> areas = new ArrayList<>();
        if(fileFileName.endsWith(".xls")){
            //.xls使用HSSF
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            for (Row row:sheet
                 ) {
                //如果是第一行 则跳过
                if(row.getRowNum()==0){
                    continue;
                }
                //如果出现空行，跳过
                if(row.getCell(0)==null||StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                    continue;
                }
                Area area1 = new Area();
                area1.setId(row.getCell(0).getStringCellValue());
                area1.setProvince(row.getCell(1).getStringCellValue());
                area1.setCity(row.getCell(2).getStringCellValue());
                area1.setDistrict(row.getCell(3).getStringCellValue());
                area1.setPostcode(row.getCell(4).getStringCellValue());


                //简码
                String province = area1.getProvince().substring(0,area1.getProvince().length()-1).toString();
                String city = area1.getCity().substring(0,area1.getCity().length()-1).toString();
                String district = area1.getDistrict().substring(0,area1.getDistrict().length()-1).toString();

                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
                StringBuffer stringBuffer = new StringBuffer();
                for (String head:headByString
                     ) {
                    stringBuffer.append(head);
                }
                area1.setShortcode(stringBuffer.toString());
                //城市编码
                area1.setCitycode(PinYin4jUtils.hanziToPinyin(city,""));
                areas.add(area1);
            }

            areaService.saveBatch(areas);
        }
        if(fileFileName.endsWith(".xlsx")){
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            for (Row row:sheet
                    ) {
                //如果是第一行 则跳过
                if(row.getRowNum()==0){
                    continue;
                }
                //如果出现空行，跳过
                if(row.getCell(0)==null||StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                    continue;
                }
                Area area1 = new Area();
                area1.setId(row.getCell(0).getStringCellValue());
                area1.setProvince(row.getCell(1).getStringCellValue());
                area1.setCity(row.getCell(2).getStringCellValue());
                area1.setDistrict(row.getCell(3).getStringCellValue());
                area1.setPostcode(row.getCell(4).getStringCellValue());


                //简码
                String province = area1.getProvince().substring(0,area1.getProvince().length()-1).toString();
                String city = area1.getCity().substring(0,area1.getCity().length()-1).toString();
                String district = area1.getDistrict().substring(0,area1.getDistrict().length()-1).toString();

                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
                StringBuffer stringBuffer = new StringBuffer();
                for (String head:headByString
                        ) {
                    stringBuffer.append(head);
                }
                area1.setShortcode(stringBuffer.toString());
                //城市编码
                area1.setCitycode(PinYin4jUtils.hanziToPinyin(city,""));
                areas.add(area1);
            }

            areaService.saveBatch(areas);
        }
        return NONE;
    }

    /** 条件分页查询
     * @return
     */
    @Action(value = "area_pageQuery")
    public String area_pageQuery(){
        //构造分页查询对象
        Pageable pageable = new PageRequest(page-1,rows);
        //构造条件查询对象
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                //身份模糊查询
                if (StringUtils.isNotBlank(model.getProvince())) {
                    Predicate p1 = criteriaBuilder.like(root.get("province").as(String.class), "%" + model.getProvince() + "%");
                    list.add(p1);
                }
                //市模糊查询
                if (StringUtils.isNotBlank(model.getCity())) {
                    Predicate p2 = criteriaBuilder.like(root.get("city").as(String.class), "%" + model.getCity() + "%");
                    list.add(p2);
                }
                //区模糊查询
                if (StringUtils.isNotBlank(model.getDistrict())) {
                    Predicate p3 = criteriaBuilder.like(root.get("district").as(String.class), "%" + model.getDistrict() + "%");
                    list.add(p3);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }

        };
        Page<Area> page = areaService.pageQuery(specification,pageable);
        //转换成json数据类型返回
        PageForJson.write(page);
        return NONE;

    }

    @Action(value = "area_findAll")
    public String area_findAll(){
        List<Area> areas = areaService.area_finaAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(areas);
        JsonUtils.write(jsonArray);
        return NONE;
    }
}
