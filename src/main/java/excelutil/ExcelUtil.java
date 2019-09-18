package excelutil;

import bean.EXcelBean;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;


public class ExcelUtil {

    private CommonUtil commonUtil = new CommonUtil();
    private ProvideXML provideXML = new ProvideXML();
    private ConsumeXML consumeXML = new ConsumeXML();
    private ServerXML serverXML = new ServerXML();
    private EXcelBean excelBean = null;
    private Log log = LogFactory.getLog(ExcelUtil.class);
    private HashMap hashHeadInfo = null;
    private ArrayList<EXcelBean> arrayList = null;

    /*
     * 读取Excel海量数据
     * @param excelFilePath    excel文件路径
     * @param startSheetIndex  读取sheet开始的下标
     * @param productFilePath  文件生成路径
     * @param callBack         回调接口报文头的类名
     * @param callPositive     正调接口报文头的类名
     */
    public void getMassExcelContent(String excelFilePath, int startSheetIndex, String productFilePath, String callBack, String callPositive) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        FileInputStream in = null;
        try {
            in = new FileInputStream(excelFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件


        //获取工作区间的个数
        int sheetNum = wk.getNumberOfSheets();

        //遍历工作区间
        for (; startSheetIndex < sheetNum; startSheetIndex++) {
            Sheet sheet = wk.getSheetAt(startSheetIndex);
            String folderName = sheet.getSheetName();   //文件名（以接口名命名）

            //获取当前工作区间的接口回调信息
            HashMap<String, String> hashMap = judgeInterfaceIsCallback(sheet);
            if (hashMap.containsKey(folderName)) {      //接口名和工作区间可以对应
                String isCallback = hashMap.get(folderName);
                if ("Y".equals(isCallback)) { //回调
                    hashHeadInfo = CommonUtil.getNewHead(callBack);
                } else {                      //正调
                    hashHeadInfo = CommonUtil.getNewHead(callPositive);
                }
                //生成接口空文件
                makeDirAndFile(sheet, productFilePath);
                //生成metada.xml文件,并返回ArrayList(字段信息)
                arrayList = productMetadata(sheet, productFilePath);

                //生成定义文件
                serverXML.produceServerXML(arrayList, productFilePath, folderName, hashHeadInfo);
                //生成cons_from文件
                consumeXML.produceC_From(arrayList, productFilePath, folderName, hashHeadInfo);
                //生成cons_to文件
                consumeXML.produceC_To(arrayList, productFilePath, folderName, hashHeadInfo);

                //生成pvd_from文件
                provideXML.produceP_From(arrayList, productFilePath, folderName, hashHeadInfo);
                //生成pvd_to文件
                provideXML.produceP_To(arrayList, productFilePath, folderName, hashHeadInfo);

            }
        }
    }

    /**
     * 获取接口是否是回调 excel表格中接口名在第一行第二列  是否回调在第四行第六列
     *
     * @param sheet
     * @return hashMap
     */
    public HashMap<String, String> judgeInterfaceIsCallback(Sheet sheet) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String interfaceName = "";
        String isCallback = "";
        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                interfaceName = row.getCell(1).getStringCellValue().trim();
            }
            if (rowNum == 3) {
                isCallback = row.getCell(6).getStringCellValue().trim();
                break;
            }
        }
        System.out.println(interfaceName + "   是否回调:  " + isCallback);
        hashMap.put(interfaceName, isCallback);
        return hashMap;
    }

    /**
     * 创建文件夹和xml文件(空文件)  P端、C端拆组包文件 ，定义文件 ，metadata.xml
     *
     * @param sheet           工作区间
     * @param productFilePath 文件生成路径
     */
    public void makeDirAndFile(Sheet sheet, String productFilePath) {
        String folderName = sheet.getSheetName();

        String metadataName = productFilePath + folderName + "\\metadata" + ".xml";
        String cons_from = productFilePath + folderName + "\\" + "cons_" + folderName + "V1_from_JSON" + ".xml";
        String cons_to = productFilePath + folderName + "\\" + "cons_" + folderName + "V1_to_JSON" + ".xml";
        String prvd_from = productFilePath + folderName + "\\" + "prvd_" + folderName + "V1_from_JSON" + ".xml";
        String prvd_to = productFilePath + folderName + "\\" + "prvd_" + folderName + "V1_to_JSON" + ".xml";
        String service = productFilePath + folderName + "\\" + "service_" + folderName + "V1" + ".xml";

        File file1 = new File(metadataName);
        File file2 = new File(cons_from);
        File file3 = new File(cons_to);
        File file4 = new File(prvd_from);
        File file5 = new File(prvd_to);
        File file6 = new File(service);

        //生成目录
        File fileParent = file1.getParentFile();
        //如果文件存在，删除目录和目录下的文件，重新新建
        if (fileParent.exists()) {
            if (file1.exists()) {
                file1.delete();
            }
            if (file2.exists()) {
                file2.delete();
            }
            if (file3.exists()) {
                file3.delete();
            }
            if (file4.exists()) {
                file4.delete();
            }
            if (file5.exists()) {
                file5.delete();
            }
            if (file6.exists()) {
                file6.delete();
            }
            if (fileParent.exists()) {
                fileParent.delete();
            }

        }

        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }

        //生成文件
        try {
            if (!file1.exists()) {
                file1.createNewFile();
            }
            if (!file2.exists()) {
                file2.createNewFile();
            }
            if (!file3.exists()) {
                file3.createNewFile();
            }
            if (!file4.exists()) {
                file4.createNewFile();
            }
            if (!file5.exists()) {
                file5.createNewFile();
            }
            if (!file6.exists()) {
                file6.createNewFile();
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * 生成metadata.xml文件 并将字段信息存储在ArrayList中
     *
     * @param sheet           工作区间
     * @param productFilePath 文件生成路径
     * @return ArrayList
     */
    public ArrayList<EXcelBean> productMetadata(Sheet sheet, String productFilePath) {
        ArrayList<EXcelBean> arrayList = new ArrayList<EXcelBean>();
        StringBuffer stringBuffer = new StringBuffer();
        String metadataFilePath = productFilePath + sheet.getSheetName() + "\\metadata" + ".xml";
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<metadata>\n"
        );
        int rowAllNum = sheet.getLastRowNum();
        int inputOrOutputFlag = 0; //标注是输入（0）还是输出字段  （1）
        for (Row row : sheet) {
            if (row.getRowNum() > rowAllNum || row.getCell(0) == null) {
                break;
            }
            //用于存储字段信息
            String cellValue = row.getCell(0).getStringCellValue().trim();
            if (cellValue.equals("输入接口")) {
                inputOrOutputFlag = 0;
            }
            if (cellValue.equals("输出接口")) {
                inputOrOutputFlag = 1;
            }
            //Excel字段从第5行开始，判断第一列是否目标字段
            if (row.getRowNum() > 5 && commonUtil.judgeIsTargetField(cellValue)) {  //将空格、中文排除
                excelBean = getNewExcelBean(row, inputOrOutputFlag); //当前行数据信息
                if ("struct".equals(excelBean.getType())) {        //首次判断为结构体(excel表格有明显标志)
                    arrayList.add(excelBean);
                } else if ("array".equals(excelBean.getType())) {   //首次判断为数组(excel表格有明显标志)
                    arrayList.add(excelBean);
                    if ("start".equals(excelBean.getStartOrEnd())) {  //若为数组
                        stringBuffer.append(excelBean.getComStr());
                    }
                } else {    //非结构体、数组
                    arrayList.add(excelBean);
                    stringBuffer.append(excelBean.getComStr());
                }
            }

        }
        stringBuffer.append("</metadata>\n");

        //将stringbuffer中的字符写到文件中
        boolean f = commonUtil.writeTargetFile(stringBuffer, metadataFilePath);
        if (f) {
            log.info("metadata.xml文件已生成。");
        } else {
            log.warn("metadata.xml文件生成失败！！！");
        }

        return arrayList;

    }

    /**
     * 获取字段对象
     *
     * @param row  excel表格每行数据
     * @param flag 输入或者输出字段标志
     * @return ExcelBean
     */
    public EXcelBean getNewExcelBean(Row row, int flag) {

        EXcelBean eXcelBean = new EXcelBean();
        String cellValue = row.getCell(0).getStringCellValue().trim();
        eXcelBean.setField(cellValue);

        eXcelBean.setFieldName(row.getCell(1).getStringCellValue().replaceAll("\r|\n", "").trim());

        //判断字段类型  若映射字段为JSON Struct为结构体  若为Array 为数组，否则为目标字段
        String type = row.getCell(2).getStringCellValue().replaceAll("\r|\n", "").trim();
        if ("json".equals(type.toLowerCase()) || "struct".equals(type.toLowerCase())) {
            eXcelBean.setType("struct");
            eXcelBean.setField(cellValue.replaceAll("\\d+", "").trim());
        } else if ("array".equals(type.toLowerCase())) {
            eXcelBean.setType("array");
            eXcelBean.setField(cellValue.replaceAll("\\d+", "").trim());
        } else {
            //元数据都用String类型
            eXcelBean.setType("String");
        }
        String statu = row.getCell(3).getStringCellValue().replaceAll("\r|\n", "");
        if ("start".equals(statu.toLowerCase())) {
            eXcelBean.setStartOrEnd("start");
            if (!"array".equals(eXcelBean.getType())) {
                eXcelBean.setType("struct");
                eXcelBean.setField(cellValue.replaceAll("\\d+", "").trim());
            }
        } else if ("end".equals(statu.toLowerCase())) {
            eXcelBean.setStartOrEnd("end");
            if (!"array".equals(eXcelBean.getType())) {
                eXcelBean.setType("struct");
                eXcelBean.setField(cellValue.replaceAll("\\d+", "").trim());
            }
        } else {
            eXcelBean.setStartOrEnd("");
        }


        String mapField = row.getCell(5).getStringCellValue().replaceAll("\r|\n", "").trim();
        if ("array".equals(eXcelBean.getType()) || "struct".equals(eXcelBean.getType())) {
            eXcelBean.setMapField(mapField.replaceAll("\\d+", "").trim());
            eXcelBean.setField(cellValue.replaceAll("\\d+", "").trim());
        } else {
            eXcelBean.setMapField(mapField);

        }
        String conStr = "";
        if ("array".equals(eXcelBean.getType())) {
            conStr = "\t<" + row.getCell(0).getStringCellValue().replaceAll("\r|\n|\\d+", "") +
                    " type=\"" + eXcelBean.getType() + "\" />\n";

        } else {
            conStr = "\t<" + row.getCell(0).getStringCellValue().replaceAll("\r|\n", "") +
                    " type=\"" + eXcelBean.getType() + "\"" +
                    " length=\"" + row.getCell(3).getStringCellValue().replaceAll("\r|\n", "") + "\"" +
                    " isPin=\"false\"" +
                    " chinese_name=\"" + row.getCell(1).getStringCellValue().replaceAll("\r|\n", "") + "\"/>\n";
        }

        eXcelBean.setComStr(conStr);
        //标识字段是输出还是输入
        if (flag == 0) {
            eXcelBean.setInOrOut("in");
        } else {
            eXcelBean.setInOrOut("out");
        }
        return eXcelBean;
    }


}
