package excelutil;

import bean.EXcelBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsumeXML {
    private Log log = LogFactory.getLog(ConsumeXML.class);
    private CommonUtil commonUtil = new CommonUtil();
    private static int indentationNum;  // 用于计算\t的数量，从而控制代码缩进

    /**
     * C端拆包文件
     * @param arrayList       arrayList
     * @param productFilePath 生成文件夹路径
     * @param folderName      文件夹（接口名称）
     * @param hashHeadInfo    报文头信息
     */
    public void produceC_From(ArrayList<EXcelBean> arrayList, String productFilePath, String folderName, HashMap hashHeadInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        String cons_from = productFilePath + folderName + "\\" + "cons_" + folderName + "V1_from_JSON" + ".xml";
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Json package_type=\"json\">\n"
        );
        indentationNum = 1;
        if (hashHeadInfo.get("headValue_C_FROM") != null) {  //需报文头
            stringBuffer.append("\t" + hashHeadInfo.get("headValue_C_FROM").toString().replace("\t", "\t\t").replace("</head>", "\t</head>"));

        }
        if(hashHeadInfo.get("bodyValue_C_FROM") != null){
            stringBuffer.append("\t<"+hashHeadInfo.get("bodyValue_C_FROM").toString()+" is_struct=\"true\">\n");
            indentationNum = indentationNum + 1;
        }

        for (EXcelBean e : arrayList) {
            if ("in".equals(e.getInOrOut())) { //输入字段
                stringBuffer = getNewStringBuffer(stringBuffer, e);
            } else {
                break;
            }
        }
        if (hashHeadInfo.get("bodyValue_C_FROM") != null) {  //需报文头
            stringBuffer.append("\t</"+hashHeadInfo.get("bodyValue_C_FROM").toString()+">\n");
        }

        stringBuffer.append("</Json>\n");

        boolean f = commonUtil.writeTargetFile(stringBuffer, cons_from);
        if (f) {
            log.info(folderName + "   C_From文件生成成功。");
        } else {
            log.warn(folderName + "   C_FROM文件生成失败！！！");
        }
    }

    /**
     * 生成c端组包
     * @param arrayList
     * @param productFilePath
     * @param folderName
     * @param hashHeadInfo
     */
    public void produceC_To(ArrayList<EXcelBean> arrayList, String productFilePath, String folderName, HashMap hashHeadInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        String cons_to = productFilePath + folderName + "\\" + "cons_" + folderName + "V1_to_JSON" + ".xml";

        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Json package_type=\"json\">\n"
        );
        indentationNum = 1;
        if (hashHeadInfo.get("headValue_C_TO") != null) {  //需报文头
            stringBuffer.append("\t" + hashHeadInfo.get("headValue_C_TO").toString().replace("\t", "\t\t").replace("</head>", "\t</head>"));

        }

        if (hashHeadInfo.get("bodyValue_C_TO") != null){
            stringBuffer.append("\t<"+hashHeadInfo.get("bodyValue_C_TO").toString()+" is_struct=\"true\">\n");
            indentationNum = indentationNum + 1;
        }

        for (EXcelBean e : arrayList) {
            if ("out".equals(e.getInOrOut()) && (e.getMapField() != null && e.getMapField() != "")) {
                stringBuffer = getNewStringBuffer(stringBuffer, e);
            }
        }

        if (hashHeadInfo.get("bodyValue_C_TO") != null){
            stringBuffer.append("\t</"+hashHeadInfo.get("bodyValue_C_TO").toString()+">\n");
        }
        stringBuffer.append("</Json>\n");
        boolean f = commonUtil.writeTargetFile(stringBuffer, cons_to);
        if (f) {
            log.info(folderName + "   C_TO文件生成成功。");
        } else {
            log.warn(folderName + "   C_TO文件生成失败！！！");
        }
    }

    /**
     *
     * @param stringBuffer
     * @param e
     * @return
     */
    public StringBuffer getNewStringBuffer(StringBuffer stringBuffer, EXcelBean e) {
        if ("struct".equals(e.getType())) {
            if ("start".equals(e.getStartOrEnd())) {
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getField() + " metadataid=\"" + e.getField() + "\" is_struct=\"true\" >\n");
                indentationNum++;
            } else {
                indentationNum--;
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "</" + e.getField() + ">\n");
            }
        } else if ("array".equals(e.getType())) {
            if ("start".equals(e.getStartOrEnd())) {
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getField() + " metadataid=\"" + e.getField() + "\" type=\"array\"  is_struct=\"true\" >\n");
                indentationNum++;
            } else {
                indentationNum--;
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "</" + e.getField() + ">\n");
            }
        } else {
            stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getField() + " metadataid=\"" + e.getField() + "\" />\n");
        }
        return stringBuffer;
    }
}
