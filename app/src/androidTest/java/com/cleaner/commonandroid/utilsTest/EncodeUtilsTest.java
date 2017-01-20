package com.cleaner.commonandroid.utilsTest;

import org.junit.Test;

import common.utils.EncodeUtils;
import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/5.
 */

public class EncodeUtilsTest {

    @Test
    public void urlEncode() {
        String url = "https://mjd507.github.io/";
        String urlEncode = EncodeUtils.urlEncode(url);
        LogUtils.d("EncodeUtilsTest", "URL 编码后为: " + urlEncode);
        String urlDecode = EncodeUtils.urlDecode(urlEncode);
        LogUtils.d("EncodeUtilsTest", "URL 解码后为: " + urlDecode);
    }

    @Test
    public void base64EncodeByte() {
        String str = "mjd507";
        byte[] bytes = EncodeUtils.base64Encode(str.getBytes());
        LogUtils.d("EncodeUtilsTest", "Base64 编码后为: " + new String(bytes));
        byte[] bytes1 = EncodeUtils.base64Decode(bytes);
        LogUtils.d("EncodeUtilsTest", "Base64 解码后为:" + new String(bytes1));

    }


}
