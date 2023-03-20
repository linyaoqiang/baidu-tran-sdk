package io.github.tool.vo;

public class LangIdent {

    public Integer error_code;
    public String error_msg;
    public Data data;

    @Override
    public String toString() {
        return "LangIdent{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        public String src;

        @Override
        public String toString() {
            return "Data{" +
                    "src='" + src + '\'' +
                    '}';
        }
    }
}
