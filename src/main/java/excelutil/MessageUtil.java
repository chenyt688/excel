/**
 * 根据要求，生成JSON报文代码
 */


package excelutil;


import bean.EXcelBean;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageUtil {

    private int flag = 0; //结构体或者数组标志
    private JSONObject tempJsonObj = new JSONObject();
    public void productMessages(ArrayList<EXcelBean> arrayList,HashMap hashHeadInfo){
        JSONObject result = new JSONObject();
        if(hashHeadInfo.get("headValue_C_FROM") != null){       //有报文头
            JSONObject head = new JSONObject();
            JSONObject body = new JSONObject();
            body = getNewJSON(body,arrayList);
            result.put("head",head);
            result.put("body",body);
        }else {
            result = getNewJSON(result,arrayList);
        }

    }

    public JSONObject getNewJSON(JSONObject jsonObject,ArrayList<EXcelBean> arrayList){
        for (EXcelBean e : arrayList) {
            if ("in".equals(e.getInOrOut())) { //输入字段
                if("struct".equals(e.getType()) && "start".equals(e.getStartOrEnd())){

                    flag = 1;
                }
                jsonObject.put(e.getField(),"1234");
            } else {
                break;
            }
        }
        return jsonObject;
    }
}
