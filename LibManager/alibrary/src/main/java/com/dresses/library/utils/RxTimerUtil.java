package com.dresses.library.utils;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 定时器工具类
 * <p>
 * author: zzs
 * date: 2017/10/24
 */

public class RxTimerUtil {

    private static WeakReference<Disposable> mDisposable;

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds 毫秒
     * @param next
     */
    public static void timer(long milliseconds, final IRxNext next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = new WeakReference<>(disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel();
                    }
                });
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds 毫秒
     * @param next         下次
     */
    public static void interval(long milliseconds, final IRxNext next) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = new WeakReference<>(disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 取消订阅
     */
    public static void cancel() {
        if (mDisposable != null && mDisposable.get() != null && !mDisposable.get().isDisposed()) {
            mDisposable.get().dispose();
        }
    }

    /**
     * 下一步接口
     */
    public interface IRxNext {

        /**
         * 下一步
         *
         * @param number
         */
        void doNext(long number);
    }
}
