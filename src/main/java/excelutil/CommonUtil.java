package excelutil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    private Log log = LogFactory.getLog(CommonUtil.class);
    /**
     * 将stringbuffer中的字符写到文件中
     * @param stringBuffer      目标数据
     * @param filePath  文件路径
     */
    public boolean writeTargetFile(StringBuffer stringBuffer, String filePath) {
        boolean flag = true;
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(stringBuffer.toString());
            fw.close();
        } catch (IOException e) {
            flag = false;
            log.debug("文件生成失败！！！");
        }
        return flag;
    }


    //判断是否单元格是否为空和是否含有中文
    public boolean judgeIsTargetField(String cellValue){
        boolean flag = true;
        if(cellValue == null || cellValue == ""){
            flag = false;
        }else  {     //excel首个单元格不为空
            //判断是否含有中文
            flag=judgeChinese(cellValue);

        }
        return flag;
    }

    //判断字符是否含有中文,若没有中文返回true，否则返回false
    public boolean judgeChinese(String cellValue){
        boolean flag = true;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(cellValue.trim());
        if (m.find()) {
            flag = false;
        }
        return flag;
    }

    //判断字符中是否含有数字
    public boolean judgeIncludeNumber(String cellValue){
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(cellValue);
        if (m.matches()) {
            flag = true;
        }
        return  flag;
    }

    /**
     * 获取字符缩进\t数量
     * @param num 缩进\t个数
     * @return stringBuffer
     */
    public StringBuffer getIndentation(int num){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < num; i++){
            stringBuffer.append("\t");
        }
        return stringBuffer;
    }

    /**
     * 获取head
     * @param className
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static HashMap<String,String> getNewHead(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class c = Class.forName(className);
        Method m = c.getDeclaredMethod("getMessageHead");
        Object object = c.newInstance();
        HashMap<String,String> hashMap = (HashMap<String,String>)m.invoke(object);
        return hashMap;
    }
}
