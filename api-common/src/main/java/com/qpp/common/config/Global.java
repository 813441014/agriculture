package com.qpp.common.config;


import com.qpp.common.utils.StringUtils;
import com.qpp.common.utils.YamlUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AddressUtils
 * @Description TODO 全局配置类
 * @Author qipengpai
 * @Date 2018/10/23 11:49
 * @Version 1.0.1
 */
@Slf4j
public class Global {

    private static String NAME = "custom.yml";
    private static final String QPP_PROFILE = "qpp.profile";

    /**
     * 当前对象实例
     */
    private static Global global = null;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private Global() { }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */

    public static synchronized Global getInstance() {
        if (global == null) {
            synchronized (Global.class) {
                if (global == null)
                    global = new Global();
            }
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            Map<?, ?> yamlMap = null;
            try
            {
                yamlMap = YamlUtil.loadYaml(NAME);
                value = String.valueOf(YamlUtil.getProperty(yamlMap, key));
                map.put(key, value != null ? value : StringUtils.EMPTY);
            }
            catch (FileNotFoundException e)
            {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取项目名称
     */
    public static String getName() {
        return StringUtils.nvl(getConfig("qpp.name"), "agriculture");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion() {
        return StringUtils.nvl(getConfig("qpp.version"), "1.0.1");
    }

    /**
     * 获取版权年份
     */
    public static String getCopyrightYear() {
        return StringUtils.nvl(getConfig("qpp.copyrightYear"), "2018");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled() {
        return Boolean.valueOf(getConfig("qpp.addressEnabled"));
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile() {
        return getConfig(QPP_PROFILE);
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getConfig(QPP_PROFILE) + "avatar/";
    }

    /**
     * 获取下载上传路径
     */
    public static String getDownloadPath() {
        return getConfig(QPP_PROFILE) + "download/";
    }

    /**
     * 获取作者
     */
    public static String getAuthor() {
        return StringUtils.nvl(getConfig("gen.author"), "qpp");
    }

    /**
     * 生成包路径
     */
    public static String getPackageName() {
        return StringUtils.nvl(getConfig("gen.packageName"), "com.qpp.project.module");
    }

    /**
     * 是否自动去除表前缀
     */
    public static String getAutoRemovePre() {
        return StringUtils.nvl(getConfig("gen.autoRemovePre"), "true");
    }

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String getTablePrefix() {
        return StringUtils.nvl(getConfig("gen.tablePrefix"), "sys_");
    }
}
