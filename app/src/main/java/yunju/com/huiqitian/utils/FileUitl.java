package yunju.com.huiqitian.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import cn.trinea.android.common.util.LogUtils;

/**
 * @author 张超群
 * @since 2016/8/9
 *
 * 文件帮助类
 */
public class FileUitl {


    /**
     * 根据URI 获取文件的路径
     *
     * @param context 上下文
     * @param uri uri地址
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor =
                    context.getContentResolver().query(uri,
                            new String[] {ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 图片写入文件
     *
     * @param bitmap 图片
     * @param filePath 文件路径
     * @return 是否写入成功
     */
    public static boolean bitmapToFile(Bitmap bitmap, String filePath) {
        boolean isSuccess = false;
        if (bitmap == null) {
            return isSuccess;
        }
        File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
        if (!file.exists()) {
            file.mkdirs();
        }

        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(filePath), 8 * 1024);
            isSuccess = bitmap.compress(CompressFormat.PNG, 300, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeIO(out);
        }
        return isSuccess;
    }

    /**
     * 关闭流
     *
     * @param closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                throw new RuntimeException(FileUitl.class.getClass().getName(), e);
            }
        }
    }

    /**
     * 检测SD卡是否存在
     */
    public static boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取sdk文件路径
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        if (checkSDcard()) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        } else {
            LogUtils.error(FileUitl.class, "==图片地址==SDK不存在");
        }
        return sdDir.toString();
    }

    /**
     * 获取app缓存路径
     *
     * @param context
     * @return
     */
    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.getExternalStorageState() != null) {//判断不能获取null
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                cachePath = context.getExternalCacheDir().getPath(); // 获取缓存路径
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            return getSDPath();
        }
        return cachePath;
    }

    /**
     * 读取 assets 下的 文本内容
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getGetAssets(Context context, String uri) {
        String successdata = "";
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(uri);
            inputStream.read();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            successdata = new String(br.readLine()).toString();
            return successdata;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return successdata;
    }

    /**
     * 拷贝 assets 到指定文件夹
     *
     * @param context
     * @param fileName 文件名
     * @param newPath 新的文件路径
     */
    public static void copyFilesFassets(Context context, String fileName, String newPath) {
        try {
            InputStream inStream = context.getAssets().open(fileName);// 读取文件，获取输入流
            if (inStream == null) {
                return;
            }
            FileOutputStream fos = new FileOutputStream(newPath); // 写入文件，获取输出流
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = inStream.read(buffer)) > 0) {
                fos.write(buffer, 0, count);// 写文件
            }
            fos.close();// 关闭
            inStream.close();// 关闭
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
