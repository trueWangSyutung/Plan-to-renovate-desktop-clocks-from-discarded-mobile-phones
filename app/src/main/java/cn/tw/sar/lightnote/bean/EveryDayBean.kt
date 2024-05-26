package cn.tw.sar.lightnote.bean

import com.google.gson.annotations.SerializedName

data class EveryDayBean(
    @SerializedName("data"       ) var data      : EveryDayData? = EveryDayData(),
    @SerializedName("status"     ) var status    : Int?  = null,
    @SerializedName("error_code" ) var errorCode : Int?  = null
)

data class EveryDayData (

    @SerializedName("mid"                  ) var mid                : String?             = null,
    @SerializedName("client_playlist_flag" ) var clientPlaylistFlag : Int?                = null,
    @SerializedName("OlexpIds"             ) var OlexpIds           : String?             = null,
    @SerializedName("song_list"            ) var songList           : ArrayList<SongList> = arrayListOf(),
    @SerializedName("sign"                 ) var sign               : String?             = null,
    @SerializedName("song_list_size"       ) var songListSize       : Int?                = null

)
class RelateGoods (


)
data class SongList (

    @SerializedName("tracker_info"       ) var trackerInfo      : TrackerInfo?          = TrackerInfo(),
    @SerializedName("mv_hash"            ) var mvHash           : String?               = null,
    @SerializedName("hash"               ) var hash             : String?               = null,
    @SerializedName("author_name"        ) var authorName       : String?               = null,
    @SerializedName("tags"               ) var tags             : ArrayList<Tags>       = arrayListOf(),
    @SerializedName("official_songname"  ) var officialSongname : String?               = null,
    @SerializedName("alg_path"           ) var algPath          : String?               = null,
    @SerializedName("bitrate"            ) var bitrate          : Int?                  = null,
    @SerializedName("is_mv_file_head"    ) var isMvFileHead     : Int?                  = null,
    @SerializedName("hash_192"           ) var hash192          : String?               = null,
    @SerializedName("has_accompany"      ) var hasAccompany     : Int?                  = null,
    @SerializedName("ztc_mark"           ) var ztcMark          : String?               = null,
    @SerializedName("filesize_320"       ) var filesize320      : Int?                  = null,
    @SerializedName("album_name"         ) var albumName        : String?               = null,
    @SerializedName("filesize_128"       ) var filesize128      : Int?                  = null,
    @SerializedName("songname"           ) var songname         : String?               = null,
    @SerializedName("music_trac"         ) var musicTrac        : Int?                  = null,
    @SerializedName("is_publish"         ) var isPublish        : Int?                  = null,
    @SerializedName("climax_end_time"    ) var climaxEndTime    : Int?                  = null,
    @SerializedName("hash_ape"           ) var hashApe          : String?               = null,
    @SerializedName("songid"             ) var songid           : Int?                  = null,
    @SerializedName("quality_level"      ) var qualityLevel     : Int?                  = null,
    @SerializedName("fail_process"       ) var failProcess      : Int?                  = null,
    @SerializedName("pay_type"           ) var payType          : Int?                  = null,
    @SerializedName("album_audio_id"     ) var albumAudioId     : String?               = null,
    @SerializedName("song_type"          ) var songType         : String?               = null,
    @SerializedName("file_size"          ) var fileSize         : Int?                  = null,
    @SerializedName("has_album"          ) var hasAlbum         : Int?                  = null,
    @SerializedName("climax_timelength"  ) var climaxTimelength : Int?                  = null,
    @SerializedName("extname"            ) var extname          : String?               = null,
    @SerializedName("climax_start_time"  ) var climaxStartTime  : Int?                  = null,
    @SerializedName("type"               ) var type             : String?               = null,
    @SerializedName("sizable_cover"      ) var sizableCover     : String?               = null,
    @SerializedName("remark"             ) var remark           : String?               = null,
    @SerializedName("album_audio_remark" ) var albumAudioRemark : String?               = null,
    @SerializedName("level"              ) var level            : Int?                  = null,
    @SerializedName("time_length"        ) var timeLength       : Int?                  = null,
    @SerializedName("publish_date"       ) var publishDate      : String?               = null,
    @SerializedName("old_cpy"            ) var oldCpy           : Int?                  = null,
    @SerializedName("hash_flac"          ) var hashFlac         : String?               = null,
    @SerializedName("language"           ) var language         : String?               = null,
    @SerializedName("is_file_head_320"   ) var isFileHead320    : Int?                  = null,
    @SerializedName("relate_goods"       ) var relateGoods      : RelateGoods?          = RelateGoods(),
    @SerializedName("hash_other"         ) var hashOther        : String?               = null,
    @SerializedName("album_id"           ) var albumId          : String?               = null,
    @SerializedName("ori_audio_name"     ) var oriAudioName     : String?               = null,
    @SerializedName("mv_type"            ) var mvType           : Int?                  = null,
    @SerializedName("publish_time"       ) var publishTime      : Int?                  = null,
    @SerializedName("filename"           ) var filename         : String?               = null,
    @SerializedName("filesize_other"     ) var filesizeOther    : Int?                  = null,
    @SerializedName("singerinfo"         ) var singerinfo       : ArrayList<Singerinfo> = arrayListOf(),
    @SerializedName("timelength_320"     ) var timelength320    : Int?                  = null,
    @SerializedName("trans_param"        ) var transParam       : TransParam?           = TransParam(),
    @SerializedName("is_file_head"       ) var isFileHead       : Int?                  = null,
    @SerializedName("hash_128"           ) var hash128          : String?               = null,
    @SerializedName("filesize_ape"       ) var filesizeApe      : Int?                  = null,
    @SerializedName("cid"                ) var cid              : Int?                  = null,
    @SerializedName("scid"               ) var scid             : Int?                  = null,
    @SerializedName("privilege"          ) var privilege        : Int?                  = null,
    @SerializedName("filesize_192"       ) var filesize192      : Int?                  = null,
    @SerializedName("mixsongid"          ) var mixsongid        : String?               = null,
    @SerializedName("hash_320"           ) var hash320          : String?               = null,
    @SerializedName("suffix_audio_name"  ) var suffixAudioName  : String?               = null,
    @SerializedName("filesize_flac"      ) var filesizeFlac     : Int?                  = null

)

data class TransParam (

    @SerializedName("ogg_128_hash"      ) var ogg128Hash       : String?     = null,
    @SerializedName("classmap"          ) var classmap         : Classmap?   = Classmap(),
    @SerializedName("language"          ) var language         : String?     = null,
    @SerializedName("cpy_attr0"         ) var cpyAttr0         : Int?        = null,
    @SerializedName("musicpack_advance" ) var musicpackAdvance : Int?        = null,
    @SerializedName("ogg_128_filesize"  ) var ogg128Filesize   : Int?        = null,
    @SerializedName("display_rate"      ) var displayRate      : Int?        = null,
    @SerializedName("qualitymap"        ) var qualitymap       : Qualitymap? = Qualitymap(),
    @SerializedName("ogg_320_filesize"  ) var ogg320Filesize   : Int?        = null,
    @SerializedName("cpy_grade"         ) var cpyGrade         : Int?        = null,
    @SerializedName("cid"               ) var cid              : Int?        = null,
    @SerializedName("display"           ) var display          : Int?        = null,
    @SerializedName("ogg_320_hash"      ) var ogg320Hash       : String?     = null,
    @SerializedName("ipmap"             ) var ipmap            : Ipmap?      = Ipmap(),
    @SerializedName("appid_block"       ) var appidBlock       : String?     = null,
    @SerializedName("pay_block_tpl"     ) var payBlockTpl      : Int?        = null,
    @SerializedName("hash_multitrack"   ) var hashMultitrack   : String?     = null,
    @SerializedName("cpy_level"         ) var cpyLevel         : Int?        = null

)


data class Ipmap (

    @SerializedName("attr0" ) var attr0 : Int? = null

)

data class Qualitymap (

    @SerializedName("attr0" ) var attr0 : Int? = null

)

data class Classmap (

    @SerializedName("attr0" ) var attr0 : Int? = null

)

data class Singerinfo (

    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("is_publish" ) var isPublish : String? = null,
    @SerializedName("id"         ) var id        : String? = null

)

data class Tags (

    @SerializedName("tag_id"    ) var tagId    : String? = null,
    @SerializedName("parent_id" ) var parentId : String? = null,
    @SerializedName("tag_name"  ) var tagName  : String? = null

)


data class TrackerInfo (

    @SerializedName("auth"      ) var auth     : String? = null,
    @SerializedName("module_id" ) var moduleId : Int?    = null,
    @SerializedName("open_time" ) var openTime : String? = null

)

