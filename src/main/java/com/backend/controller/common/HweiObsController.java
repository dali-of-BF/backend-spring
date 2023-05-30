package com.backend.controller.common;


import cn.hutool.core.io.file.FileNameUtil;
import com.backend.common.result.Result;
import com.backend.exception.BusinessException;
import com.backend.utils.HweiObsUtils;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author FPH
 * @since 2022年11月10日10:38:03
 */
@RestController
@RequestMapping("/common")
@Api(tags = "华为OBS上传下载")
@RequiredArgsConstructor
@Slf4j
public class HweiObsController {

    private final HweiObsUtils hweiObsUtils;

//    @PostMapping("/file")
//    @ApiOperation("文件上传(自动获取文件名)")
//    public ResponseEntity<List<String>> uploadFiles(@RequestPart("files")List<MultipartFile> file) {
//        List<String> fileNames = new ArrayList<>();
//        for (MultipartFile multipartFile : file) {
//            String filename = multipartFile.getOriginalFilename();
//            if (filename.length() > 200) {
//                throw new BusinessException("文件名过长，请修改文件名！");
//            }
//            this.hweiObsUtils.fileUpload(multipartFile, filename);
//            fileNames.add(filename);
//        }
//        return ResponseEntity.ok(fileNames);
//    }

    @PostMapping("/file-up-load")
    @ApiOperation("文件上传(自动获取文件名)")
    public ResponseEntity<Map<String, String>> uploadFilesRandomName(@RequestPart("file")List<MultipartFile> file) {
        if (CollectionUtils.isEmpty(file)) {
            throw new BusinessException("文件不可为空");
        }
        Map<String, String> result = Maps.newHashMap();
        for (MultipartFile multipartFile : file) {
            String filename = multipartFile.getOriginalFilename();
            if (filename.length() > 200) {
                throw new BusinessException("文件名过长，请修改文件名！");
            }
            String ext = FileNameUtil.getSuffix(filename);
            if(StringUtils.isNotBlank(ext)){
                ext= "."+ext;
            }
            String randomFileName = UUID.randomUUID().toString().replace("-", "")+ext;
            this.hweiObsUtils.fileUpload(multipartFile, randomFileName);
            result.put(filename, randomFileName);
        }
        return ResponseEntity.ok(result);
    }
    //https://sport-exam.obs.cn-south-1.myhuaweicloud.com/dd9b9cab6f8244ed99b5d52fe29fb1c1.png
    @GetMapping("/file-down-load/{filename}")
    @ApiOperation("文件下载（预览）nginx转发")
    public Result<Object> downloadFileName(@PathVariable("filename") String filename) {
        return null;
    }
}
