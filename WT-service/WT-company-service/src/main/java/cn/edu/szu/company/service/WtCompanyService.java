package cn.edu.szu.company.service;

import cn.edu.szu.company.domain.WtCompany;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86199
* @description 针对表【wt_company(企业)】的数据库操作Service
* @createDate 2024-04-16 16:59:21
*/
public interface WtCompanyService extends IService<WtCompany> {

    // 更新公司信息
    boolean updateCompany(WtCompany company);

    boolean deleteCompany(Long id);

    boolean addCompany(WtCompany company);

}
