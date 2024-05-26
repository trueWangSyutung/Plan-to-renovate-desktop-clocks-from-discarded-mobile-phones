package cn.tw.sar.lightnote.util

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.ComponentName
import android.content.Context
import android.provider.MediaStore
import android.util.Log
data class ImageItem(
    val id: Long,
    val path: String
)
object WidgetUtils {

    fun getAppliconsWidgets(
        context: Context
    ): List<AppWidgetProviderInfo>{
        // 获取所有第三方应用的小部件
        var appWidgetManager = AppWidgetManager.getInstance(context)
        var appWidgetProviders = appWidgetManager.installedProviders

        // 获取所有第三方应用的小部件
        var appWidgetProvidersThird = appWidgetManager.installedProviders.filter {
            !it.provider.packageName.startsWith("com.android")
        }

        return appWidgetProvidersThird

    }
    fun readImagesFromGallery(
        context: Context
    ) : ArrayList<ImageItem>{
        var res = ArrayList<ImageItem>()
        // 读取相册中的图片
        var projection = arrayOf(
            MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA
        );
        var  sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        var uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        var name = "Camera";
        // 查询条件, 只查询相册名为 Camera 的图片
        var selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";

        var cursor = context.contentResolver.query(uri, projection, selection, arrayOf(name), sortOrder);
        Log.d("WidgetUtils", "readImagesFromGallery: ${cursor?.count}")
        var num = 0

        if (cursor != null) {
            while (cursor.moveToNext()) {
                var id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                var path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                // 处理相册中的图片
                if (num >= 5){
                    return res
                }
                res.add(ImageItem(id, path))
                num++
            }
            cursor.close();
        }
        return res
    }


}