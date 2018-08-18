package com.kynchen.action.report;/*
* @author kynchen
* 
* @date 2018/8/16 14:59
*
* @version idea
*/

import bos.domain.take_delivery.WayBill;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.take_delivery.WayBillService;
import com.kynchen.utils.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import java.util.List;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class ReportAction extends BaseAction<WayBill> {

    @Autowired
    private WayBillService wayBillService;

    @Action("report_exportXls")
    public String report_exportXls() throws Exception {

        List<WayBill> wayBills = wayBillService.findAllWayBills(model);

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("运单数据");

        //新增头行
        XSSFRow headRow = xssfSheet.createRow(0);
        headRow.createCell(0).setCellValue("运单编号");
        headRow.createCell(1).setCellValue("寄件人");
        headRow.createCell(2).setCellValue("寄件人电话");
        headRow.createCell(3).setCellValue("寄件人公司");
        headRow.createCell(4).setCellValue("寄件人详细地址");
        headRow.createCell(5).setCellValue("收件人");
        headRow.createCell(6).setCellValue("收件人电话");
        headRow.createCell(7).setCellValue("收件人公司");
        headRow.createCell(8).setCellValue("收件人详细地址");
        headRow.createCell(9).setCellValue("产品类型");
        headRow.createCell(10).setCellValue("配载要求");

        for(WayBill wayBill:wayBills){
            XSSFRow dataRow = xssfSheet.createRow(xssfSheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(wayBill.getWayBillNum());
            dataRow.createCell(1).setCellValue(wayBill.getSendName());
            dataRow.createCell(2).setCellValue(wayBill.getSendMobile());
            dataRow.createCell(3).setCellValue(wayBill.getSendCompany());
            dataRow.createCell(4).setCellValue(wayBill.getSendAddress());
            dataRow.createCell(5).setCellValue(wayBill.getRecName());
            dataRow.createCell(6).setCellValue(wayBill.getRecMobile());
            dataRow.createCell(7).setCellValue(wayBill.getRecCompany());
            dataRow.createCell(8).setCellValue(wayBill.getRecAddress());
            dataRow.createCell(9).setCellValue(wayBill.getSendProNum());
            dataRow.createCell(10).setCellValue(wayBill.getSignStatus());
        }
            xssfSheet.autoSizeColumn(0,true);
            xssfSheet.autoSizeColumn(1,true);
            xssfSheet.autoSizeColumn(2,true);
            xssfSheet.autoSizeColumn(3,true);
            xssfSheet.autoSizeColumn(4,true);
            xssfSheet.autoSizeColumn(5,true);
            xssfSheet.autoSizeColumn(6,true);
            xssfSheet.autoSizeColumn(7,true);
            xssfSheet.autoSizeColumn(8,true);
            xssfSheet.autoSizeColumn(9,true);
            xssfSheet.autoSizeColumn(10,true);

            //设置头信息
            ServletActionContext.getResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String filename="运单数据.xlsx";
            String agent = ServletActionContext.getRequest().getHeader("user-agent");
            filename=FileUtils.encodeDownloadFilename(filename,agent);
            ServletActionContext.getResponse().setHeader("Content-Disposition","attachment;filename=" + filename);
            ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
            xssfWorkbook.write(outputStream);
            xssfWorkbook.close();
            return NONE;
    }



}
