package bjy.edu.android_learn.util;

public class StringUtil {

    /**
     * @param size 中文字符的长度
     * @return 配合 new String(char[] chars) 使用
     */
    public static char[] getRandomChnCharArray(int size){
        if (size < 1)
            throw new IllegalArgumentException("size must be positive integer");

        char[] chars = new char[size];
        for (int i=0; i<size; i++){
            chars[i] = getRandomChnChar();
        }

        return chars;
    }

    /**
     * @return 单个随机汉字字符
     */
    public static char getRandomChnChar(){
        return (char) (0x4e00 + Math.random() * (0x9fa5 - 0x4e00 + 1));
    }
}