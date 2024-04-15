package cn.edu.szu.common.pojo;

//状态码
public class Code {
    //操作成功代码
    public static final Integer SAVE_OK = 20011;
    public static final Integer DELETE_OK = 20021;
    public static final Integer UPDATE_OK = 20031;
    public static final Integer GET_OK = 20041;

    //操作失败代码
    public static final Integer SAVE_ERR = 20010;
    public static final Integer DELETE_ERR = 20020;
    public static final Integer UPDATE_ERR = 20030;
    public static final Integer GET_ERR = 20040;

    public static final Integer ID_NOT_FOUND_ERR = 10010;
    public static final Integer AUTH_ERR = 10020;
}
