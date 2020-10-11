package com.example.controllers.retrofit;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ControlledLiveData<T> extends LiveData<T> implements Disposable, Subscriber<T> {
    AtomicBoolean disposed = new AtomicBoolean(false);
    AtomicBoolean pending = new AtomicBoolean(false);
    Subscription s;

    public ControlledLiveData(Observable<T> source) {
        source.toFlowable(BackpressureStrategy.BUFFER).subscribe(this);
    }

    @Override
    public void dispose() {
        if (disposed.compareAndSet(true, false)) {
            s.cancel();
            s = null;
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed.get();
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.s = s;
    }

    @Override
    public void onNext(T t) {
        postValue(t);
    }

    @Override
    public void onError(Throwable t) {
        dispose();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new UnhandledException();
            }
        });
    }

    @Override
    public void onComplete() {
        dispose();
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (s != null)
            s.request(1);
    }

    @Override
    @MainThread
    protected void setValue(T value) {
        pending.set(true);
        super.setValue(value);
        if (hasActiveObservers()) {
            if (s != null)
                s.request(1);
        }
    }

    @Override
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }
}
