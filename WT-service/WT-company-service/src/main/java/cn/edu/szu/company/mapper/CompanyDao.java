package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyDao extends BaseMapper<Company> {
    // 这里可以添加其他自定义的数据库操作，基本的CRUD操作已经由BaseMapper提供
}
