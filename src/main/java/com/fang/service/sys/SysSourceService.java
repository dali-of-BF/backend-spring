package com.fang.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.constants.SwaggerGroupConstants;
import com.fang.domain.entity.sys.SysResource;
import com.fang.mapper.sys.SysResourceMapper;
import com.fang.utils.ClassUtils;
import com.fang.utils.SecurityUtils;
import com.google.common.collect.Lists;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author FPH
 * @since 2023年4月15日22:26:14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysSourceService extends ServiceImpl<SysResourceMapper,SysResource> {

    private final DocumentationCache documentationCache;
    private final ServiceModelToSwagger2Mapper serviceModelToSwagger2Mapper;


    public List<SysResource> doRefreshResource() throws IllegalAccessException {
        StopWatch stopWatch = new StopWatch();
        List<String> allFieldValue = ClassUtils.getAllFieldValue(SwaggerGroupConstants.class);
        //排除测试API
        allFieldValue.remove(SwaggerGroupConstants.TEST_API);
        stopWatch.start("扫描接口资源");
        List<SysResource> resources = getResourceByGroupNameList(allFieldValue);
        stopWatch.stop();
        log.info("===> 扫描资源结束，耗时：{} 毫秒，总条数：{}", stopWatch.getLastTaskTimeMillis(),resources.size());
        return addOrRemove(resources);
    }

    /**
     * 针对扫描出来的sysResource信息进行相对应的添加修改与删除
     * @param sysResources
     */
    @Transactional(rollbackFor = Exception.class)
    public List<SysResource> addOrRemove(List<SysResource> sysResources){
        List<SysResource> dataResource = this.list();
        sysResources.forEach(i->{
            SysResource sysResource = dataResource.stream().filter(j -> j.getResourceUrl().equals(i.getResourceUrl()))
                    .findFirst().orElse(null);
            i.setId(Objects.nonNull(sysResource)?sysResource.getId():null);
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
        if(CollectionUtils.isNotEmpty(removeUrlList)) {
            this.remove(new LambdaQueryWrapper<SysResource>().in(SysResource::getResourceUrl, removeUrlList));
        }
        return sysResources;
    }

    /**
     * 通过分组集合获取资源信息
     * @param groupNameList
     * @return
     */
    public List<SysResource> getResourceByGroupNameList(List<String> groupNameList) {
        List<SysResource> resultList = Lists.newArrayList();
        groupNameList.forEach(i-> resultList.addAll(this.getResourceByGroupName(i)));
        return resultList;
    }

    /**
     * 根据分组获取接口资源信息
     *
     * @param groupName    分组名称
    //     * @param ignoreList   忽略的uri集合
    //     * @param ignorePrefix 忽略的uri前缀集合
     * @return 资源信息
     */
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
}
