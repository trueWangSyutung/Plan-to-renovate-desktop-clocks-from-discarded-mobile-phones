package cn.tw.sar.lightnote.dao

import com.google.gson.annotations.SerializedName

data class HitokotoBean(
    @SerializedName("id") val id : Int,
    @SerializedName("uuid") val uuid : String,
    @SerializedName("hitokoto") val hitokoto : String,
    @SerializedName("type") val type : String,
    @SerializedName("from") val from : String,
    @SerializedName("from_who") val from_who : String,
    @SerializedName("creator") val creator : String,
    @SerializedName("creator_uid") val creator_uid : Int,
    @SerializedName("reviewer") val reviewer : Int,
    @SerializedName("commit_from") val commit_from : String,
    @SerializedName("created_at") val created_at : Int,
    @SerializedName("length") val length : Int
)