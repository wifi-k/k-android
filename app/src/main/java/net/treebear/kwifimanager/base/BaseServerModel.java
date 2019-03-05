package net.treebear.kwifimanager.base;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import net.treebear.kwifimanager.http.HttpClient;
import net.treebear.kwifimanager.http.HttpObserver;
import net.treebear.kwifimanager.http.HttpService;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.util.TLog;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Tinlone
 * @date 2018/3/23.
 * Life has become richer by the love that has been lost.
 */

public class BaseServerModel implements IModel {
    /**
     * API service对象
     */
    protected HttpService mService = HttpClient.getInstance().getApiService();
    /**
     * 请求队列
     */
    private SparseArray<Observable> queue = null;
    private int index = 0;

    @SuppressLint("CheckResult")
    protected Observable bindObservable(@NonNull Observable call) {
        if (queue == null) {
            queue = new SparseArray<>();
        }
        queue.append(index, call);
        index++;
        return call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 自动绑定订阅器
     *
     * @param call     可订阅
     * @param callBack 响应回调
     * @param <T>      请求响应数据类型
     */
    protected <T> void bindObservable(@NonNull Observable<BaseResponse<T>> call, @NonNull AsyncCallBack<BaseResponse<T>> callBack) {
        if (queue == null) {
            queue = new SparseArray<>();
        }
        queue.append(index, call);
        index++;
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(HttpObserver.getInstance().createObserver(callBack));
    }

    @Override
    @SuppressLint("CheckResult")
    public void cancelRequest() {
        if (queue != null && queue.size() > 0) {
            for (int i = 0; i < queue.size(); i++) {
                TLog.i(queue.get(i).unsubscribeOn(AndroidSchedulers.mainThread()));
            }
            queue.clear();
            index = 0;
        }
    }
}
