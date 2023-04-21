package com.backend.controller.resource;

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
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        sysSourceService.doRefreshResource();
    }
}
