package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author zgr24
 * @description 针对表【wt_department(部门)】的数据库操作Mapper
 * @createDate 2024-04-28 16:39:41
 * @Entity cn.edu.szu.company.pojo.domain.Department
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    List<DeptDTO> selectHighestDeptByCompanyId(Long companyId);

    List<DeptDTO> selectDeptsByParentId(Long parentId);

    @Update("UPDATE wt_company_user SET dept_id = #{did} WHERE user_id = #{uid}")
    int updateUserDept(Long uid, Long did);

    Long selectIdByName(String name);

    @Update("update wt_company_user set type = #{type} where user_id = #{uid} and dept_id = #{did} ")
    int updateUDPosition(Long uid, Long did, Long type);

    @Select("select wcu.user_id as id, wd.name as deptName, wt.type as position,wcu.join_time as joinTime  from wt_company_user as wcu,wt_department as wd ,wt_type as wt where" +
            " wcu.dept_id = #{deptId} and wcu.company_id = #{companyId} and wcu.dept_id = wd.id and wcu.type = wt.id")
    List<MemberDTO> getDeptMember(Long deptId, Long companyId);

    List<Department> selectDeptByCompanyId(Long companyId);

    @Select("select id from wt_type where type = #{position}")
    Long selectPositionId(String position);

    Boolean updateDept(DeptDTO deptDTO);

    List<MemberDTO> selectAllByCompanyId(Long companyId);
}




