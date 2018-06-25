package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.PromotionService;
import com.itheima.bos.web.action.base.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 宣传任务Action
 *
 * @author tengchao
 * @create 2018-01-27-14:00.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class PromotionAction  extends BaseAction<Promotion>{
    //属性驱动获取上传的文件
    private File titleImgFile;
    //属性驱动获取上传的文件名
    private String titleImgFileFileName;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }
    //执行保存方法
    @Autowired
    private PromotionService promotionService;
    @Action(value = "promotionAction_save",
        results = {@Result(location = "/pages/take_delivery/promotion.html",type = "redirect"),
                   @Result(name = "error",location = "error.html",type = "redirect")})
    public String save() {
        Promotion promotion = model;
        try {
            if (titleImgFile != null) {
                //获取保存文件的文件夹的绝对路径
                String saveDirPath = "upload";
                String saveDirRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);
                //获取文件后缀名
                String suffix = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
                //生成文件名
                String fileName = UUID.randomUUID().toString().replaceAll("-", "")
                        .toUpperCase() + suffix;
                //复制文件
                File file = new File(saveDirRealPath + File.separator + fileName);
                FileUtils.copyFile(titleImgFile, file);
                //设置封面图片保存的路径
                promotion.setTitleImg(ServletActionContext.getServletContext().getContextPath()
                            + "/upload/" + fileName);
            }
            //设置活动状态
            promotion.setStatus("1");
            //保存数据
            promotionService.save(promotion);
            return SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    //执行查询方法
    @Action("promotionAction_pageQuery")
    public String pageQuery() throws IOException {
        PageRequest pageRequest = new PageRequest(page - 1, rows);
        Page<Promotion> promotions = promotionService.pageQuery(pageRequest);
        this.toJson(promotions);
        return NONE;
    }
}
