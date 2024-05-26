package cn.tw.sar.lightnote.bean

import com.google.gson.annotations.SerializedName

data class LyricBean(

    @SerializedName("status"             ) var status           : Int?                  = null,
    @SerializedName("info"               ) var info             : String?               = null,
    @SerializedName("errcode"            ) var errcode          : Int?                  = null,
    @SerializedName("errmsg"             ) var errmsg           : String?               = null,
    @SerializedName("keyword"            ) var keyword          : String?               = null,
    @SerializedName("proposal"           ) var proposal         : String?               = null,
    @SerializedName("has_complete_right" ) var hasCompleteRight : Int?                  = null,
    @SerializedName("companys"           ) var companys         : String?               = null,
    @SerializedName("ugc"                ) var ugc              : Int?                  = null,
    @SerializedName("ugccount"           ) var ugccount         : Int?                  = null,
    @SerializedName("expire"             ) var expire           : Int?                  = null,
    @SerializedName("candidates"         ) var candidates       : ArrayList<Candidates> = arrayListOf(),
    @SerializedName("ugccandidates"      ) var ugccandidates    : ArrayList<String>     = arrayListOf(),
    //@SerializedName("artists"            ) var artists          : ArrayList<Artists>    = arrayListOf(),
    @SerializedName("ai_candidates"      ) var aiCandidates     : ArrayList<String>     = arrayListOf()

)


data class Candidates(
    @SerializedName("id"             ) var id            : String?           = null,
    @SerializedName("product_from"   ) var productFrom   : String?           = null,
    @SerializedName("accesskey"      ) var accesskey     : String?           = null,
    @SerializedName("can_score"      ) var canScore      : Boolean?          = null,
    @SerializedName("singer"         ) var singer        : String?           = null,
    @SerializedName("song"           ) var song          : String?           = null,
    @SerializedName("duration"       ) var duration      : Int?              = null,
    @SerializedName("uid"            ) var uid           : String?           = null,
    @SerializedName("nickname"       ) var nickname      : String?           = null,
    @SerializedName("origiuid"       ) var origiuid      : String?           = null,
    @SerializedName("transuid"       ) var transuid      : String?           = null,
    @SerializedName("sounduid"       ) var sounduid      : String?           = null,
    @SerializedName("originame"      ) var originame     : String?           = null,
    @SerializedName("transname"      ) var transname     : String?           = null,
    @SerializedName("soundname"      ) var soundname     : String?           = null,
    //@SerializedName("parinfo"        ) var parinfo       : ArrayList<String>? = arrayListOf(),
    //@SerializedName("parinfoExt"     ) var parinfoExt    : ArrayList<String>? = arrayListOf(),
    @SerializedName("language"       ) var language      : String?           = null,
    @SerializedName("krctype"        ) var krctype       : Int?              = null,
    @SerializedName("hitlayer"       ) var hitlayer      : Int?              = null,
    @SerializedName("hitcasemask"    ) var hitcasemask   : Int?              = null,
    @SerializedName("adjust"         ) var adjust        : Int?              = null,
    @SerializedName("score"          ) var score         : Int?              = null,
    @SerializedName("contenttype"    ) var contenttype   : Int?              = null,
    @SerializedName("content_format" ) var contentFormat : Int?              = null
)


data class Base (

    @SerializedName("author_id"   ) var authorId   : Int?    = null,
    @SerializedName("author_name" ) var authorName : String? = null,
    @SerializedName("is_publish"  ) var isPublish  : Int?    = null,
    @SerializedName("avatar"      ) var avatar     : String? = null,
    @SerializedName("identity"    ) var identity   : Int?    = null,
    @SerializedName("type"        ) var type       : Int?    = null,
    @SerializedName("country"     ) var country    : String? = null,
    @SerializedName("birthday"    ) var birthday   : String? = null,
    @SerializedName("language"    ) var language   : String? = null

)

data class Artists (

    @SerializedName("identity" ) var identity : Int?  = null,
    @SerializedName("base"     ) var base     : Base? = Base()

)