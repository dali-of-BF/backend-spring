package com.fang.config.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author FPH
 * mybatisPlus新增修改拦截
 * @since 2022年4月27日04:17:56
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdDate",new Date(),metaObject);
        this.setFieldValByName("createdBy","System",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("lastModifiedDate",new Date(),metaObject);
        this.setFieldValByName("lastModifiedBy","System",metaObject);
    }
}
