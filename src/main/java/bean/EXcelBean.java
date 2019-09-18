package bean;

public class EXcelBean {
    private String field;       //字段
    private String fieldName;   //字段中文名
    private String type;        //字段类型
    private String mapField;    //映射字段
    private String comStr;      //组装字符
    private String inOrOut;     //输入、输出类型
    private String startOrEnd;  //结构体开始、结束标志

    public EXcelBean() {
    }

    public String getStartOrEnd() {
        return startOrEnd;
    }

    public void setStartOrEnd(String startOrEnd) {
        this.startOrEnd = startOrEnd;
    }
    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMapField() {
        return mapField;
    }

    public void setMapField(String mapField) {
        this.mapField = mapField;
    }

    public String getComStr() {
        return comStr;
    }

    public void setComStr(String comStr) {
        this.comStr = comStr;
    }

    @Override
    public String toString() {
        return "EXcelBean{" +
                "field='" + field + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", type='" + type + '\'' +
                ", mapField='" + mapField + '\'' +
                ", comStr='" + comStr + '\'' +
                ", inOrOut='" + inOrOut + '\'' +
                ", startOrEnd='" + startOrEnd + '\'' +
                '}';
    }
}
