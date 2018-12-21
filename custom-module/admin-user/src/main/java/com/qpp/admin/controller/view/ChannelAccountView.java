package com.qpp.admin.controller.view;
/*          _      ______ ______ _   _
 *         | |    |  ____|  ____| \ | |
 *         | |    | |__  | |__  |  \| |
 *         | |    |  __| |  __| | . ` |
 *         | |____| |____| |____| |\  |
 *         |______|______|______|_| \_|
 *
 *   · · · 佛祖镇楼· · ·BUG辟易 · · ·
 *    佛曰：
 *              写字楼里写字间，写字间里程序员
 *              程序人员写程序，又拿程序抵酒钱
 *              酒醒只在网上做，酒醉还来网下眠
 *              酒醉酒醒日复日，网上网下年复年
 *              但愿老死电脑间，不愿鞠躬老板前
 *              奔驰宝马贵人趣，公交自行程序员
 *              别人笑我太疯癫，我笑自己命太贱
 */


import com.qpp.admin.entity.system.SysUser;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChannelAccountView extends SysUser {
    private String channelName;
}
