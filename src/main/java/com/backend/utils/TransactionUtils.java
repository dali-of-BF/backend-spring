//package com.backend.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.transaction.TransactionManager;
//
///**
// * @author FPH
// * @since 2023年7月24日11:24:20
// * 事物状态查看工具类
// */
//@Slf4j
//public abstract class TransactionUtils {
//    /**
//     * https://docs.oracle.com/javaee/5/api/javax/transaction/Status.html
//     *
//     * @return
//     */
//    public static String traceTransationState() {
//        String rStatus = null;
//        try {
//            int status = SpringUtils.getBean(TransactionManager.class).getStatus();
//            if (Status.STATUS_ACTIVE == status) {
//                rStatus = "ACTIVE";// 活动
//            } else if (Status.STATUS_MARKED_ROLLBACK == status) {
//                rStatus = "MARKED_ROLLBACK";// 标记回滚
//            } else if (Status.STATUS_PREPARED == status) {
//                rStatus = "PREPARED";// 已准备
//            } else if (Status.STATUS_COMMITTED == status) {
//                rStatus = "COMMITTED";// 已提交
//            } else if (Status.STATUS_ROLLEDBACK == status) {
//                rStatus = "ROLLEDBACK";// 已退回
//            } else if (Status.STATUS_UNKNOWN == status) {
//                // A transaction is associated with the target object but its current status cannot be determined.
//                rStatus = "UNKNOWN";// 未知
//            } else if (Status.STATUS_NO_TRANSACTION == status) {
//                // No transaction is currently associated with the target object.
//                rStatus = "NO_TRANSACTION";// 没有事务
//            } else if (Status.STATUS_PREPARING == status) {
//                rStatus = "PREPARING";// 准备中（中间状态）
//            } else if (Status.STATUS_COMMITTING == status) {
//                rStatus = "COMMITTING";// 提交中（中间状态）
//            } else if (Status.STATUS_ROLLING_BACK == status) {
//                rStatus = "ROLLING_BACK";// 回滚中（中间状态）
//            }
//            // logger.error("");
//        } catch (SystemException ex) {
//            logger.error(ex.getMessage(), ex);
//        }
//        return rStatus;
//    }
//}