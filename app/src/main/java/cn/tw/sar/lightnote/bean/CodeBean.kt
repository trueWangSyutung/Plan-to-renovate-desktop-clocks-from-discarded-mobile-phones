package cn.tw.sar.lightnote.bean

import com.google.gson.annotations.SerializedName


data class CodeBean (

    @SerializedName("data"       ) var data      : CodeData? = CodeData(),
    @SerializedName("status"     ) var status    : Int?  = null,
    @SerializedName("error_code" ) var errorCode : Int?  = null

)

data class CodeData (
    @SerializedName("count" ) var count : Int? = null
)