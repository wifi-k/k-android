package net.treebear.kwifimanager.test;

import net.treebear.kwifimanager.bean.AppBean;
import net.treebear.kwifimanager.bean.FamilyMemberBean;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.bean.NoticeBean;

import java.util.ArrayList;
import java.util.Random;

public class BeanTest {
    private BeanTest() {
    }

    private static Random random = new Random();

    public static ArrayList<NoticeBean> getNoticeList() {
        ArrayList<NoticeBean> result = new ArrayList<>();
        result.add(new NoticeBean("好消息，小K管家1.0正式上线啦！", "https://www.baidu.com"));
        result.add(new NoticeBean("特大好消息，小K管家2.0正式上线啦！", "https://www.baidu.com"));
        result.add(new NoticeBean("特大大好消息，小K管家3.0正式上线啦！", "https://www.baidu.com"));
        result.add(new NoticeBean("特大大大好消息，小K管家4.0正式上线啦！", "https://www.baidu.com"));
        return result;
    }

    public static ArrayList<String> getNoticFromBean(ArrayList<NoticeBean> notics) {
        ArrayList<String> strings = new ArrayList<>();
        for (NoticeBean notic : notics) {
            strings.add(notic.getContent());
        }
        return strings;
    }

    /**
     * 测试数据
     * 首页----获取联网设备
     */
    public static ArrayList<MobilePhoneBean> getMobilePhoneList(int number) {
        //long id, String name, String type, int status, long onlineTime, long offlineTime
        ArrayList<MobilePhoneBean> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(new MobilePhoneBean(
                    i,
                    createString(random.nextInt(5)),
                    random.nextInt(3),
                    random.nextInt(3) >= 1,
                    createTimeMill(),
                    createTimeMill()
            ));
        }
        return result;
    }

    /**
     * 测试数据
     * 首页----获取儿童设备列表
     */
    public static ArrayList<MobilePhoneBean> getChildrenPhoneList(int number) {
        ArrayList<MobilePhoneBean> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(new MobilePhoneBean(
                    createString(random.nextInt(5)),
                    random.nextInt(3),
                    random.nextInt(100),
                    createHoursMill(),
                    new ArrayList<AppBean>() {
                        {
                            add(new AppBean());
                            add(new AppBean());
                            add(new AppBean());
                        }
                    }
            ));
        }
        return result;
    }

    /**
     * 测试数据
     * 家庭成员----列表
     */
    public static ArrayList<FamilyMemberBean> getFamilyMemberList(int number) {
        ArrayList<FamilyMemberBean> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(new FamilyMemberBean(createString(4), createMobile()));
        }
        return result;
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

    private static String createString(int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number + 1; i++) {
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
