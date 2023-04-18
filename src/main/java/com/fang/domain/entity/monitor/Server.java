package com.fang.domain.entity.monitor;

import com.fang.utils.ArithUtils;
import com.fang.utils.TimeUtils;
import com.fang.utils.http.IpUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author FPH
 */
@Data
@Component
public class Server {

    private static final int OSHI_WAIT_SECOND = 1000;

    @ApiModelProperty("cpu")
    private Cpu cpu = new Cpu();

    @ApiModelProperty("JVM相关信息")
    private Jvm jvm = new Jvm();

    @ApiModelProperty("內存相关信息")
    private Mem mem = new Mem();

    @ApiModelProperty("系统相关信息")
    private Sys sys = new Sys();

    @ApiModelProperty("系统磁盘文件相关信息")
    private List<SysFile> sysFiles = new LinkedList<>();

    public void copyTo() throws Exception {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        setCpuInfo(hal.getProcessor());

        setMemInfo(hal.getMemory());

        setSysInfo();

        setJvmInfo();

        setSysFiles(si.getOperatingSystem());
    }

    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }

    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    /**
     * 设置服务器信息
     */
    private void setSysInfo() {
        Properties props = System.getProperties();
        sys.setComputerName(IpUtils.getHostName());
        sys.setComputerIp(IpUtils.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo() throws UnknownHostException {
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(ArithUtils.mul(ArithUtils.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else {
            return String.format("%d B", size);
        }
    }


    /**
     * CPU相关信息
     */
    @Data
    public static class Cpu {

        @ApiModelProperty("核心数")
        private int cpuNum;

        @ApiModelProperty("CPU总的使用率")
        private double total;

        @ApiModelProperty("CPU系统使用率")
        private double sys;

        @ApiModelProperty("CPU用户使用率")
        private double used;

        @ApiModelProperty("CPU当前等待率")
        private double wait;

        @ApiModelProperty("CPU当前空闲率")
        private double free;

        public double getTotal() {
            return ArithUtils.round(ArithUtils.mul(total, 100), 2);
        }

        public double getSys() {
            return ArithUtils.round(ArithUtils.mul(sys / total, 100), 2);
        }

        public double getUsed() {
            return ArithUtils.round(ArithUtils.mul(used / total, 100), 2);
        }

        public double getWait() {
            return ArithUtils.round(ArithUtils.mul(wait / total, 100), 2);
        }

        public double getFree() {
            return ArithUtils.round(ArithUtils.mul(free / total, 100), 2);
        }
    }

    /**
     * JVM相关信息
     */
    @Data
    public static class Jvm {

        @ApiModelProperty("当前JVM占用的内存总数(M)")
        private double total;

        /**
         *
         */
        @ApiModelProperty("JVM最大可用内存总数(M)")
        private double max;

        @ApiModelProperty("JVM空闲内存(M)")
        private double free;

        @ApiModelProperty("JDK版本")
        private String version;

        @ApiModelProperty("JDK路径")
        private String home;

        public double getTotal() {
            return ArithUtils.div(total, (1024 * 1024), 2);
        }
        public double getMax() {
            return ArithUtils.div(max, (1024 * 1024), 2);
        }
        public double getFree() {
            return ArithUtils.div(free, (1024 * 1024), 2);
        }

        public double getUsed()
        {
            return ArithUtils.div(total - free, (1024 * 1024), 2);
        }

        public double getUsage()
        {
            return ArithUtils.mul(ArithUtils.div(total - free, total, 4), 100);
        }

        /**
         * JDK启动时间
         */
        public String getStartTime()
        {
            return TimeUtils.toText(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()), TimeUtils.DATE_TIME_FORMAT);
        }

        /**
         * JDK运行时间
         */
        public String getRunTime() {
            return TimeUtils.timeDistance(getStartTime(),TimeUtils.now());
        }

        /**
         * 运行参数
         */
        public String getInputArgs()
        {
            return ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
        }
    }

    /**
     * 內存相关信息
     */
    @Data
    public static class Mem {

        @ApiModelProperty("内存总量")
        private double total;

        @ApiModelProperty("已用内存")
        private double used;

        @ApiModelProperty("剩余内存")
        private double free;

        public double getTotal() {
            return ArithUtils.div(total, (1024 * 1024 * 1024), 2);
        }

        public double getUsed() {
            return ArithUtils.div(used, (1024 * 1024 * 1024), 2);
        }

        public double getFree(){
            return ArithUtils.div(free, (1024 * 1024 * 1024), 2);
        }

        public double getUsage() {
            return ArithUtils.mul(ArithUtils.div(used, total, 4), 100);
        }
    }

    /**
     * 系统相关信息
     */
    @Data
    public static class Sys {

        @ApiModelProperty("服务器名称")
        private String computerName;

        @ApiModelProperty("服务器Ip")
        private String computerIp;

        @ApiModelProperty("项目路径")
        private String userDir;

        @ApiModelProperty("操作系统")
        private String osName;

        @ApiModelProperty("系统架构")
        private String osArch;
    }

    /**
     * 系统磁盘文件相关信息
     */
    @Data
    public static class SysFile {

        @ApiModelProperty("盘符路径")
        private String dirName;

        @ApiModelProperty("盘符类型")
        private String sysTypeName;

        @ApiModelProperty("文件类型")
        private String typeName;

        @ApiModelProperty("总大小")
        private String total;

        @ApiModelProperty("剩余大小")
        private String free;

        @ApiModelProperty("已经使用量")
        private String used;

        @ApiModelProperty("资源的使用率")
        private double usage;
    }
}
