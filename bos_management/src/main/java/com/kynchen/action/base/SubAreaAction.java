package com.kynchen.action.base;/*
* @author kynchen
* 
* @date 2018/8/1 11:18
*
* @version idea
*/

import bos.domain.base.Area;
import bos.domain.base.FixedArea;
import bos.domain.base.SubArea;
import com.alibaba.fastjson.JSONArray;
import com.kynchen.action.common.BaseAction;
import com.kynchen.dao.base.AreaRepository;
import com.kynchen.service.base.SubAreaService;
import com.kynchen.utils.JsonUtils;
import com.kynchen.utils.PageForJson;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

import javax.persistence.criteria.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {

    @Autowired
    private SubAreaService subAreaService;
    @Autowired
    private AreaRepository areaRepository;

    /** 查询所有的分区信息
     * @return
     */
    @Action(value = "subArea_findAll")
    public String subArea_findAll(){
        List<SubArea> areas = subAreaService.findAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(areas);
        JsonUtils.write(jsonArray);
        return NONE;
    }

    /** 添加分区信息
     * @return
     */
    @Action(value = "subArea_addSubArea",results = {@Result(name="success",type="redirect",location = "./pages/base/take_time.html")})
    public  String subArea_addSubArea(){
        subAreaService.save(model);
        return SUCCESS;
    }

    /** 条件分页查询定区信息
     * @return
     */
    @Action(value = "subArea_queryPage")
    public String subArea_queryPage(){
        Pageable pageable = new PageRequest(page-1,rows);
        Specification<SubArea> specification = new Specification<SubArea>() {
            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Join<Object, Object> area = root.join("area", JoinType.INNER);

                if(model.getArea()!=null){
                    if(StringUtils.isNotBlank(model.getArea().getProvince())){
                        Predicate p1 = criteriaBuilder.like(area.get("province").as(String.class), "%"+model.getArea().getProvince()+"%");
                        predicates.add(p1);
                    }
                    if(StringUtils.isNotBlank(model.getArea().getCity())){
                        Predicate p2 = criteriaBuilder.like(area.get("city").as(String.class), "%"+model.getArea().getCity()+"%");
                        predicates.add(p2);
                    }
                    if(StringUtils.isNotBlank(model.getArea().getDistrict())){
                        Predicate p3 = criteriaBuilder.like(area.get("district").as(String.class), "%"+model.getArea().getDistrict()+"%");
                        predicates.add(p3);
                    }
                }
                if(StringUtils.isNotBlank(model.getKeyWords())){
                    Predicate p4 = criteriaBuilder.like(root.get("keyWords").as(String.class), "%"+model.getKeyWords()+"%");
                    predicates.add(p4);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<SubArea> page = subAreaService.subArea_findAll(specification,pageable);
        PageForJson.write(page);
        System.out.println(page.getContent());
        return NONE;
    }

    private File file;
    private String fileFileName;

    public void setFile(File file) {
        this.file = file;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    @Action(value="subArea_import")
    public String subArea_import(){
        List<SubArea> subAreas = new ArrayList<>();
        Workbook workbook=null;
        String flag="0";
            try {
                if(fileFileName.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(new FileInputStream(file));
                }else{
                    workbook = new XSSFWorkbook(new FileInputStream(file));
                }
                Sheet sheet = workbook.getSheetAt(0);
                for(Row row:sheet){
                    if(row.getRowNum()==0){
                        continue;
                    }
                    if(row.getCell(0)==null||StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                        continue;
                    }
                    SubArea subArea = new SubArea();

                    subArea.setId(row.getCell(0).getStringCellValue());



                    String province =row.getCell(1).getStringCellValue();
                    String city = row.getCell(2).getStringCellValue();
                    String distinct=row.getCell(3).getStringCellValue();
                    Area area =areaRepository.findByProvinceAndCityAndDistrict(province,city,distinct).get(0);
                    subArea.setArea(area);
                    subArea.setKeyWords(row.getCell(4).getStringCellValue());
                    subArea.setStartNum(row.getCell(5).getStringCellValue());
                    subArea.setEndNum(row.getCell(6).getStringCellValue());
                    subArea.setSingle(row.getCell(7).getStringCellValue().charAt(0));
                    subArea.setAssistKeyWords(row.getCell(8).getStringCellValue());

                    FixedArea fixedArea = new FixedArea();
                    fixedArea.setId(row.getCell(9).getStringCellValue());
                    subArea.setFixedArea(fixedArea);
                    subAreas.add(subArea);
                    flag="1";
                }
                subAreaService.saveBatch(subAreas);
                JsonUtils.write(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return NONE;
    }
}
