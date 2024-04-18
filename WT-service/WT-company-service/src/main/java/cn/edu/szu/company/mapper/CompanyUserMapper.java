package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.CompanyUser;
import cn.edu.szu.company.pojo.MemberDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyUserMapper extends BaseMapper<CompanyUser> {
    // 根据公司ID返回用户ID集合
    List<Long> selectUserIdsByCompanyId(Long companyId);

    boolean setMemberAsDeleted(Long memberId,Long companyId);

    List<MemberDTO> selectAllByCompanyId(Long companyId);
}
