package io.github.tool.vo;

public class VoiceTran {

    public Integer code;
    public String msg;
    public Data data;

    @Override
    public String toString() {
        return "VoiceTran{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        public String source;
        public String target;
        public String target_tts;

        @Override
        public String toString() {
            return "Data{" +
                    "source='" + source + '\'' +
                    ", target='" + target + '\'' +
                    ", target_tts='" + target_tts + '\'' +
                    '}';
        }
    }

}
