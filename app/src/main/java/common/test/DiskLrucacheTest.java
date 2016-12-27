package common.test;

import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import common.bitmap.ImageResizer;
import common.bitmap.cache.DiskLruCache;
import common.utils.CloseUtils;
import common.utils.MD5Utils;

/**
 * 描述:
 * Created by mjd on 2016/12/20.
 */

public class DiskLrucacheTest {
    private DiskLruCache mDiskLrucache;
    String url = "picurl";


    public void test() throws IOException {
        String key = MD5Utils.getMd5Str(url.getBytes());
        DiskLruCache.Editor editor = mDiskLrucache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(0);
            if (downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLrucache.flush();
        }
    }

    private boolean downloadUrlToStream(String url, OutputStream outputStream) {
        URL urlStr = null;
        HttpURLConnection conn = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            urlStr = new URL(url);
            conn = (HttpURLConnection) urlStr.openConnection();
            out = new BufferedOutputStream(outputStream);
            in = new BufferedInputStream(conn.getInputStream());
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            CloseUtils.closeIO(in, out);
        }
        return false;
    }

    private Bitmap get(String url,int reqWidth,int reqHeight) throws IOException {
        Bitmap bitmap = null;
        String key = MD5Utils.getMd5Str(url.getBytes());
        DiskLruCache.Snapshot snapshot = mDiskLrucache.get(key);
        if(snapshot!=null){
            FileInputStream fis = (FileInputStream) snapshot.getInputStream(0);
            FileDescriptor fileDescriptor = fis.getFD();
            bitmap = ImageResizer.decodeSampledBitmapFromDescriptor(fileDescriptor, reqWidth, reqHeight);
            //add to memory
        }
        return bitmap;
    }


}
