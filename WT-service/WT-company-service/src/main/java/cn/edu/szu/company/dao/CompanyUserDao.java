package cn.edu.szu.company.dao;

import cn.edu.szu.company.pojo.CompanyUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface CompanyUserDao extends BaseMapper<CompanyUser> {
    // 根据公司ID返回用户ID集合
    List<Long> selectUserIdsByCompanyId(Long companyId);
}
