package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.pojo.domain.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
    int updateUserDept(Long uid,Long did);

}




