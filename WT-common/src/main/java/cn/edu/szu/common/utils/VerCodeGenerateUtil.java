package cn.edu.szu.common.utils;

/**
 * TODO
 *
 * @author DB
 * <br>CreateDate 2021/9/13 2:07
 */
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Classname VerCodeGenerateUtil
 * @Description 生成验证码工具类  用于生成一个六位随机验证码
 * @Date 2021/9/7 14:23
 * @Created by DB
 */
public class VerCodeGenerateUtil {
    //验证码包含的字段，可自己设置
    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();
    //    生成 6 位数的随机数字
    public static Integer generateVerCode() {
        //	如果是六位，就生成大小为 6 的数组
        char[] numbers = new char[6];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return Integer.valueOf(new String(numbers));
    }
}

