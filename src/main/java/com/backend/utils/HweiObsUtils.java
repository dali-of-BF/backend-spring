package com.backend.utils;

import com.backend.config.HweiObsConfig;
import com.obs.services.ObsClient;
import com.obs.services.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author FPH
 * @since 2022年11月10日10:33:03
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class HweiObsUtils {

    private final HweiObsConfig hweiObsConfig;


    /**
     * 文件上传
     *
     * @param uploadFile 上传的文件
     * @param filename   文件名称
     */
    public void fileUpload(MultipartFile uploadFile, String filename){
        ObsClient obsClient=null;
        try {
            //创建实例
            obsClient = hweiObsConfig.getInstance();
            //获取文件信息
            InputStream inputStream = uploadFile.getInputStream();
            UploadFileRequest request1 = new UploadFileRequest(hweiObsConfig.getBucketName(), filename);
            long available = inputStream.available();
            PutObjectRequest request = new PutObjectRequest(hweiObsConfig.getBucketName(), java.lang.String.valueOf(filename), inputStream);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(available);
            request.setMetadata(objectMetadata);
            //设置公共读
            request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
            PutObjectResult putObjectResult = obsClient.putObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //销毁实例
            hweiObsConfig.destroy(obsClient);
        }
    }
    /**
     * 获取文件上传进度
     * @param objectName
     * @return
     */
//    public FileUploadStatus getFileUploadPlan(String objectName){
//        ObsClient obsClient=null;
//        FileUploadStatus fileUploadStatus = new FileUploadStatus();
//        try {
//            obsClient=hweiOBSConfig.getInstance();
//            GetObjectRequest request = new GetObjectRequest(hweiOBSConfig.getBucketName(), objectName);
//            request.setProgressListener(new ProgressListener() {
//                @Override
//                public void progressChanged(ProgressStatus status) {
//                    //上传的平均速度
//                    fileUploadStatus.setAvgSpeed(status.getAverageSpeed());
//                    //上传的百分比
//                    fileUploadStatus.setPct(String.valueOf(status.getTransferPercentage()));
//                }
//            });
//            // 每下载1MB数据反馈下载进度
//            request.setProgressInterval(1024*1024L);
//            ObsObject obsObject = obsClient.getObject(request);
//            // 读取对象内容
//            InputStream input= obsObject.getObjectContent();
//            byte[] b = new byte[1024];
//            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
//            int len;
//            while ((len=input.read(b))!=-1 ){
//                //将每一次的数据写入缓冲区
//                byteArrayOutputStream.write(b,0,len);
//            }
//            byteArrayOutputStream.close();
//            input.close();
//            return fileUploadStatus;
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            hweiOBSConfig.destroy(obsClient);
//        }
//        return null;
//    }
}
