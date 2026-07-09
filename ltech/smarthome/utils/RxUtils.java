package com.ltech.smarthome.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes4.dex */
public class RxUtils {
    private RxUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer() { // from class: com.ltech.smarthome.utils.RxUtils$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                ObservableSource observeOn;
                observeOn = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                return observeOn;
            }
        };
    }

    public static <T> ObservableTransformer<T, T> io_io() {
        return new ObservableTransformer() { // from class: com.ltech.smarthome.utils.RxUtils$$ExternalSyntheticLambda1
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                ObservableSource observeOn;
                observeOn = observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
                return observeOn;
            }
        };
    }
}