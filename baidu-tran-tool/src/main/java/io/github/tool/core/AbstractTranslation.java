package io.github.tool.core;

import retrofit2.Call;

public abstract class AbstractTranslation<T>{
    protected Call<T> call;
    public AbstractTranslation(Call<T> call) {
        this.call = call;
    }
    public abstract T get() throws Throwable;

    public abstract void get(CallBack<T> callBack);


    public interface CallBack<T> {
        void success(T item);
        void fail(Throwable throwable, int resultCode);
    }


}

