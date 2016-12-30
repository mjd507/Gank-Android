package common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static common.utils.ConstUtils.KB;

/**
 * 描述:
 * Created by mjd on 2016/12/29.
 */

public class ZipUtils {


    /**
     * 压缩文件
     *
     * @param resFile 待压缩文件
     * @param zipFile 压缩文件
     * @param comment 压缩文件的注释
     */
    public static boolean zipFile(File resFile, File zipFile, String comment)
            throws IOException {
        if (resFile == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            return zipFile(resFile, zos, comment);
        } finally {
            if (zos != null) {
                CloseUtils.closeIO(zos);
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param resFile 待压缩文件
     * @param zos     压缩文件输出流
     * @param comment 压缩文件的注释
     */
    private static boolean zipFile(File resFile, ZipOutputStream zos, String comment)
            throws IOException {
        String rootPath = resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            // 如果是空文件夹那么创建它，我把'/'换为File.separator测试就不成功，eggPain
            if (fileList == null || fileList.length <= 0) {
                ZipEntry entry = new ZipEntry(rootPath + '/');
                if (!StringUtils.isEmpty(comment)) entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    // 如果递归返回false则返回false
                    if (!zipFile(file, zos, comment)) return false;
                }
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(resFile));
                ZipEntry entry = new ZipEntry(rootPath);
                if (!StringUtils.isEmpty(comment)) entry.setComment(comment);
                zos.putNextEntry(entry);
                byte buffer[] = new byte[KB];
                int len;
                while ((len = is.read(buffer, 0, KB)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } finally {
                CloseUtils.closeIO(is);
            }
        }
        return true;
    }


    /**
     * 解压文件
     *
     * @param zipFile 待解压文件
     * @param destDir 目标目录
     */
    public static boolean unzipFile(File zipFile, File destDir) throws IOException {
        return unZipFile(zipFile, destDir) != null;
    }

    /**
     * 解压文件
     *
     * @param zipFile 待解压文件
     * @param destDir 目标目录
     */
    private static List<File> unZipFile(File zipFile, File destDir) throws IOException {
        if (zipFile == null || destDir == null) return null;
        List<File> files = new ArrayList<>();
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<?> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            String entryName = entry.getName();

            if (!StringUtils.isEmpty(entryName)) {
                int lastSep = entryName.lastIndexOf(File.separator);
                entryName = lastSep == -1 ? entryName : entryName.substring(lastSep + 1);
            }

            String filePath = destDir + File.separator + entryName;
            File file = new File(filePath);
            files.add(file);
            if (entry.isDirectory()) {
                boolean createOrExistsDir = file.exists() ? file.isDirectory() : file.mkdirs();
                if (!createOrExistsDir) return null;
            } else {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = new BufferedInputStream(zf.getInputStream(entry));
                    out = new BufferedOutputStream(new FileOutputStream(file));
                    byte buffer[] = new byte[KB];
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } finally {
                    CloseUtils.closeIO(in, out);
                }
            }
        }
        return files;
    }


}
