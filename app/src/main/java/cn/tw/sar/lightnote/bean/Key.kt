package cn.tw.sar.lightnote.bean

import com.google.gson.annotations.SerializedName

data class Key(
    @SerializedName("appid") val appid : String,
    @SerializedName("key") val key : String
)
