package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.TaketimeService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * 收派时间Action
 *
 * @author tengchao
 * @create 2018-01-07-10:19.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TaketimeAction extends BaseAction<TakeTime>{
    @Autowired
    private TaketimeService taketimeService;
    //查询所有数据
    @Action("taketimeAction_listajax")
    public String listajax() throws IOException {
        List<TakeTime> takeTimes = taketimeService.findAll();
        toJson(takeTimes);
        return NONE;
    }
}
