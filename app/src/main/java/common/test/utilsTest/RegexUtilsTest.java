package common.test.utilsTest;


import org.junit.Test;

import java.util.List;

import common.utils.RegexUtils;

import static junit.framework.Assert.assertTrue;


/**
 * 描述:
 * Created by mjd on 2016/12/26.
 */

public class RegexUtilsTest {

    @Test
    public void isMobileExact() {
        CharSequence input = "13862124216";
        assertTrue(RegexUtils.isMobileExact(input));
    }

    @Test
    public void isTel() {
        CharSequence input = "0515-88888888";//or 0515 88888888 or 051588888888
        assertTrue(RegexUtils.isTel(input));
    }

    @Test
    public void isIDCard15() {
        CharSequence input = "130503670401001";
        assertTrue(RegexUtils.isIDCard15(input));
    }

    @Test
    public void isIDCard18() {
        CharSequence input = "320981199912222349";
        assertTrue(RegexUtils.isIDCard18(input));
    }

    @Test
    public void isEmail() {
        CharSequence input = "824419248@qq.com";
        assertTrue(RegexUtils.isEmail(input));
    }

    @Test
    public void isURL() {
        CharSequence input = "https://mjd507.github.io/";
        assertTrue(RegexUtils.isURL(input));
    }

    @Test
    public void isZh() {
        CharSequence input = "你好";
        assertTrue(RegexUtils.isZh(input));
    }


    @Test
    public void isUsername() {
        //取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
        CharSequence input = "mjd507";
        assertTrue(RegexUtils.isUsername(input));
    }

    @Test
    public void isDate() {
        //验证yyyy-MM-dd格式的日期校验，已考虑平闰年
        CharSequence input = "2016-11-11";
        assertTrue(RegexUtils.isDate(input));
    }

    @Test
    public void isIP() {
        CharSequence input = "172.16.29.205";
        assertTrue(RegexUtils.isIP(input));
    }

    @Test
    public void getMatches() {
        String content = "hello:world:java";
        String regex = ":";
        List<String> matches = RegexUtils.getMatches(regex, content);
        for (String s : matches) {
            System.out.println(s);
        }
    }


    @Test
    public void getSplits() {
        String content = "hello:world:java";
        String regex = ":";
        String[] splits = RegexUtils.getSplits(content, regex);
        for (String s : splits) {
            System.out.println(s);
        }
    }

    @Test
    public void getReplaceFirst() {
        String content = "hello:world:java";
        String regex = ":";
        String replacement = ":thank:";
        String s = RegexUtils.getReplaceFirst(content, regex, replacement);
        System.out.println(s);
    }

    @Test
    public void getReplaceAll() {
        String content = "hello:world:java";
        String regex = ":";
        String replacement = " ";
        String s = RegexUtils.getReplaceAll(content, regex, replacement);
        System.out.println(s);
    }

    @Test
    public void isMatch() {
        String regex = "[a-z]";
        String content = "f";
        boolean match = RegexUtils.isMatch(regex, content);
        assertTrue(match);
    }

}
