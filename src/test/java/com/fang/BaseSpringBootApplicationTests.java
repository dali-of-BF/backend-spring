package com.fang;

import com.fang.common.redis.RedisCache;
import com.fang.common.service.TokenService;
import com.fang.pojo.model.LoginUser;
import com.fang.utils.BeanUtil;
import com.fang.utils.IdUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BaseSpringBootApplicationTests {

    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() {
        try {
            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        String[] asd={"111","222","22233"};
        System.out.println(BeanUtil.test(asd));
    }

    @Resource
    private TokenService tokenService;
    @Test
    public void  testToken(){
//        Map<String, Object> map = new HashMap<>();
//        String uuid = UUID.randomUUID().toString();
//        System.out.println(uuid);
//        map.put("token", uuid);
//        String token = tokenService.createToken(map);
//        System.out.println(token);
//        System.out.println(tokenService.parseToken(token));
//        System.out.println(tokenService.parseToken(token).get("token"));
        String token = tokenService.createToken(new LoginUser());
        System.out.println(token);
        System.out.println(tokenService.parseToken(token).getSubject());
    }

    @Resource
    private RedisCache redisCache;
    @Test
    public void redisTest(){
        Object asdasd = redisCache.getCacheObject("asdasd");
        if(asdasd==null){
            System.out.println("111");
        }else{
            System.out.println("22222");
        }
        System.out.println("333333");
    }

    @Test
    public void UUIDTest(){
        System.out.println(IdUtils.fastUUID());
    }

}
