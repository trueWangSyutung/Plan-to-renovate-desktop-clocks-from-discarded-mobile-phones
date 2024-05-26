package cn.tw.sar.lightnote.bean

import com.google.gson.annotations.SerializedName

data class LoginBean(
    @SerializedName("data") var data: UserData? = null,
    @SerializedName("status"     ) var status    : Int?  = null,
    @SerializedName("error_code" ) var errorCode : Int?  = null
)

data class UserData(
    @SerializedName("is_vip"                ) var isVip               : Int?      = null,
    @SerializedName("servertime"            ) var servertime          : String?   = null,
    @SerializedName("roam_type"             ) var roamType            : Int?      = null,
    @SerializedName("t1"                    ) var t1                  : String?   = null,
    @SerializedName("reg_time"              ) var regTime             : String?   = null,
    @SerializedName("vip_type"              ) var vipType             : Int?      = null,
    @SerializedName("vip_begin_time"        ) var vipBeginTime        : String?   = null,
    @SerializedName("userid"                ) var userid              : Int?      = null,
    @SerializedName("su_vip_end_time"       ) var suVipEndTime        : String?   = null,
    @SerializedName("sex"                   ) var sex                 : Int?      = null,
    @SerializedName("user_type"             ) var userType            : Int?      = null,
    @SerializedName("username"              ) var username            : String?   = null,
    @SerializedName("qq"                    ) var qq                  : Int?      = null,
    @SerializedName("exp"                   ) var exp                 : Int?      = null,
    @SerializedName("m_end_time"            ) var mEndTime            : String?   = null,
    @SerializedName("score"                 ) var score               : Int?      = null,
    @SerializedName("m_is_old"              ) var mIsOld              : Int?      = null,
    @SerializedName("birthday"              ) var birthday            : String?   = null,
    @SerializedName("arttoy_avatar"         ) var arttoyAvatar        : String?   = null,
    @SerializedName("totp_server_timestamp" ) var totpServerTimestamp : Int?      = null,
    @SerializedName("roam_end_time"         ) var roamEndTime         : String?   = null,
    @SerializedName("su_vip_begin_time"     ) var suVipBeginTime      : String?   = null,
    @SerializedName("roam_begin_time"       ) var roamBeginTime       : String?   = null,
    @SerializedName("vip_end_time"          ) var vipEndTime          : String?   = null,
    @SerializedName("secu_params"           ) var secuParams          : String?   = null,
    @SerializedName("nickname"              ) var nickname            : String?   = null,
    @SerializedName("mobile"                ) var mobile              : Int?      = null,
    @SerializedName("vip_token"             ) var vipToken            : String?   = null,
    @SerializedName("bc_code"               ) var bcCode              : String?   = null,
    @SerializedName("m_type"                ) var mType               : Int?      = null,
    @SerializedName("roam_list"             ) var roamList            : RoamList? = RoamList(),
    @SerializedName("m_begin_time"          ) var mBeginTime          : String?   = null,
    @SerializedName("pic"                   ) var pic                 : String?   = null,
    @SerializedName("su_vip_clearday"       ) var suVipClearday       : String?   = null,
    @SerializedName("user_y_type"           ) var userYType           : Int?      = null,
    @SerializedName("su_vip_y_endtime"      ) var suVipYEndtime       : String?   = null,
    @SerializedName("birthday_mmdd"         ) var birthdayMmdd        : String?   = null,
    @SerializedName("y_type"                ) var yType               : Int?      = null,
    @SerializedName("wechat"                ) var wechat              : Int?      = null,
    @SerializedName("token"                 ) var token               : String?   = null
)

class RoamList(
)