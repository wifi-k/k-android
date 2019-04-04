package cn.treebear.kwifimanager.config;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.Collections;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DateTimeUtils;
import cn.treebear.kwifimanager.util.TLog;

public class GalleryHelper {

    private static ArrayList<LocalImageBean> imageBeans = new ArrayList<>();
    private static ArrayList<LocalImageSection> sections = new ArrayList<>();

    private GalleryHelper() {
    }

    public static ArrayList<LocalImageBean> getImageBeans() {
        return imageBeans;
    }

    public static ArrayList<LocalImageSection> getSections() {
        return sections;
    }

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
                TLog.w(imageBean);
            } while (cursor.moveToNext());
            image2Section();
        }
    }

    private static void image2Section() {
        if (!Check.hasContent(imageBeans)) {
            return;
        }
        sections.clear();
        Collections.sort(imageBeans, (o1, o2) -> Long.compare(o2.getDateAdded(), o1.getDateAdded()));
        TLog.i(imageBeans);
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
}
