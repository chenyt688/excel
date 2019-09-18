package bean;

import java.util.HashMap;

//回调
public class MessageHeadCallBack extends MessageHead {
    //c端拆包请求头
    private final static String headValue_C_FROM = "<head is_struct=\"true\">\n" +
            "\t<OPENSeqNo metadataid=\"OPENSeqNo\" />\n" +
            "\t<OPENReqTime metadataid=\"OPENReqTime\" />\n" +
            "</head>\n";
    //c端拆包请求体
    private String bodyValue_C_FROM = "body";

    //c端组包请求头
    private final static String headValue_C_TO = "<head is_struct=\"true\">\n" +
            "\t<OPENSeqNo metadataid=\"OPENSeqNo\" />\n" +
            "\t<OPENRspTime metadataid=\"OPENRspTime\" />\n" +
            "\t<OPENRetCode metadataid=\"OPENRetCode\" />\n" +
            "\t<OPENRetMsg metadataid=\"OPENRetMsg\" />\n" +
            "</head>\n";

    //c端组包请求体
    private String bodyValue_C_TO = "body";


    //p端拆包请求头
    private final static String headValue_P_FROM = null;
    //p端拆包请求体
    private String bodyValue_P_FROM = "rspData";

    //p端组包请求头
    private final static String headValue_P_TO = null;

    //P端组包请求体
    private String bodyValue_P_TO = "reqData";

    //定义文件请求头
    private String reqHead = "<head is_struct=\"true\">\n" +
            "\t<OPENSeqNo metadataid=\"OPENSeqNo\" />\n" +
            "\t<OPENReqTime metadataid=\"OPENReqTime\" />\n" +
            "</head>\n";

    //定义文件响应头
    private String resHead = "<head is_struct=\"true\">\n" +
            "\t<OPENSeqNo metadataid=\"OPENSeqNo\" />\n" +
            "\t<OPENRspTime metadataid=\"OPENRspTime\" />\n" +
            "\t<OPENRetCode metadataid=\"OPENRetCode\" />\n" +
            "\t<OPENRetMsg metadataid=\"OPENRetMsg\" />\n" +
            "</head>\n";
    //定义文件请求体
    private String reqBody = null;
    //定义文件响应体
    private String rspBody = null;

    public HashMap<String, String> getMessageHead() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("headValue_C_FROM", headValue_C_FROM);
        hashMap.put("headValue_C_TO", headValue_C_TO);
        hashMap.put("headValue_P_FROM", headValue_P_FROM);
        hashMap.put("headValue_P_TO", headValue_P_TO);
        hashMap.put("bodyValue_C_FROM", bodyValue_C_FROM);
        hashMap.put("bodyValue_C_TO", bodyValue_C_TO);
        hashMap.put("bodyValue_P_FROM", bodyValue_P_FROM);
        hashMap.put("bodyValue_P_TO", bodyValue_P_TO);
        hashMap.put("reqHead", reqHead);
        hashMap.put("resHead", resHead);
        hashMap.put("reqBody", reqBody);
        hashMap.put("rspBody", rspBody);
        return hashMap;
    }
}
