package com.ltech.smarthome.net;

import com.ltech.smarthome.cache.Cache;
import com.ltech.smarthome.cache.CacheType;
import com.ltech.smarthome.singleton.CacheManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.schedulers.Schedulers;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import retrofit2.Retrofit;

/* loaded from: classes4.dex */
public abstract class AbstractRetrofit {
    protected Retrofit mRetrofit;
    private Cache<String, Object> mRetrofitServiceCache;

    protected synchronized <T> T obtainRetrofitService(Class<T> cls) {
        return (T) createWrapperService(cls);
    }

    private <T> T createWrapperService(final Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: com.ltech.smarthome.net.AbstractRetrofit$$ExternalSyntheticLambda1
            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                Object lambda$createWrapperService$1;
                lambda$createWrapperService$1 = AbstractRetrofit.this.lambda$createWrapperService$1(cls, obj, method, objArr);
                return lambda$createWrapperService$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$createWrapperService$1(final Class cls, Object obj, final Method method, final Object[] objArr) throws Throwable {
        if (method.getReturnType() == Observable.class) {
            return Observable.defer(new Callable() { // from class: com.ltech.smarthome.net.AbstractRetrofit$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    ObservableSource lambda$createWrapperService$0;
                    lambda$createWrapperService$0 = AbstractRetrofit.this.lambda$createWrapperService$0(cls, method, objArr);
                    return lambda$createWrapperService$0;
                }
            }).subscribeOn(Schedulers.single());
        }
        Object retrofitService = getRetrofitService(cls);
        return getRetrofitMethod(retrofitService, method).invoke(retrofitService, objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource lambda$createWrapperService$0(Class cls, Method method, Object[] objArr) throws Exception {
        Object retrofitService = getRetrofitService(cls);
        return ((Observable) getRetrofitMethod(retrofitService, method).invoke(retrofitService, objArr)).subscribeOn(Schedulers.io());
    }

    private <T> T getRetrofitService(Class<T> cls) {
        if (this.mRetrofitServiceCache == null) {
            this.mRetrofitServiceCache = CacheManager.getInstance().getCacheFactory().build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        T t = (T) this.mRetrofitServiceCache.get(cls.getCanonicalName());
        if (t != null) {
            return t;
        }
        T t2 = (T) this.mRetrofit.create(cls);
        this.mRetrofitServiceCache.put(cls.getCanonicalName(), t2);
        return t2;
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }
}