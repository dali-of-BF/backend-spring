package com.backend.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内部类测试
 * @author FPH
 * @since 2022年12月29日18:00:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultVO {
    private String id;

    private ImagesList imagesList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImagesList{
        private String url;

        private String name;
    }

    private ImagesTestList two;
}
