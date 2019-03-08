package net.treebear.kwifimanager.test;

import net.treebear.kwifimanager.bean.AppBean;
import net.treebear.kwifimanager.bean.BanAppPlanBean;
import net.treebear.kwifimanager.bean.Daybean;
import net.treebear.kwifimanager.bean.FamilyMemberBean;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.bean.NoticeBean;
import net.treebear.kwifimanager.bean.TimeLimitBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 *
 * 测试数据类
 * 用于开发期间测试界面展示效果
 * @author Administrator
 */
public class BeanTest {
    private static ArrayList<NoticeBean> result = null;
    private static ArrayList<MobilePhoneBean> mobilePhoneList = null;
    private static ArrayList<MobilePhoneBean> childrenMobile = null;
    private static ArrayList<FamilyMemberBean> familyMemberBeans = null;
    private static ArrayList<BanAppPlanBean> banAppPlanBeans = null;
    private static ArrayList<TimeLimitBean> timeLimitBeans;

    private BeanTest() {
    }

    /**
     * 测试数据
     * 获取时间控制列表
     */
    public static ArrayList<TimeLimitBean> getTimeLimitList() {
        if (timeLimitBeans == null) {
            timeLimitBeans = new ArrayList<TimeLimitBean>() {{
                add(new TimeLimitBean(createString(3), "12:00", "16:00", new ArrayList<Daybean>() {{
                    add(new Daybean("周一", Calendar.MONDAY));
                    add(new Daybean("周二", Calendar.TUESDAY));
                    add(new Daybean("周三", Calendar.WEDNESDAY));
                }}));
                add(new TimeLimitBean(createString(3), "12:00", "16:00", new ArrayList<Daybean>() {{
                    add(new Daybean("周一", Calendar.MONDAY));
                    add(new Daybean("周二", Calendar.TUESDAY));
                    add(new Daybean("周三", Calendar.WEDNESDAY));
                }}));
                add(new TimeLimitBean(createString(3), "12:00", "16:00", new ArrayList<Daybean>() {{
                    add(new Daybean("周一", Calendar.MONDAY));
                    add(new Daybean("周二", Calendar.TUESDAY));
                    add(new Daybean("周三", Calendar.WEDNESDAY));
                }}));
            }};
        }
        return timeLimitBeans;
    }

    /**
     * 测试数据
     *
     * @return 禁用app计划
     */
    public static ArrayList<BanAppPlanBean> getBanAppPlanList() {
        if (banAppPlanBeans == null) {
            banAppPlanBeans = new ArrayList<BanAppPlanBean>() {{
                for (int i = 0; i < 3; i++) {
                    add(new BanAppPlanBean(createString(5),
                            new ArrayList<TimeLimitBean>() {{
                                add(new TimeLimitBean(createString(3), "12:00", "16:00", new ArrayList<Daybean>() {{
                                    add(new Daybean("周一", Calendar.MONDAY));
                                    add(new Daybean("周二", Calendar.TUESDAY));
                                }}));
                            }},
                            new ArrayList<AppBean>() {{
                                add(new AppBean());
                                add(new AppBean());
                                add(new AppBean());
                            }},
                            new ArrayList<MobilePhoneBean>() {{
                                if (mobilePhoneList == null) {
                                    getMobilePhoneList(10);
                                }
                                addAll(mobilePhoneList.subList(2, 4));
                            }}
                    ));
                }
            }};
        }
        return banAppPlanBeans;
    }


    /**
     * 获取消息列表
     */
    public static ArrayList<NoticeBean> getNoticeList() {
        if (result == null) {
            result = new ArrayList<>();
            result.add(new NoticeBean("树熊客访专用上网时间到了，被踢下线了", "【设备下线】", createTimeMill()));
            result.add(new NoticeBean("树熊客访专用上线啦", "【设备上线】", createTimeMill()));
            result.add(new NoticeBean("熊孩子的手机上线啦", "【设备上线】", createTimeMill()));
            result.add(new NoticeBean("您有新设备树熊客访专用上线啦，请立即备注 便于管理", "【新设备上线】", createTimeMill()));
        }
        return result;
    }

    /**
     * 从信息中提取字符串集合
     */
    public static ArrayList<String> getNoticeFromBean(ArrayList<NoticeBean> notices) {
        ArrayList<String> strings = new ArrayList<>();
        for (NoticeBean notice : notices) {
            strings.add(notice.getContent());
        }
        return strings;
    }

    /**
     * 测试数据
     * ----获取联网设备
     */
    public static ArrayList<MobilePhoneBean> getMobilePhoneList(int number) {
        if (mobilePhoneList == null) {
            mobilePhoneList = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                mobilePhoneList.add(new MobilePhoneBean(
                        createString(random.nextInt(5)),
                        random.nextInt(3),
                        random.nextInt(3) >= 1,
                        createTimeMill(),
                        createTimeMill(),
                        random.nextInt(100),
                        createHoursMill(),
                        new ArrayList<AppBean>() {{
                            add(new AppBean());
                            add(new AppBean());
                            add(new AppBean());
                        }}
                ));
            }
        }
        return mobilePhoneList;
    }

    /**
     * 获取首页设备
     */
    public static ArrayList<MobilePhoneBean> getHomeMobileList() {
        ArrayList<MobilePhoneBean> result = new ArrayList<>();
        if (mobilePhoneList == null || mobilePhoneList.size() < 3) {
            getMobilePhoneList(10);
        }
        result.addAll(new ArrayList<>(mobilePhoneList.subList(0, 3)));
        return result;
    }

    /**
     * 测试数据
     * 首页----获取儿童设备列表
     */
    public static ArrayList<MobilePhoneBean> getChildrenPhoneList(int number) {
        if (childrenMobile == null) {
            childrenMobile = new ArrayList<>();
            if (mobilePhoneList == null) {
                getMobilePhoneList(10 + number);
            }
            for (int i = 3; i < 3 + number; i++) {
                childrenMobile.add(mobilePhoneList.get(i));
            }
        }
        return childrenMobile;
    }

    /**
     * 测试数据
     * 家庭成员----列表
     */
    public static ArrayList<FamilyMemberBean> getFamilyMemberList(int number) {
        if (familyMemberBeans == null) {
            familyMemberBeans = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                familyMemberBeans.add(new FamilyMemberBean(createString(4), createMobile()));
            }
        }
        return familyMemberBeans;
    }


    /**
     * ------------------------------------------随机生成时间和字符
     */
    private static String text = "公亢中丹井介今及太天斗斤支允元勿午友尤尹引文月日王云匀仁升壬少心日氏水尤仍双尺止才不互分化夫幻方木比丰火克" +
            "告改攻更谷况伽估君吾圻均坎研完局岐我杞江究见角言住占况里冷伶利助努君昌壮妞局弄廷弟彤志托杖杜呆李江男究良见里舟佟体足玎位佐冶吾吟完忘" +
            "我修言邑酉吟吴研呆伸佐作些些初呈坐孝宋岐希岑床序巡形忍成杏材杉束村杞步汝汐池秀赤身车辰系占伺住余助劭劬邵壮志汕江见早布伯伴佛兵判别含" +
            "坊宏每甫孚孝希形杏贝儿恭格桂根耕耿股肯贡高刚哥宫挂皋径徒倜恬拯旅晋朕桔桃桐洞流洛烈特玲珍真矩祝秩纳纽者肩芝记伦兼唐哲旃娟娜展峻准凌洲" +
            "套特留庭栗株津玳恩洋纹耘育芽芸袁倚原员埃宴峨倚娱容峪移益差师席座徐恰息恕持拾时书校朔桑珊神祖秦秤索素纾纯虔讫训财起轩芩闪迅倩幸修仓" +
            "城夏孙宰容射峡叟奚春乘借准淞宵指拭洵洳狩兹珊站宸挈晃桓活洪畔亩眠秘粉纺航芳芙花峰涵畔埔恢恍恒柏派洹珉祜呗赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张" +
            "孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤";

    private static char[] dict = text.toCharArray();

    private static Random random = new Random();

    private static String createString(int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            sb.append(dict[random.nextInt(dict.length)]);
        }
        return sb.toString();
    }

    private static long createTimeMill() {
        return System.currentTimeMillis() - random.nextInt(86400 * 1000);
    }

    private static long createHoursMill() {
        return random.nextInt(1000 * 60 * 60 * 12);
    }

    private static String createMobile() {
        StringBuilder ph = new StringBuilder("1");
        for (int i = 0; i < 10; i++) {
            ph.append(random.nextInt(10));
        }
        return ph.toString();
    }

}
