package excelutil;

import bean.EXcelBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ProvideXML {

    private Log log = LogFactory.getLog(ProvideXML.class);
    private CommonUtil commonUtil = new CommonUtil();
    private static int indentationNum;  // 用于计算\t的数量，从而控制代码缩

    /**
     * 生成p端拆包
     * @param arrayList
     * @param productFilePath
     * @param folderName
     * @param hashHeadInfo
     */
    public void produceP_From(ArrayList<EXcelBean> arrayList, String productFilePath, String folderName, HashMap hashHeadInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        String prvd_from = productFilePath + folderName + "\\" + "prvd_" + folderName + "V1_from_JSON" + ".xml";
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Json package_type=\"json\">\n"
        );

        indentationNum = 1;  // 用于计算\t的数量，从而控制代码缩进
        if (hashHeadInfo.get("headValue_P_FROM") != null) {  //需报文头
            stringBuffer.append("\t" + hashHeadInfo.get("headValue_P_FROM").toString().replace("\t", "\t\t").replace("</head>", "\t</head>"));

        }

        if(hashHeadInfo.get("bodyValue_P_FROM") != null){
            stringBuffer.append("\t<"+hashHeadInfo.get("bodyValue_P_FROM").toString()+" is_struct=\"true\">\n");
            indentationNum++;
        }

        for (EXcelBean e : arrayList) {
            if ("out".equals(e.getInOrOut()) && (e.getMapField() != null && e.getMapField() != "")) {
                stringBuffer = getNewStringBuffer(stringBuffer, e);
            }

        }

        if(hashHeadInfo.get("bodyValue_P_FROM") != null){
            stringBuffer.append("\t</"+hashHeadInfo.get("bodyValue_P_FROM").toString()+">\n");

        }
        stringBuffer.append("</Json>\n");
        boolean f = commonUtil.writeTargetFile(stringBuffer, prvd_from);
        if (f) {
            log.info(folderName + "   P_FROM文件生成成功。");
        } else {
            log.warn(folderName + "   P_FROM文件生成失败！！！");
        }
    }

    /**
     * 生成p端组包
     * @param arrayList
     * @param productFilePath
     * @param folderName
     * @param hashHeadInfo
     */
    public void produceP_To(ArrayList<EXcelBean> arrayList, String productFilePath, String folderName, HashMap hashHeadInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        String prvd_to = productFilePath + folderName + "\\" + "prvd_" + folderName + "V1_to_JSON" + ".xml";
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Json package_type=\"json\">\n"
        );
        indentationNum = 1;  // 用于计算\t的数量，从而控制代码缩进
        if (hashHeadInfo.get("headValue_P_TO") != null) {  //需报文头
            stringBuffer.append("\t" + hashHeadInfo.get("headValue_P_TO").toString().replace("\t", "\t\t").replace("</head>", "\t</head>"));

        }
        if(hashHeadInfo.get("bodyValue_P_TO") != null){
            stringBuffer.append("\t<"+hashHeadInfo.get("bodyValue_P_TO").toString()+" is_struct=\"true\">\n");
            indentationNum++;
        }

        for (EXcelBean e : arrayList) {
            if ("in".equals(e.getInOrOut()) && (e.getMapField() != null && e.getMapField() != "")) {
                stringBuffer = getNewStringBuffer(stringBuffer, e);
            }
        }

        if(hashHeadInfo.get("bodyValue_P_TO") != null){
            stringBuffer.append("\t</"+hashHeadInfo.get("bodyValue_P_TO").toString()+">\n");

        }
        stringBuffer.append("</Json>\n");
        boolean f = commonUtil.writeTargetFile(stringBuffer, prvd_to);
        if (f) {
            log.info(folderName + "   P_TO文件生成成功。");
        } else {
            log.warn(folderName + "   P_TO文件生成失败！！！");
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
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getMapField() + " metadataid=\"" + e.getField() + "\" is_struct=\"true\" >\n");
                indentationNum++;
            } else {
                indentationNum--;
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "</" + e.getMapField() + ">\n");
            }
        } else if ("array".equals(e.getType())) {
            if ("start".equals(e.getStartOrEnd())) {
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getMapField() + " metadataid=\"" + e.getField() + "\" type=\"array\"  is_struct=\"true\" >\n");
                indentationNum++;
            } else {
                indentationNum--;
                stringBuffer.append(commonUtil.getIndentation(indentationNum) + "</" + e.getMapField() + ">\n");
            }
        } else {
            stringBuffer.append(commonUtil.getIndentation(indentationNum) + "<" + e.getMapField() + " metadataid=\"" + e.getField() + "\" />\n");
        }
        return stringBuffer;
    }
}
