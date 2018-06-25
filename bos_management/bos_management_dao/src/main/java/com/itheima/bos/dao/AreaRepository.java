package com.itheima.bos.dao;

import com.itheima.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tengchao
 * @create 2018-01-05-20:27.
 */
public interface AreaRepository extends JpaRepository<Area,String>{
    Area findAreaByProvinceAndCityAndDistrict(String province,String city,String district);
}
