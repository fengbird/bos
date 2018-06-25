package com.itheima.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基类Action
 *
 * @author tengchao
 * @create 2018-01-05-21:23.
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    protected T model;
    public BaseAction() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        //获取类型第一个泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        try {
            model = modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.err.println("模型构造失败...");
        }
    }

    @Override
    public T getModel() {
        return model;
    }

    //接收分页查询参数
    protected int page;
    protected int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //将分页查询的结果数据以json字符串的形式返回前端
    protected void toJson(Page<T> pageData, String... excludes) throws IOException {
        Map<String, Object> map = new HashMap<>();
        long total = pageData.getTotalElements();
        List<T> rows = pageData.getContent();
        map.put("total", total);
        map.put("rows", rows);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(jsonObject);
    }

    //将List集合的结果数据以json字符串的形式返回前端
    protected void toJson(List lists, String... excludes) throws IOException {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONArray jsonArray = JSONArray.fromObject(lists, jsonConfig);
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(jsonArray);
    }
}
