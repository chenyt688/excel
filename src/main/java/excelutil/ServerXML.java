package excelutil;

import bean.EXcelBean;
import bean.MessageHeadCallBack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerXML {
    private CommonUtil commonUtil = new CommonUtil();
    MessageHeadCallBack messageHeadCallBack = new MessageHeadCallBack();
    private Log log = LogFactory.getLog(ServerXML.class);

    /**
     * 生成定义文件
     *
     * @param arrayList
     * @param productFilePath
     * @param folderName
     * @param hashMapHeadInfo
     */
    public void produceServerXML(ArrayList<EXcelBean> arrayList, String productFilePath, String folderName, HashMap hashMapHeadInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        String serviceFilePath = productFilePath + folderName + "\\" + "service_" + folderName + "V1" + ".xml";
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<S" + folderName + ">\n" +
                "\t<request>\n" +
                "\t\t<sdoroot>\n" +
                "\t\t\t" + hashMapHeadInfo.get("reqHead").toString().replace("\t", "\t\t\t\t").replace("</head>", "\t\t\t</head>")
        );

        int indentationNum = 3;  // 用于计算\t的数量，从而控制代码缩进
        int flag = 0; //用于判断是输出还是输入
        if (hashMapHeadInfo.get("reqBody") != null){
            stringBuffer.append("\t\t\t<"+hashMapHeadInfo.get("reqBody")+" is_struct=\"true\">\n");
            indentationNum ++;
        }
        for (EXcelBean e : arrayList) {
            if ("out".equals(e.getInOrOut()) && flag == 0) {   //只执行一次
                if (hashMapHeadInfo.get("reqBody") != null){
                    indentationNum --;
                    stringBuffer.append("\t\t\t</"+hashMapHeadInfo.get("reqBody")+">\n");
                }

                stringBuffer.append("\t\t</sdoroot>\n" +
                        "\t</request>\n" +
                        "\t<response>\n" +
                        "\t\t<sdoroot>\n" +
                        "\t\t\t" + hashMapHeadInfo.get("rspHead").toString().replace("\t", "\t\t\t\t").replace("</head>", "\t\t\t</head>")
                );
                if(hashMapHeadInfo.get("rspBody") != null){
                    stringBuffer.append("\t\t\t<"+hashMapHeadInfo.get("rspBody")+" is_struct=\"true\">\n");
                    indentationNum ++;
                }
                flag = 1;
            }
            if ("struct".equals(e.getType())) {
                if ("start".equals(e.getStartOrEnd())) {
                    stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getField() + " metadataid=\"" + e.getField() + "\" is_struct=\"true\" >\n");
                    indentationNum = indentationNum + 1;
                } else {
                    indentationNum = indentationNum - 1;
                    stringBuffer.append(commonUtil.getIndentation(indentationNum) + "</" + e.getField() + ">\n");
                }
            } else if ("array".equals(e.getType())) {
                if ("start".equals(e.getStartOrEnd())) {
                    stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getField() + " metadataid=\"" + e.getField() + "\" type=\"array\" >\n");
                    stringBuffer.append(commonUtil.getIndentation(indentationNum + 1) + "<sdo>\n");
                    indentationNum = indentationNum + 2;
                } else {
                    stringBuffer.append(commonUtil.getIndentation(indentationNum - 1) + "</sdo>\n");
                    stringBuffer.append(commonUtil.getIndentation(indentationNum - 2) + "</" + e.getField() + ">\n");
                    indentationNum = indentationNum - 2;
                }
            } else {
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getField() + " metadataid=\"" + e.getField() + "\" />\n");
            }
        }
        if (hashMapHeadInfo.get("rspBody") != null){
            indentationNum --;
            stringBuffer.append("\t\t\t</"+hashMapHeadInfo.get("rspBody").toString()+">\n");
        }
        stringBuffer.append(
                "\t\t</sdoroot>\n" +
                        "\t</response>\n" +
                        "</S" + folderName + ">\n"
        );
        boolean f = commonUtil.writeTargetFile(stringBuffer, serviceFilePath);
        if (f) {
            log.info(folderName + "接口定义文件生成成功。");
        } else {
            log.warn(folderName + "接口定义文件生成失败！！！");
        }

    }
}
