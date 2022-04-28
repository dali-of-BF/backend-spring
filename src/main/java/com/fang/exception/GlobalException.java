package com.fang.exception;

/**
 * @author FPH
 * @since 2022年4月28日12:44:04
 */
public class GlobalException extends RuntimeException{

    /**
     * 错误提示
     */
    private String message;
    /**
     *错误明细
     */
    private String detailMessage;

    public GlobalException() {
    }

    public GlobalException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public GlobalException setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public GlobalException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}
