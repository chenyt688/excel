package bean;

import java.util.HashMap;

public abstract class MessageHead {
    private String headValue_C_FROM;   //c端拆包请求头
    private String headValue_C_TO;     //c端组包请求头
    private String headValue_P_FROM;   //p端拆包请求头
    private String headValue_P_TO;     //p端组包请求头
    private String bodyValue_C_FROM;   //c端拆包请求体
    private String bodyValue_C_TO;     //c端组包请求体
    private String bodyValue_P_FROM;   //p端拆包请求体
    private String bodyValue_P_TO;     //P端组包请求体
    private String reqHead;            //定义文件请求头
    private String resHead;            //定义文件响应头
    private String reqBody;            //定义文件请求体
    private String rspBody;            //定义文件响应体

    public abstract HashMap<String,String> getMessageHead();
}
