package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.AuthResource;
import cn.edu.szu.auth.service.AuthResourceService;
import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王奇艳
 * 资源controller
 */
@RestController
@RequestMapping("/resource")
public class AuthResourceController {
    @Autowired
    private AuthResourceService authResourceService;

    @GetMapping("/selectAllResources")
    public Result selectAllResources(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 计算查询的起始位置
        int offset = page * size;
        // 调用服务层方法进行分页查询
        List<AuthResource> resources = authResourceService.selectAllResources(offset, size);
        if(resources==null||resources.isEmpty())
            return new Result(Code.GET_ERR,null,"查询失败");
        return new Result(Code.GET_OK, resources, "查询成功！");
    }

}
