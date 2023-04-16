package com.fang.service.sys;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fang.domain.entity.sys.SysResource;
import com.fang.utils.SecurityUtils;
import com.google.common.collect.Lists;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author FPH
 * @since 2023年4月15日22:26:14
 */
@Service
@RequiredArgsConstructor
public class SysSourceService {
    private final SwaggerResourcesProvider swaggerResourcesProvider;
    private final DocumentationCache documentationCache;
    private final ServiceModelToSwagger2Mapper serviceModelToSwagger2Mapper;

    public List<SwaggerResource> getResource(){
        return swaggerResourcesProvider.get();
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

                    resource.setResourceUri(api);
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
