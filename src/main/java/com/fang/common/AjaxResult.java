package com.fang.common;

import java.util.HashMap;

/**
 * 操作消息提醒
 * @author FPH
 * @since 2022年4月28日04:34:08
 */
public class AjaxResult extends HashMap<String,Object> {
    /**
     * 状态码
     */
    public static final String CODE_TAG="code";
    /**
     * 返回消息
     */
    public static final String MSG_TAG="msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG="data";

    public AjaxResult() {
    }

    public AjaxResult(Integer code,String msg){
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
    }

    public AjaxResult(Integer code,String msg,Object data){
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
        if (data!=null){
            super.put(DATA_TAG,data);
        }
    }
//成功的：
    public static AjaxResult success(String msg,Object data){
        return new AjaxResult(HttpStatus.SUCCESS,msg,data);
    }

    public static AjaxResult success(String msg){
        return AjaxResult.success(msg,null);
    }

    public static AjaxResult success(){
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(Object data){
        return AjaxResult.success("操作成功",data);
    }

//失败的：

    public static AjaxResult error(Integer code,String msg,Object data){
        return new AjaxResult(code,msg,data);
    }

    public static AjaxResult error(String msg){
        return AjaxResult.error(msg,null);
    }

    public static AjaxResult error(Integer code,String msg){
        return AjaxResult.error(code,msg,null);
    }

    public static AjaxResult error(String msg,Object data){
        return AjaxResult.error(HttpStatus.ERROR,msg, data);
    }

    @Override
    public Object put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
