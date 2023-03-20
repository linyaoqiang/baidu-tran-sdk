package io.github.tool.core;


import java.util.Map;

import io.github.tool.vo.LangIdent;
import io.github.tool.vo.PhotoTran;
import io.github.tool.vo.TextTran;
import io.github.tool.vo.VoiceTran;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface TranslationAPI {
    String BASE_URL = "https://fanyi-api.baidu.com/";
    String GENERAL_TEXT_TRANSLATION_API = "/api/trans/vip/translate";
    String VOICE_TRANSLATION_API = "/api/trans/v2/voicetrans";
    String PHOTO_TRANSLATION_API = "/api/trans/sdk/picture";
    String LANG_IDENTIFICATION_API = "/api/trans/vip/language";
    @GET(GENERAL_TEXT_TRANSLATION_API)
    Call<TextTran> textTran(
            @QueryMap Map<String, String> body
    );
    @POST(VOICE_TRANSLATION_API)
    Call<VoiceTran> voiceTran(
            @Header("Content-Type") String contentType,
            @Header("X-Appid") String appId,
            @Header("X-Timestamp") String timestamp,
            @Header("X-Sign") String sign,
            @Body RequestBody requestBody
    );

    @POST(PHOTO_TRANSLATION_API)
    Call<PhotoTran> photoTran(
            @Body RequestBody body
    );

    @GET(LANG_IDENTIFICATION_API)
    Call<LangIdent> langIdent(@QueryMap Map<String, String> body);
}
