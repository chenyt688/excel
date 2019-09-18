/**
 * 根据要求，生成JSON报文代码
 */


package bwutil;


public class ProductMessage {


    /**
     * 有报文头
     * 报文字段,隔开
     * @param str
     * @return
     */

    public StringBuffer productMessageHasHead(String str){
        String[] strArr = str.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(""+
                "JSONObject result = JSONObject.fromObject(true);\n" +
                "JSONObject head = JSONObject.fromObject(true);\n"+
                "JSONObject body = JSONObject.fromObject(true);\n"
        );
        for(String s: strArr)
            stringBuffer.append(""+
                "body.put(\""+s+"\",\"1234\");\n"
                );
        /*stringBuffer.append(""+
                "result.put(\"head\",head);\n"+
                "result.put(\"body\",body);\n"+
                "return result.toJSONString();\n"
        );*/
        stringBuffer.append(""+
                "result.put(\"head\",head);\n"+
                "result.put(\"body\",body);\n"+
                "System.out.println(result.toString());\n"
        );
        return stringBuffer;
    }

    /**
     * 无报文头
     * 报文字段,隔开
     * @param str
     * @return
     */

    public StringBuffer productMessageNoHasHead(String str){
        String[] strArr = str.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(""+
                "JSONObject result = JSONObject.fromObject(true);\n"
        );
        for(String s: strArr)
            stringBuffer.append(""+
                    "result.put(\""+s+"\",\"1234\");\n"
            );
        /*stringBuffer.append(""+
                "return result.toJSONString();\n"
        );*/
        stringBuffer.append(""+
                "System.out.println(result.toString());\n"
        );

        return stringBuffer;
    }
}
