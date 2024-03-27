package cn.edu.szu.auth.service;

import cn.edu.szu.auth.domain.AuthResource;
import feign.Param;

import java.util.List;

/**
 * @author 王奇艳
 * 资源service
 */
public interface AuthResourceService {
    /**
     * 分页查询所有资源
     * @param offset 查询起始位置
     * @param limit 查询数量
     * @return 资源列表
     */
    public List<AuthResource> selectAllResources(@Param("offset") int offset, @Param("limit") int limit);

}
