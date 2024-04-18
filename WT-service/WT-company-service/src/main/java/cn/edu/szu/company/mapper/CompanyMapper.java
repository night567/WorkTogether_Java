package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.domain.WtCompany;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86199
* @description 针对表【wt_company(企业)】的数据库操作Mapper
* @createDate 2024-04-16 16:59:21
* @Entity cn.edu.szu.company.domain.WtCompany
*/
@Mapper
public interface CompanyMapper extends BaseMapper<WtCompany> {
}




