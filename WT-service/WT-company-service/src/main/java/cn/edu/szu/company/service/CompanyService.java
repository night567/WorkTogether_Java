package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.domain.Company;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86199
* @description 针对表【wt_company(企业)】的数据库操作Service
* @createDate 2024-04-16 16:59:21
*/
public interface CompanyService extends IService<Company> {

    // 更新公司信息
    boolean updateCompany(Company company);

    boolean deleteCompany(Long id);

    boolean addCompany(Company company);

    Company selectCompany(Long id);

    public Company getCompanyById(Long id);

    List<Company> selectMyCompany(Long uid);
}
