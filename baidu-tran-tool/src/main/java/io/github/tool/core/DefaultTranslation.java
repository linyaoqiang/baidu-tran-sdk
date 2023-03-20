package io.github.tool.core;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultTranslation <T> extends AbstractTranslation<T> {

    public static final int DEF_ERR_CODE = -1;
    public DefaultTranslation(Call<T> call) {
        super(call);
    }

    @Override
    public T get() throws Throwable {
        return call.execute().body();
    }

    @Override
    public void get(final CallBack<T> callBack) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callBack.success(response.body());
                }else {
                    callBack.fail(null, response.code());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                callBack.fail(throwable, DEF_ERR_CODE);
            }
        });
    }




}
