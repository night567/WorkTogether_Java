package cn.edu.szu.company.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.company.service.DepartmentService;
import cn.edu.szu.company.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author zgr24
* @description 针对表【wt_department(部门)】的数据库操作Service实现
* @createDate 2024-04-28 16:39:41
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




