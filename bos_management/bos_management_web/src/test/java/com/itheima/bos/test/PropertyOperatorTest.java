package com.itheima.bos.test;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.utils.PropertyOperator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 当一个对象中属性为空串时将其替换为null，若该对象中包含有其他对象，而其他对象中的属性只有空串
 * 或null，那么将对象替换为null
 * @author tengchao
 * @create 2018-01-18-19:58.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
    @Slf4j
public class PropertyOperatorTest {
    /*@Autowired
    private SubareaService subareaService;*/
    @Test
    public void test1() {
        SubArea subArea = new SubArea();
        FixedArea fixedArea = new FixedArea();
        fixedArea.setCompany("");
        Area area = new Area();
        area.setCity("   ");
        area.setDistrict("");
        area.setProvince("");
        subArea.setArea(area);
        subArea.setFixedArea(fixedArea);
        PropertyOperator operator = new PropertyOperator();
        subArea = operator.replacePropertyFromEmptyToNull(subArea);
        log.debug(subArea.toString());
        log.error("这是一个SUVArea对象",subArea);
        log.info("这是一个SUVArea对象",subArea);
        log.warn("这是一个SUVArea对象",subArea);
        System.out.println("========================");
        /*Page<SubArea> subAreas = subareaService.queryPage(subArea, new PageRequest(0, 30));
        System.out.println(subAreas.getContent());*/
    }
    //@Test
    public void test2() {
        FixedArea fixedArea = new FixedArea();
        PropertyOperator propertyOperator = new PropertyOperator();
        boolean b = propertyOperator.objectIsEmpty(fixedArea);
        System.out.println(b);
        /*HashSet hs = new HashSet();
        System.out.println(hs.isEmpty());*/
    }
}
