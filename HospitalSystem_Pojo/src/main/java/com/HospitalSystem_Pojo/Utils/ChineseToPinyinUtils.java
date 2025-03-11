package com.HospitalSystem_Pojo.Utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class ChineseToPinyinUtils {

    public static String convertNameToPinYin(String name) {
        var format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        var result = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (isChineseCharacter(c)) {
                try {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyins != null && pinyins.length > 0) {
                        result.append(pinyins[0].charAt(0));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    private static boolean isChineseCharacter(char c) {
        return String.valueOf(c).matches("[\\u4E00-\\u9FA5]");
    }
}
