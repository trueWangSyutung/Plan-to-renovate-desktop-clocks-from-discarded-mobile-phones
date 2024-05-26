package cn.tw.sar.lightnote.bean

import com.google.gson.annotations.SerializedName

data class MusicBean(
    @SerializedName("hash"          ) var hash        : String?           = null,
    @SerializedName("classmap"      ) var classmap    : Classmap2?         = Classmap2(),
    @SerializedName("status"        ) var status      : Int?              = null,
    @SerializedName("volume"        ) var volume      : Double?           = null,
    @SerializedName("std_hash_time" ) var stdHashTime : Int?              = null,
    @SerializedName("backupUrl"     ) var backupUrl   : ArrayList<String> = arrayListOf(),
    @SerializedName("url"           ) var url         : ArrayList<String> = arrayListOf(),
    @SerializedName("std_hash"      ) var stdHash     : String?           = null,
    @SerializedName("trans_param"   ) var transParam  : TransParam2?       = TransParam2(),
    @SerializedName("fileHead"      ) var fileHead    : Int?              = null,
    @SerializedName("fileSize"      ) var fileSize    : Int?              = null,
    @SerializedName("bitRate"       ) var bitRate     : Int?              = null,
    @SerializedName("volume_peak"   ) var volumePeak  : Double?           = null,
    @SerializedName("volume_gain"   ) var volumeGain  : Int?              = null,
    @SerializedName("timeLength"    ) var timeLength  : Int?              = null,
    @SerializedName("fileName"      ) var fileName    : String?           = null,
    @SerializedName("q"             ) var q           : Int?              = null,
    @SerializedName("extName"       ) var extName     : String?           = null
)


data class Classmap2 (

    @SerializedName("attr0" ) var attr0 : Int? = null

)


data class TransParam2 (

    @SerializedName("display"      ) var display     : Int? = null,
    @SerializedName("display_rate" ) var displayRate : Int? = null

)