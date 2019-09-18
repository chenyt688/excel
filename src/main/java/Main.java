import excelutil.ExcelUtil;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {

        ExcelUtil excelUtil = new ExcelUtil();
        //excel文件路径
        String excelFilePath = "G:\\excel/宁波银行开放平台接口文档_和包＿v1.0.2(1).xlsx";
        //生成目标文件路径
        String productFilePath = "E:\\SoftTool\\IntelliJ IDEA 2018.1.5\\workspace\\excel\\src\\main\\resources\\HB\\";
        //工作区间遍历开始下标，0开始计数
        int startSheetIndex = 5;
        //回调报文头信息  类名称（相对路径）
        String callBack = "bean.MessageHeadCallBack";
        //正调报文头信息  类名称（相对路径）
        String callPositive = "bean.MessageHeadCallPositive";

        //读取Excel文件，并生成目标文件
        try {
            excelUtil.getMassExcelContent(excelFilePath, startSheetIndex, productFilePath,callBack,callPositive);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
