package net.treebear.kwifimanager.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class CountObserver implements Observer<Long> {

    @Override
    public abstract void onSubscribe(Disposable d);

    @Override
    public void onNext(Long t) {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public abstract void onComplete();
}
