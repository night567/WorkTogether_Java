package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.domain.CompanyUser;
import cn.edu.szu.company.pojo.MemberDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyUserMapper extends BaseMapper<CompanyUser> {
    // 根据公司ID返回用户ID集合
    List<Long> selectUserIdsByCompanyId(Long companyId);

    boolean setMemberAsDeleted(Long memberId,Long companyId);

    List<MemberDTO> selectAllByCompanyId(Long companyId);

    @Select("select t.user_id as id,d.name as deptName from wt_company_user t,wt_department d " +
            "where t.company_id = #{companyId} and t.dept_id = #{deptId} and t.dept_id = d.id")
    List<MemberDTO> selectAllByCompanyIdAndDeptId(Long companyId,Long deptId);
}
