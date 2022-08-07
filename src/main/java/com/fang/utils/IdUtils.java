package com.fang.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author FPH
 * @since 2022年6月6日15:46:23
 */
public class IdUtils {

    /**
     * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     * @return
     */
    public static String fastUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
