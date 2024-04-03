package cn.edu.szu.auth.mapper;

import cn.edu.szu.auth.domain.AuthResource;
import feign.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王奇艳
 * 资源Mapper
 */
@Repository
public interface AuthResourceMapper {
    /**
     * 分页查询所有资源
     * @param offset 查询起始位置
     * @param limit 查询数量
     * @return 资源列表
     */
    public List<AuthResource> selectAllResources(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据资源名称模糊查询资源
     * @param name 资源名称
     * @return 资源列表
     */
    public List<AuthResource> selectResourcesByNames(String name);
}
