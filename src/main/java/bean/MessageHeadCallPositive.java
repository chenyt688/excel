package bean;

import java.util.HashMap;

/**
 * 正调请求头
 */
public class MessageHeadCallPositive extends MessageHead {

    //c端拆包请求头
    private final static String headValue_C_FROM = null;
    //c端拆包请求体
    private String bodyValue_C_FROM = "reqData";
    //c端组包请求头
    private final static String headValue_C_TO = null;

    //c端组包请求体
    private String bodyValue_C_TO = "rspData";

    //p端拆包请求头
    private final static String headValue_P_FROM = "<head is_struct=\"true\">\n" +
            "\t<tenantId metadataid=\"tenantId\" />\n" +
            "\t<channelNo metadataid=\"channelNo\" />\n" +
            "\t<merchantId metadataid=\"merchantId\" />\n" +
            "\t<organId metadataid=\"organId\" />\n" +
            "\t<empNo metadataid=\"empNo\" />\n" +
            "\t<requestSerialNo metadataid=\"requestSerialNo\" />\n" +
            "\t<requestTime metadataid=\"requestTime\" />\n" +
            "\t<serialNo metadataid=\"serialNo\" />\n" +
            "\t<transTime metadataid=\"transTime\" />\n" +
            "\t<returnFlag metadataid=\"returnFlag\" />\n" +
            "\t<returnCode metadataid=\"returnCode\" />\n" +
            "\t<returnMessage metadataid=\"returnMessage\" />\n" +
            "</head>\n";

    //p端拆包请求体
    private String bodyValue_P_FROM = "body";

    //p端组包请求头

    private final static String headValue_P_TO = "<head is_struct=\"true\">\n" +
            "\t<tenantId metadataid=\"tenantId\" expression=\"000\" />\n" +
            "\t<channelNo metadataid=\"channelNo\" expression=\"NBYH\" />\n" +
            "\t<markeClue metadataid=\"markeClue\" expression=\"07\" />\n" +
            "\t<merchtId metadataid=\"merchtId\" expression=\"待提供\" />\n" +
            "\t<organId metadataid=\"organId\" expression=\"待提供\" />\n" +
            "\t<empNo metadataid=\"empNo\" />\n" +
            "\t<token metadataid=\"token\" />\n" +
            "\t<requestSerialNo metadataid=\"requestSerialNo\" />\n" +
            "\t<requestTime metadataid=\"requestTime\" />\n" +
            "\t<rebackUrl metadataid=\"rebackUrl\" />\n" +
            "\t<notifyUrl metadataid=\"notifyUrl\" />\n" +
            "\t<deviceId metadataid=\"deviceId\" />\n" +
            "\t<longitude metadataid=\"longitude\" />\n" +
            "\t<latitude metadataid=\"latitude\" />\n" +
            "</head>\n";

    //P端组包请求体
    private String bodyValue_P_TO = "body";

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
    private String reqBody = "reqData";
    //定义文件响应体
    private String rspBody = "rspData";

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
