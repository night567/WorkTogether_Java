package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.exception.CompanyNotFoundException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.company.domain.WtCompany;
import cn.edu.szu.company.service.WtCompanyService;
import cn.edu.szu.company.mapper.WtCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
* @author 86199
* @description 针对表【wt_company(企业)】的数据库操作Service实现
* @createDate 2024-04-16 16:59:21
*/
@Service
public class WtCompanyServiceImpl extends ServiceImpl<WtCompanyMapper, WtCompany>
    implements WtCompanyService{
    @Autowired
    WtCompanyMapper companyMapper;
    @Override
    public boolean updateCompany(WtCompany company) {

        int cnt = companyMapper.updateById(company);
        if(cnt == 0){
            throw new CompanyNotFoundException("更新失败，未找到公司");
        }
        return true;
    }

    @Override
    public boolean deleteCompany(Long id) {
        int cnt = companyMapper.deleteById(id);
        if(cnt == 0){
            throw new CompanyNotFoundException("更新失败，未找到公司");
        }
        return true;
    }

    @Override
    public boolean addCompany(WtCompany company) {
        try {
            companyMapper.insert(company);
        } catch (Exception e) {
            // 捕获异常并处理
            if (e instanceof DataIntegrityViolationException) {
                // 数据库约束异常处理
                throw new DataIntegrityViolationException("存在空字段，重新检查表单");
                // 其他处理逻辑...
            } else {
                // 其他异常处理
                System.out.println("其他异常：" + e.getMessage());
                // 其他处理逻辑...
            }
        }
        return true;
    }
}




