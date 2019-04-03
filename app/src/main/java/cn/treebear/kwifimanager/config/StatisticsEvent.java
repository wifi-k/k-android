package cn.treebear.kwifimanager.config;

public interface StatisticsEvent {
    /**
     * 添加成员
     */
    String FAMILY_INVITE_MEMBER = "family.invite_member";
    /**
     * 加入家庭
     */
    String FAMILY_INPUT_FAMILY_CODE = "family.input_family_code";
    /**
     * 禁用app
     */
    String CONTROL_BAN_APP = "control.ban_app";
    /**
     * 禁用设备
     */
    String CONTROL_BAN_DEVICE = "control.ban_device";
    /**
     * 查看周报
     */
    String MANAGER_WEEK_REPORT = "manager.week_report";
}
