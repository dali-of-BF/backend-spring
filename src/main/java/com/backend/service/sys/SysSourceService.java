package com.backend.service.sys;

import com.backend.annotation.Log;
import com.backend.constants.RedisConstants;
import com.backend.constants.SwaggerGroupConstants;
import com.backend.domain.entity.sys.SysResource;
import com.backend.enums.sys.DeletedEnum;
import com.backend.mapper.sys.SysResourceMapper;
import com.backend.utils.ClassUtils;
import com.backend.utils.RedisUtils;
import com.backend.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FPH
 * @since 2023年4月15日22:26:14
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysSourceService extends ServiceImpl<SysResourceMapper, SysResource> {

    private final DocumentationCache documentationCache;
    private final ServiceModelToSwagger2Mapper serviceModelToSwagger2Mapper;
    private final RedisUtils redisUtils;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    public List<SysResource> doRefreshResource() throws IllegalAccessException {
        StopWatch stopWatch = new StopWatch();
        List<String> allFieldValue = ClassUtils.getAllFieldValue(SwaggerGroupConstants.class);
        //排除测试API
        allFieldValue.remove(SwaggerGroupConstants.TEST_API);
        stopWatch.start("扫描接口资源");
        List<SysResource> resources = getResourceByGroupNameList(allFieldValue);
        stopWatch.stop();
        log.info("===> 扫描资源结束，耗时：{} 毫秒，总条数：{}", stopWatch.getLastTaskTimeMillis(), resources.size());
        List<SysResource> sysResources = addOrRemove(resources);
        //插入到redis中
        redisUtils.deleteObject(RedisConstants.SOURCE_KEY);
        //删除redis后再set集合，否则会不断累加个数
        redisUtils.setCacheSet(RedisConstants.SOURCE_KEY, new HashSet<>(sysResources));
        log.info("sysResource已更新:\n{}", sysResources);
        return sysResources;
    }

    /**
     * 针对扫描出来的sysResource信息进行相对应的添加修改与删除
     *
     * @param sysResources
     */
    @Transactional(rollbackFor = Exception.class)
    public List<SysResource> addOrRemove(List<SysResource> sysResources) {
        List<SysResource> dataResource = this.list();
        sysResources.forEach(i -> {
            SysResource sysResource = dataResource.stream().filter(j -> j.getResourceUrl().equals(i.getResourceUrl()))
                    .findFirst().orElse(null);
            i.setId(Objects.nonNull(sysResource) ? sysResource.getId() : null);
            //处理APPID
            String resourceUrl = i.getResourceUrl();
            //获取前缀，保存在appId中
            String substring = resourceUrl.substring(contextPath.length() + 1);
            String appId = substring.substring(0, substring.indexOf("/"));
            i.setAppId(appId);
        });
        List<String> removeUrlList = dataResource.stream()
                .map(SysResource::getResourceUrl)
                .collect(Collectors.toList());

        //修改或保存资源表
        this.saveOrUpdateBatch(sysResources);

        removeUrlList.removeAll(sysResources.stream()
                .map(SysResource::getResourceUrl)
                .collect(Collectors.toList()));
        //此时多余出来的集合为需要被删除的部分
        if (CollectionUtils.isNotEmpty(removeUrlList)) {
            this.remove(new LambdaQueryWrapper<SysResource>().in(SysResource::getResourceUrl, removeUrlList));
        }

        return sysResources;
    }

    /**
     * 通过分组集合获取资源信息
     *
     * @param groupNameList
     * @return
     */
    public List<SysResource> getResourceByGroupNameList(List<String> groupNameList) {
        List<SysResource> resultList = Lists.newArrayList();
        groupNameList.forEach(i -> resultList.addAll(this.getResourceByGroupName(i)));
        return resultList;
    }

    /**
     * 根据分组获取接口资源信息
     *
     * @param groupName 分组名称
     *                  //     * @param ignoreList   忽略的uri集合
     *                  //     * @param ignorePrefix 忽略的uri前缀集合
     * @return 资源信息
     */
    @Log
    public List<SysResource> getResourceByGroupName(String groupName) {
        List<SysResource> result = Lists.newArrayList();
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        if (Objects.nonNull(documentation)) {
            Swagger swagger = this.serviceModelToSwagger2Mapper.mapDocumentation(documentation);
            if (Objects.nonNull(swagger)) {
                Map<String, Path> paths = swagger.getPaths();
                for (Map.Entry<String, Path> entry : paths.entrySet()) {
                    Path path = entry.getValue();
                    if (Objects.isNull(path)) {
                        continue;
                    }
                    String api = entry.getKey();
                    List<Operation> operations = path.getOperations();
                    Operation operation = operations.get(0);
                    SysResource resource = new SysResource();

                    resource.setResourceUrl(api);
                    resource.setRemark(operation.getSummary());
                    resource.setTag(StringUtils.substringBetween(operation.getTags().toString(),
                            StringPool.LEFT_SQ_BRACKET, StringPool.RIGHT_SQ_BRACKET));
                    resource.setResourceMethod(getMethod(path.getOperationMap()));
                    resource.setAppId(SecurityUtils.getAppId());
                    result.add(resource);
                }
            }
        }
        return result;
    }

    /**
     * 获取当前请求接口的方法
     */
    private String getMethod(Map<HttpMethod, Operation> operationMap) {
        StringBuilder method = new StringBuilder();
        if (null != operationMap) {
            for (Map.Entry<HttpMethod, Operation> entry : operationMap.entrySet()) {
                method.append(entry.getKey().toString()).append(StringPool.COMMA);
            }
        }
        return StringUtils.isNotBlank(method) ? (method.substring(0, method.length() - 1)) : method.toString();
    }

    /**
     * 获取resource表集合（先查缓存再查数据库）
     *
     * @return
     */
    public Set<SysResource> getResource() {
        Set<SysResource> sysResource = redisUtils.getCacheSet(RedisConstants.SOURCE_KEY);
        if (Objects.isNull(sysResource)) {
            Set<SysResource> list = new HashSet<>(this.list(new LambdaQueryWrapper<SysResource>()
                    .eq(SysResource::getDeleted, DeletedEnum.UNDELETED.getValue())));
            //插入redis
            redisUtils.setCacheSet(RedisConstants.SOURCE_KEY, list);
            return list;
        }
        return sysResource;
    }
}
