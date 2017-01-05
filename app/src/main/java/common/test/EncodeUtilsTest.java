package common.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.junit.Test;

import common.utils.EncodeUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/5.
 */

public class EncodeUtilsTest extends Activity {

    private byte[] base64EncodeByte;
    private String base64Encode2Str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        Button btnBase64EncodeByte = new Button(this);
        btnBase64EncodeByte.setText("base64 编码成 字节");
        btnBase64EncodeByte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base64EncodeByte = base64EncodeByte();
            }
        });
        Button btnBase64DecodeByte = new Button(this);
        btnBase64DecodeByte.setText("base64 解码成 字节");
        btnBase64DecodeByte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base64DecodeByte(base64EncodeByte);
            }
        });
        Button btnBase64Encode2Str = new Button(this);
        btnBase64Encode2Str.setText("base64 编码成 字符串");
        btnBase64Encode2Str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base64Encode2Str = base64Encode2Str();
            }
        });
        Button btnBase64Decode2Str = new Button(this);
        btnBase64Decode2Str.setText("base64 解码成 字符串");
        btnBase64Decode2Str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base64Decode2Str(base64Encode2Str);
            }
        });
        contentView.addView(btnBase64EncodeByte);
        contentView.addView(btnBase64DecodeByte);
        contentView.addView(btnBase64Encode2Str);
        contentView.addView(btnBase64Decode2Str);
        setContentView(contentView);
    }

    public byte[] base64EncodeByte() {
        byte[] b = new byte[]{'a', 'b', 'c'};
        byte[] bytes = EncodeUtils.base64Encode(b);
        ToastUtils.showShort(getApplicationContext(), "Base64 abc字节编码后为: " + new String(bytes));
        return bytes;
    }


    public void base64DecodeByte(byte[] base64EncodeByte) {
        byte[] bytes1 = EncodeUtils.base64Decode(base64EncodeByte);
        ToastUtils.showShort(getApplicationContext(), "Base64 abc字节解码后为: " + new String(bytes1));
    }

    public String base64Encode2Str() {

        String str = "hello";
        String s = EncodeUtils.base64Encode2String(str.getBytes());
        ToastUtils.showShort(getApplicationContext(),"Base64 hello字节编码后为: " + s);
        return s;
    }

    public void base64Decode2Str(String str) {
        byte[] bytes2 = EncodeUtils.base64Decode(str);
        ToastUtils.showShort(getApplicationContext(),"Base64 hello字符串解码后为: " + new String(bytes2));
    }

    @Test
    public void urlEncode() {
        String url = "https://mjd507.github.io/";
        String urlEncode = EncodeUtils.urlEncode(url);
        System.out.println("URL 编码后为: " + urlEncode);
        String urlDecode = EncodeUtils.urlDecode(urlEncode);
        System.out.println("URL 解码后为: " + urlDecode);
    }

}
