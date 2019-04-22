package cn.treebear.kwifimanager.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.AppBean;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;
import cn.treebear.kwifimanager.bean.Daybean;
import cn.treebear.kwifimanager.bean.MobilePhoneBean;
import cn.treebear.kwifimanager.bean.TimeLimitBean;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 测试数据类
 * 用于开发期间测试界面展示效果
 *
 * @author Administrator
 */
public class BeanTest {
    public static ArrayList<BanAppPlanBean> banAppPlanBeans = null;
    private static ArrayList<MobilePhoneBean> mobilePhoneList = null;
    private static ArrayList<AppBean> appBeans = null;
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
    private static String number = "1234567890";
    private static char[] dict = text.toCharArray();
    private static char[] num = number.toCharArray();
    private static Random random = new Random();

    private BeanTest() {
    }

    /**
     * 测试数据
     * --- App 列表
     */
    public static ArrayList<AppBean> getAppList() {
        if (appBeans == null) {
            appBeans = new ArrayList<AppBean>() {{
                add(new AppBean("王者荣耀", R.mipmap.ic_launcher, false));
                add(new AppBean("QQ", R.mipmap.ic_launcher, false));
                add(new AppBean("微信", R.mipmap.ic_launcher, false));
                add(new AppBean("微博", R.mipmap.ic_launcher, false));
                add(new AppBean("知乎", R.mipmap.ic_launcher, false));
                add(new AppBean("刺激战场", R.mipmap.ic_launcher, false));
                add(new AppBean("明日之后", R.mipmap.ic_launcher, false));
                add(new AppBean("脉脉", R.mipmap.ic_launcher, false));
            }};
        }
        return appBeans;
    }

    public static void testUpload(String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
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
                                add(getAppList().get(random.nextInt(getAppList().size())));
                                add(getAppList().get(random.nextInt(getAppList().size())));
                                add(getAppList().get(random.nextInt(getAppList().size())));
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
     * 测试数据
     * ----获取联网设备
     */
    public static ArrayList<MobilePhoneBean> getMobilePhoneList(int number) {
        if (mobilePhoneList == null) {
            mobilePhoneList = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                mobilePhoneList.add(new MobilePhoneBean(
                        createString(random.nextInt(3) + 1),
                        random.nextInt(3),
                        random.nextInt(3) >= 1,
                        createTimeMill(),
                        createTimeMill(),
                        random.nextInt(100),
                        createHoursMill(),
                        random.nextBoolean(),
                        new ArrayList<AppBean>() {{
                            add(getAppList().get(random.nextInt(getAppList().size())));
                            add(getAppList().get(random.nextInt(getAppList().size())));
                            add(getAppList().get(random.nextInt(getAppList().size())));
                        }}
                ));
            }
        }
        for (MobilePhoneBean bean : mobilePhoneList) {
            bean.setBanOnline(false);
        }
        return mobilePhoneList;
    }

    private static String createSerialId() {
        // kJG58824542802
        StringBuilder kJG = new StringBuilder("kJG");
        for (int i = 0; i < 14; i++) {
            kJG.append(num[random.nextInt(num.length)]);
        }
        return kJG.toString();
    }

    private static String createKname() {
        StringBuilder name = new StringBuilder("小k云管家-");
        for (int i = 0; i < 5; i++) {
            name.append(num[random.nextInt(num.length)]);
        }
        return name.toString();
    }

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
