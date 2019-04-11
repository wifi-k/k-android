package cn.treebear.kwifimanager.config;

import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DateTimeUtils;
import cn.treebear.kwifimanager.util.TLog;

public class GalleryHelper {

    private static ArrayList<LocalImageBean> imageBeans = new ArrayList<>();
    private static ArrayList<LocalImageSection> sections = new ArrayList<>();

    public static final int IMAGE_MODEL_DISPLAY = 0;
    public static final int IMAGE_MODEL_SELECT = 1;

    @IntDef({IMAGE_MODEL_DISPLAY, IMAGE_MODEL_SELECT})
    public @interface AdapterModel {

    }

    private GalleryHelper() {
    }

    public static ArrayList<LocalImageBean> getImageBeans() {
        return imageBeans;
    }

    public static ArrayList<LocalImageSection> getSections() {
        return sections;
    }

    /**
     * 创建资源游标
     *
     * @return CursorLoader
     */
    public static Loader<Cursor> onCreateLoader() {
        String[] STORE_IMAGES = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Thumbnails.DATA
        };
        imageBeans.clear();
        sections.clear();
        return new CursorLoader(
                MyApplication.getAppContext(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC");
    }

    /**
     * cursor加载完成后的回调
     *
     * @param cursor 资源游标
     */
    public static void onLoadFinished(Cursor cursor) {
        imageBeans.clear();
        sections.clear();
        if (cursor.moveToNext()) {
            int thumbPathIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            int timeIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);
            int pathIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do {
                String thumbPath = cursor.getString(thumbPathIndex);
                long date = cursor.getLong(timeIndex);
                if (date < DateTimeUtils.YEAR) {
                    date *= 1000;
                }
                String filepath = cursor.getString(pathIndex);
                LocalImageBean imageBean = new LocalImageBean(thumbPath, date, DateTimeUtils.formatYMD4Gallery(date), filepath);
                imageBeans.add(imageBean);
            } while (cursor.moveToNext());
            image2Section();
        }
    }

    /**
     * image按时间组成section
     */
    private static void image2Section() {
        if (!Check.hasContent(imageBeans)) {
            return;
        }
        sections.clear();
        Collections.sort(imageBeans, (o1, o2) -> Long.compare(o2.getDateAdded(), o1.getDateAdded()));
        String date = imageBeans.get(0).getDate();
        sections.add(new LocalImageSection(true, date));
        for (LocalImageBean bean : imageBeans) {
            if (!date.equals(bean.getDate())) {
                date = bean.getDate();
                sections.add(new LocalImageSection(true, date));
            }
            sections.add(new LocalImageSection(bean));
        }
    }

    /**
     * 获取真实的图片index
     *
     * @param bean 组员
     * @return 真实图片index
     */
    public static int getRealImageIndex(LocalImageSection bean) {
        if (!sections.contains(bean) || bean.isHeader) {
            return -1;
        }
        int total = sections.indexOf(bean);
        int header = 0;
        for (int i = 0; i < total; i++) {
            header += sections.get(i).isHeader ? 1 : 0;
        }
        return total - header;
    }

    /**
     * 获取真实的图片index
     *
     * @param position 组员index
     * @return 真实图片index
     */
    public static int getRealImageIndex(@IntRange(from = 0) int position) {
        if (position >= sections.size()) {
            return sections.size();
        }
        LocalImageSection bean = sections.get(position);
        if (!sections.contains(bean) || bean.isHeader) {
            return -1;
        }
        int total = sections.indexOf(bean);
        int header = 0;
        for (int i = 0; i < total; i++) {
            header += sections.get(i).isHeader ? 1 : 0;
        }
        return total - header;
    }
}
