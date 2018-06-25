package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.utils.PropertyOperator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域Action
 *
 * @author tengchao
 * @create 2018-01-05-18:00.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class AreaAction extends BaseAction<Area>{

    @Autowired
    private AreaService areaService;
    //接收要上传的Excel文件
    private File areaFile;

    public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }

    //分页带条件查询数据
    @RequiresPermissions({"delete"})
    @Action("areaAction_pageQuery")
    public String queryPage() throws Exception {
        PropertyOperator propertyOperator = new PropertyOperator();
        model = propertyOperator.replacePropertyFromEmptyToNull(model);
        Page<Area> areas = areaService.queryPage(model,new PageRequest(page - 1, rows));
        toJson(areas,"subareas");
        return NONE;
    }

    //查询所有数据
    @Action("areaAction_listajax")
    public String listajax() {
        List<Area> areas = areaService.findAll();
        try {
            toJson(areas,"subareas");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    //从Excel表格中导入数据
    @Action("areaAction_importXls")
    public String importXls() {
        List<Area> areas = new ArrayList<>();
        try {
            //读取Excel文件对象
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(areaFile));
            //获取文件对象中的第一个sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            //遍历sheet获取每一行数据
            for (Row cells : sheet) {
                //跳过第一行(表头)
                if (cells.getRowNum() == 0) {
                    continue;
                }
                //跳过空行
                if(cells.getCell(0)==null || StringUtils.isBlank(cells.getCell(0).getStringCellValue())){
                    continue;
                }
                Area area = new Area();
                area.setId(cells.getCell(0).getStringCellValue());
                area.setProvince(cells.getCell(1).getStringCellValue());
                area.setCity(cells.getCell(2).getStringCellValue());
                area.setDistrict(cells.getCell(3).getStringCellValue());
                area.setPostcode(cells.getCell(4).getStringCellValue());
                //基于pinyin4j生成城市编码和简码
                //去除名称中的"市","区"
                String province = area.getProvince();
                String city = area.getCity();
                String district = area.getDistrict();
                province = province.substring(0, province.length() - 1);
                city = city.substring(0, city.length() - 1);
                district = district.substring(0, district.length() - 1);
                //生成简码
                String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
                StringBuffer buffer = new StringBuffer();
                for (String headStr : headArray) {
                    buffer.append(headStr);
                }
                String shortcode = buffer.toString();
                area.setShortcode(shortcode);
                //城市编码
                String citycode = PinYin4jUtils.hanziToPinyin(city, "");
                area.setCitycode(citycode);
                areas.add(area);
            }
            //调用业务层
            areaService.saveBatch(areas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    @Autowired
    private DataSource dataSource;
    //导出PDF
    @Action("areaAction_exportPDF")
    public String exportPDF() throws JRException, SQLException, IOException {
        //读取jrxml文件
        String jrxml = ServletActionContext.getServletContext().getRealPath("jr/report1.jrxml");
        HttpServletResponse response = ServletActionContext.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        //设置相应参数,以附件形式保存PDF
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="
                + URLEncoder.encode("工作单.pdf","utf-8"));
        ServletActionContext.getRequest().getHeader("user-agent");
        //使用JRPdfExproter导出器导出PDF
        JRPdfExporter exporter = new JRPdfExporter();
        //准备需要数据
        Map<String,Object> parameters = new HashMap<String,Object>();
        JasperReport report = JasperCompileManager.compileReport(jrxml);
        Connection connection = dataSource.getConnection();
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outputStream);
        exporter.exportReport();//导出
        outputStream.close();//关闭流
        return NONE;
    }
}
