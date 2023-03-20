package io.github.tool;

import io.github.tool.core.AbstractTranslation;
import io.github.tool.core.DefaultTranslation;
import io.github.tool.core.Translator;
import io.github.tool.vo.LangIdent;
import io.github.tool.vo.PhotoTran;
import io.github.tool.vo.TextTran;
import io.github.tool.vo.VoiceTran;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class TranTest {

    //APP ID 来源于百度翻译平台 请使用自己的
    public static final String APP_ID = "20230308001591938";
    //秘钥 来源于百度翻译平台 请使用自己的
    public static final String KEY = "ZZs13UM4hFsYHHWbCO1A";

    private Translator translator;

    @Before
    public void before() {
        translator = new Translator(APP_ID, KEY);
    }

    @Test
    public void testText() throws Throwable {
        DefaultTranslation<TextTran> translation = translator.textTran("你好世界", "auto", "auto");
        TextTran textTran = translation.get();
        System.out.println(textTran);

    }

    @Test
    public void testPhoto() throws Throwable {
        DefaultTranslation<PhotoTran> translation = translator.photoTran(new File("C:\\Users\\LYQ\\Desktop\\GP0N[4~LGO@%9D]LZ%P_O1M.png"),
                    "zh", "en", DefaultTranslation.class);
        PhotoTran photoTran = translation.get();
        System.out.println(photoTran);
    }

    @Test
    public void testVoice() throws Throwable {
        DefaultTranslation<VoiceTran> translation = translator.voiceTran(
                new File("C:\\Users\\LYQ\\Downloads\\16k.pcm"),
                "zh", "en", DefaultTranslation.class);
       /* translation.get(new AbstractTranslation.CallBack<VoiceTran>() {
            @Override
            public void success(VoiceTran item) {
                System.out.println(item);
            }

            @Override
            public void fail(Throwable throwable, int resultCode) {
                if (throwable != null)
                    throwable.printStackTrace();
                else
                    System.out.println("result code :" + resultCode);

            }
        });*/

        VoiceTran voiceTran = translation.get();
        System.out.println(voiceTran);

    }

    @Test
    public void testIndent() throws Throwable {
        DefaultTranslation<LangIdent> translation = translator.langIdent("我想要飞");
        LangIdent identification = translation.get();
        System.out.println(identification);
    }


}