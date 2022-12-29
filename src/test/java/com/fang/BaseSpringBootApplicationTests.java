package com.fang;

import com.fang.domain.vo.ImagesTestList;
import com.fang.domain.vo.TestResultVO;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaseSpringBootApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 加密
     */
    @Test
    void encrypt(){
        System.out.println(stringEncryptor.decrypt("bfjILfCxGFVDa+zyvyO0fZ2SLv2H8VzE1XAf0rb6m7J/71ZXrjlahVoTm5FD2Zvh"));
    }

    @Test
    void testClass(){
        TestResultVO testResultVO = new TestResultVO();
        testResultVO.setId("1");
        TestResultVO.ImagesList imagesList = new TestResultVO.ImagesList();
        imagesList.setName("asd");
        imagesList.setUrl("zxc");
        ImagesTestList imagesTestList = new ImagesTestList();
        imagesTestList.setName("asd1");
        imagesTestList.setUrl("zxc1");
        testResultVO.setImagesList(imagesList);
        testResultVO.setTwo(imagesTestList);
        System.out.println(testResultVO);
    }
}
