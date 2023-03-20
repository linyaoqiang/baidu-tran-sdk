package io.github.tool.core;

import io.github.tool.vo.LangIdent;
import io.github.tool.vo.PhotoTran;
import io.github.tool.vo.TextTran;
import io.github.tool.vo.VoiceTran;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Translator {
    public static final String APP_ID_NAME = "appid";
    public static final String FORMAT = "pcm";

    public static final String CUID = "APICUID";
    public static final String MAC = "mac";
    public static final String VERSION = "3";


    public static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";
    public static final String IMAGE_PNG_CONTENT_TYPE = "image/png";

    private String appId;
    private String secretKey;
    private TranslationAPI api;


    public Translator(String appId, String secretKey) {
        this.appId = appId;
        this.secretKey = secretKey;
        init();
    }

    private void init() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(TranslationAPI.BASE_URL);
        builder.client(new OkHttpClient.Builder().build());
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        api = retrofit.create(TranslationAPI.class);
    }

    public DefaultTranslation<TextTran> textTran(
            String query,
            String from,
            String to
    ) {
        return textTran(query, from, to, DefaultTranslation.class);
    }


    public <T extends AbstractTranslation<?>> T textTran(
            String query,
            String from,
            String to,
            Class<T> clazz
    ) {

        try {
            Constructor<T> constructor
                    = clazz.getDeclaredConstructor(Call.class);

            String salt = CodeHelper.randomNumber();
            String sign = CodeHelper.md5TextToHex(appId + query + salt + secretKey);
            Map<String, String> body = new HashMap<>();
            body.put("q", query);
            body.put("from", from);
            body.put("to", to);
            body.put("salt", salt);
            body.put("sign", sign);
            body.put(APP_ID_NAME, appId);

            return constructor.newInstance(api.textTran(body));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends AbstractTranslation<?>> T voiceTran(
            byte[] voice,
            String from,
            String to,
            String format,
            Class<T> clazz) {
        try {
            Constructor<T> constructor =
                    clazz.getDeclaredConstructor(Call.class);

            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String voice64 = CodeHelper.base64Encode(voice);
            String sign = CodeHelper.hmacSHA256ToBase64(appId + timestamp + voice64, secretKey);
            Map<String, String> map = new HashMap<>();
            map.put("from", from);
            map.put("to", to);
            map.put("format", format);
            map.put("voice", voice64);
            RequestBody body = RequestBody.create(MediaType.parse(APPLICATION_JSON_CONTENT_TYPE), new Gson().toJson(map));
            return constructor.newInstance(
                    api.voiceTran(
                            APPLICATION_JSON_CONTENT_TYPE,
                            appId,
                            timestamp,
                            sign,
                            body
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends AbstractTranslation<?>> T voiceTran(
            byte[] voice,
            String from,
            String to,
            Class<T> clazz){
        return voiceTran(voice, from, to, FORMAT,  clazz);
    }

    public <T extends AbstractTranslation<?>> T voiceTran(
            File file,
            String from,
            String to,
            Class<T> clazz){
        try {
            byte[] data = CodeHelper.toByteArray(file);
            return voiceTran(data, from, to, FORMAT, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

       return null;
    }

    public DefaultTranslation<VoiceTran> voiceTran(
            File file,
            String from,
            String to
    ) {
        return voiceTran(file, from, to, DefaultTranslation.class);
    }


    public <T extends AbstractTranslation<?>> T  photoTran(
            byte[] image,
            String suffix,
            String from,
            String to,
            Class<T> clazz
    ) {
        try {
            Constructor<T> constructor
                    = clazz.getDeclaredConstructor(Call.class);

            String imagesMD5 = CodeHelper.md5ToHex(image);
            String imagesName = UUID.randomUUID().toString();
            if (suffix.startsWith(".")) {
                imagesName += suffix;
            }else {
                imagesName += '.' + suffix;
            }
            String salt = CodeHelper.randomNumber();
            String sign = CodeHelper.md5TextToHex(appId + imagesMD5 + salt + CUID + MAC + secretKey);
            RequestBody imageBody = RequestBody.create(MediaType.parse(IMAGE_PNG_CONTENT_TYPE), image);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("from", from);
            builder.addFormDataPart("to", to);
            builder.addFormDataPart(APP_ID_NAME, appId);
            builder.addFormDataPart("salt", salt);
            builder.addFormDataPart("cuid", CUID);
            builder.addFormDataPart("mac", MAC);
            builder.addFormDataPart("version", VERSION);
            builder.addFormDataPart("sign", sign);
            builder.addFormDataPart("image", imagesName, imageBody);


            return constructor.newInstance(api.photoTran(builder.build()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends AbstractTranslation<?>> T photoTran(
            File file,
            String from,
            String to,
            Class<T> clazz
    ) {
        try {
            byte[] data = CodeHelper.toByteArray(file);
            String name = file.getName();
            String suffix = name.substring(name.lastIndexOf('.'));
            return photoTran(data, suffix, from, to, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public DefaultTranslation<PhotoTran> photoTran(
            File file,
            String from,
            String to
    ){
        return photoTran(file, from, to, DefaultTranslation.class);
    }

    public <T extends AbstractTranslation<?>> T langIdent(
            String query,
            Class<T> clazz
    ) {
        try {
            Constructor<T> constructor
                    = clazz.getDeclaredConstructor(Call.class);

            Map<String, String> map = new HashMap<>();
            String salt = CodeHelper.randomNumber();
            String sign = CodeHelper.md5TextToHex(appId + query + salt + secretKey);
            map.put("q", query);
            map.put(APP_ID_NAME, appId);
            map.put("salt", salt);
            map.put("sign", sign);
            return constructor.newInstance(api.langIdent(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public DefaultTranslation<LangIdent> langIdent(
            String query
    ) {
        return langIdent(query, DefaultTranslation.class);
    }


}
