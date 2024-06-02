package cn.edu.szu.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
    // 获取名字首字母的方法
    public static String getFirstLetter(String input) {
        char firstLetter = input.charAt(0);
        if (isChinese(firstLetter)) {
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(firstLetter, getPinyinOutputFormat());
                if (pinyinArray != null && pinyinArray.length > 0) {
                    firstLetter = pinyinArray[0].charAt(0); // 取拼音的首字母
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            firstLetter = Character.toUpperCase(firstLetter); // 非汉字直接取大写字母
        }
        return Character.toString(Character.toUpperCase(firstLetter));
    }

    private static boolean isChinese(char c) {
        return Character.toString(c).matches("[\\u4E00-\\u9FA5]");
    }

    // 获取拼音格式
    private static HanyuPinyinOutputFormat getPinyinOutputFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        return format;
    }
}
