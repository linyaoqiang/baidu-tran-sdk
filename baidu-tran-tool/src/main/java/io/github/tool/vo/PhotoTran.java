package io.github.tool.vo;

import java.util.List;

public class PhotoTran {

    public String error_code;
    public String error_msg;
    public Data data;

    @Override
    public String toString() {
        return "PhotoTran{" +
                "error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        public String from;
        public String to;
        public List<Content> content;
        public String sumSrc;
        public String sumDst;
        public String pasteImg;

        @Override
        public String toString() {
            return "Data{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", content=" + content +
                    ", sumSrc='" + sumSrc + '\'' +
                    ", sumDst='" + sumDst + '\'' +
                    ", pasteImg='" + pasteImg + '\'' +
                    '}';
        }

        public static class Content {
            public String src;
            public String dst;
            public String rect;
            public Integer lineCount;
            public String pasteImg;
            public List<Point> points;

            public static class Point {
                public Integer x;
                public Integer y;
            }

            @Override
            public String toString() {
                return "Content{" +
                        "src='" + src + '\'' +
                        ", dst='" + dst + '\'' +
                        ", rect='" + rect + '\'' +
                        ", lineCount=" + lineCount +
                        ", pasteImg='" + pasteImg + '\'' +
                        ", points=" + points +
                        '}';
            }
        }
    }
}
