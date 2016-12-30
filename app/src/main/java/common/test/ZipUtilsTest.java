package common.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;

import common.utils.FileUtils;
import common.utils.SDCardUtils;
import common.utils.ToastUtils;
import common.utils.ZipUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/30.
 */

public class ZipUtilsTest extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        Button btnZip = new Button(this);
        btnZip.setText("压缩SD卡上名为 hello.txt 的文件");
        btnZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipFiles();
            }
        });
        Button btnZip2 = new Button(this);
        btnZip2.setText("压缩SD卡上名为 hello.txt 的文件,带comment");
        btnZip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipFiles2();
            }
        });
        Button btnUnZip = new Button(this);
        btnUnZip.setText("解压SD卡上名为 hello.txt 的文件");
        btnUnZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unZipFile();
            }
        });

        //创建一个目录(含有两个文件)
        File fileDir = new File(SDCardUtils.getSDCardPath() + "test" + File.separator);
        boolean dirExistsOrCreated = FileUtils.isDirExistsOrCreated(fileDir);
        if (!dirExistsOrCreated) return;
        File file1 = new File(fileDir + File.separator + "1.txt");
        File file2 = new File(fileDir + File.separator + "2.txt");
        FileUtils.writeFileFromString(file1, "hello 1", false);
        FileUtils.writeFileFromString(file2, "hello 2", false);
        Button btnZipDir = new Button(this);
        btnZipDir.setText("压缩SD卡上的test文件夹到test2文件夹内");
        btnZipDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipFilesDir();
            }
        });
        Button btnUnZipDir = new Button(this);
        btnUnZipDir.setText("解压SD卡上的test2文件夹内的压缩文件");
        btnUnZipDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unZipFilesDir();
            }
        });

        contentView.addView(btnZip);
        contentView.addView(btnZip2);
        contentView.addView(btnUnZip);
        contentView.addView(btnZipDir);
        contentView.addView(btnUnZipDir);
        setContentView(contentView);
    }

    private void unZipFilesDir() {
        File zipFile = new File(SDCardUtils.getSDCardPath() + "test2" + File.separator + "testZip.zip");
        File destDir = new File(SDCardUtils.getSDCardPath() + "test2" + File.separator);
        boolean successs = false;
        try {
            successs = ZipUtils.unzipFile(zipFile, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToastUtils.showShort(getApplicationContext(), successs ? "解压完成" : "解压失败");
    }

    private void zipFilesDir() {
        File resFile = new File(SDCardUtils.getSDCardPath() + "test");
        File zipFileParent = new File(SDCardUtils.getSDCardPath() + "test2" + File.separator);
        if (!FileUtils.isDirExistsOrCreated(zipFileParent)) return;
        File zipFile = new File(zipFileParent + File.separator + "testZip.zip");
        boolean successs = false;
        try {
            successs = ZipUtils.zipFile(resFile, zipFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToastUtils.showShort(getApplicationContext(), successs ? "压缩完成" : "压缩失败");
    }


    public void zipFiles() {
        File file = new File(SDCardUtils.getSDCardPath() + "hello.txt");
        FileUtils.writeFileFromString(file, "zip test", true);

        File resFile = file;
        File zipFile = new File(SDCardUtils.getSDCardPath() + "hellZip");
        boolean successs = false;
        try {
            successs = ZipUtils.zipFile(resFile, zipFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToastUtils.showShort(getApplicationContext(), successs ? "压缩完成" : "压缩失败");
    }

    public void zipFiles2() {
        File file = new File(SDCardUtils.getSDCardPath() + "hello.txt");
        FileUtils.writeFileFromString(file, "zip test", true);

        File resFile = file;
        File zipFile = new File(SDCardUtils.getSDCardPath() + "hellZip");
        boolean successs = false;
        try {
            successs = ZipUtils.zipFile(resFile, zipFile, "where is comment?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToastUtils.showShort(getApplicationContext(), successs ? "压缩完成" : "压缩失败");
    }

    private void unZipFile() {
        File zipFile = new File(SDCardUtils.getSDCardPath() + "hellZip");
        File destDir = new File(SDCardUtils.getSDCardPath());

        boolean successs = false;
        try {
            successs = ZipUtils.unzipFile(zipFile, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToastUtils.showShort(getApplicationContext(), successs ? "解压完成" : "解压失败");
    }


}
