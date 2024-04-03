package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.domain.AuthResource;
import cn.edu.szu.auth.mapper.AuthResourceMapper;
import cn.edu.szu.auth.service.AuthResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王奇艳
 * 资源service实现类
 */
@Service
public class AuthResourceServiceImpl implements AuthResourceService {
    @Autowired
    private AuthResourceMapper authResourceMapper;

    /**
     * 分页查询所有资源
     * @param offset 查询起始位置
     * @param limit 查询数量
     * @return 资源列表
     */
    @Override
    public List<AuthResource> selectAllResources(int offset, int limit) {
         return authResourceMapper.selectAllResources(offset,limit);
    }

    /**
     * 根据资源名称模糊查询资源
     * @param name 资源名称
     * @return 资源列表
     */
    @Override
    public List<AuthResource> selectResourcesByNames(String name) {
        return authResourceMapper.selectResourcesByNames(name);
    }

}
