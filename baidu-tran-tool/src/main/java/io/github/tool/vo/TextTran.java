package io.github.tool.vo;

import java.util.List;

public class TextTran {
    public String from;
    public String to;
    public List<TransResult> trans_result;
    public String error_code;
    public String error_msg;


    @Override
    public String toString() {
        return "TextTran{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", trans_result=" + trans_result +
                ", error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }

    public class TransResult{
        public String src;
        public String dst;



        @Override
        public String toString() {
            return "TransResult{" +
                    "src='" + src + '\'' +
                    ", dst='" + dst + '\'' +
                    '}';
        }
    }

}
