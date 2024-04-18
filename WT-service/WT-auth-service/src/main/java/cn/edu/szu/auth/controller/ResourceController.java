package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.AuthResource;
import cn.edu.szu.auth.service.AuthResourceService;
import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
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
@RequestMapping("/api/resource")
public class ResourceController {
    @Autowired
    private AuthResourceService authResourceService;

    /**
     * @author 王奇艳
     * @param page 第几页
     * @param size 每页记录条数
     * @return 资源列表
     */
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

    /**
     * 根据资源名称模糊查询资源
     *
     * @param name 资源名称
     * @return 包含查询结果的结果对象
     */
    @GetMapping("/selectResourcesByNames")
    public Result selectResourcesByNames(@RequestParam String name) {
        // 如果资源名称为空或者为空字符串，则返回带有错误代码和消息的结果对象
        if (name == null || name.isEmpty()) {
            return new Result(Code.GET_ERR, null, "查询失败，名称不能为空！");
        }

        // 去除字符串两端的空格
        name = name.trim();

        // 调用服务层方法查询资源列表
        List<AuthResource> authResources = authResourceService.selectResourcesByNames(name);

        // 如果查询结果为空列表，则返回带有错误代码和消息的结果对象
        if (authResources.isEmpty()) {
            return new Result(Code.GET_ERR, null, "查询失败!");
        }

        // 返回带有成功代码、资源列表和消息的结果对象
        return new Result(Code.GET_OK, authResources, "查询成功！");
    }



}
