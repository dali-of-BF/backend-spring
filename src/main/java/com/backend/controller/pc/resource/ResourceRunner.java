package com.backend.controller.pc.resource;

import com.backend.config.ApplicationProperties;
import com.backend.enums.properties.ResourceEnum;
import com.backend.service.sys.SysSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author FPH
 */
@Component
@RequiredArgsConstructor
public class ResourceRunner implements CommandLineRunner {
    private final SysSourceService sysSourceService;
    private final ApplicationProperties properties;
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        if(ResourceEnum.YES.getValue().equals(properties.getResource().getEnableResourceInit())){
            sysSourceService.doRefreshResource();
        }
    }
}
