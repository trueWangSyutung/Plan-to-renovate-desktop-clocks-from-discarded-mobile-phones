package cn.tw.sar.lightnote.util;

public class LogUtils {
    public static void d(String tag, String msg) {
        android.util.Log.d(tag, msg);
    }

    public static void e(String msg, String s) {
        android.util.Log.e(msg, s);

    }

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        android.util.Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
    }
}
