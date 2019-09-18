package bwutil;


import org.json.JSONObject;

public class BaoWenJson {
    public static void productJson(){
        JSONObject result = new JSONObject(true);
        JSONObject head = new JSONObject(true);
        JSONObject body = new JSONObject(true);
        body.put("a","1234");
        body.put("b","1234");
        body.put("c","1234");
        body.put("d","1234");
        body.put("e","1234");
        body.put("f","1234");
        result.put("head",head);
        result.put("body",body);
        System.out.println(result.toString());
    }

    public static void main(String[] args) {
        productJson();
    }


}
