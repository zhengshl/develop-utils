import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class HanYuPinYinUtil {
    private static String getPinYin(String str){
        char[] c1 = str.toCharArray();
        String[] s2;
        HanyuPinyinOutputFormat hpof = new HanyuPinyinOutputFormat();
        hpof.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        hpof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        hpof.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder pinYin = new StringBuilder();
        try {
            for (char aC1 : c1) {
                if (Character.toString(aC1).matches("[\\u4E00-\\u9FA5]+")) {
                    s2 = PinyinHelper.toHanyuPinyinStringArray(aC1, hpof);
                    pinYin.append(s2[0]);
                }
            }
            return pinYin.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pinYin.toString();
    }

    private static String getPinYinHeadChar(String str){
        StringBuilder convert = new StringBuilder();
        for (int i = 0; i < str.length(); i ++) {
            char word = str.charAt(i);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            }
        }
        return convert.toString().toUpperCase();
    }

    public static void main(String[] args) {
        String str = "刘德华";
        String pinyin = getPinYin(str);
        System.out.println("pinyin:" + pinyin);
        String headChar = getPinYinHeadChar(str);
        System.out.println("headChar:" + headChar);
    }
}
