package cn.tw.sar.lightnote.util

object IconsConvert {
    /**
     *
     * 图标代码	天气	白天	夜晚
     * 100	晴	✅	❌
     * 101	多云	✅	❌
     * 102	少云	✅	❌
     * 103	晴间多云	✅	❌
     * 104	阴	✅	✅
     * 150	晴	❌	✅
     * 151	多云	❌	✅
     * 152	少云	❌	✅
     * 153	晴间多云	❌	✅
     * 300	阵雨	✅	❌
     * 301	强阵雨	✅	❌
     * 302	雷阵雨	✅	✅
     * 303	强雷阵雨	✅	✅
     * 304	雷阵雨伴有冰雹	✅	✅
     * 305	小雨	✅	✅
     * 306	中雨	✅	✅
     * 307	大雨	✅	✅
     * 308	极端降雨	✅	✅
     * 309	毛毛雨/细雨	✅	✅
     * 310	暴雨	✅	✅
     * 311	大暴雨	✅	✅
     * 312	特大暴雨	✅	✅
     * 313	冻雨	✅	✅
     * 314	小到中雨	✅	✅
     * 315	中到大雨	✅	✅
     * 316	大到暴雨	✅	✅
     * 317	暴雨到大暴雨	✅	✅
     * 318	大暴雨到特大暴雨	✅	✅
     * 350	阵雨	❌	✅
     * 351	强阵雨	❌	✅
     * 399	雨	✅	✅
     * 400	小雪	✅	✅
     * 401	中雪	✅	✅
     * 402	大雪	✅	✅
     * 403	暴雪	✅	✅
     * 404	雨夹雪	✅	✅
     * 405	雨雪天气	✅	✅
     * 406	阵雨夹雪	✅	❌
     * 407	阵雪	✅	❌
     * 408	小到中雪	✅	✅
     * 409	中到大雪	✅	✅
     * 410	大到暴雪	✅	✅
     * 456	阵雨夹雪	❌	✅
     * 457	阵雪	❌	✅
     * 499	雪	✅	✅
     * 500	薄雾	✅	✅
     * 501	雾	✅	✅
     * 502	霾	✅	✅
     * 503	扬沙	✅	✅
     * 504	浮尘	✅	✅
     * 507	沙尘暴	✅	✅
     * 508	强沙尘暴	✅	✅
     * 509	浓雾	✅	✅
     * 510	强浓雾	✅	✅
     * 511	中度霾	✅	✅
     * 512	重度霾	✅	✅
     * 513	严重霾	✅	✅
     * 514	大雾	✅	✅
     * 515	特强浓雾	✅	✅
     * 900	热	✅	✅
     * 901	冷	✅	✅
     * 999	未知	✅	✅
     */
    fun convertNumberToResourceID(
        icon: Int,
    ) : Int{
        return when(icon) {
            100 -> cn.tw.sar.lightnote.R.drawable._100
            101 -> cn.tw.sar.lightnote.R.drawable._101
            102 -> cn.tw.sar.lightnote.R.drawable._102
            103 -> cn.tw.sar.lightnote.R.drawable._103
            104 -> cn.tw.sar.lightnote.R.drawable._104
            150 -> cn.tw.sar.lightnote.R.drawable._150
            151 -> cn.tw.sar.lightnote.R.drawable._151
            152 -> cn.tw.sar.lightnote.R.drawable._152
            153 -> cn.tw.sar.lightnote.R.drawable._153
            300 -> cn.tw.sar.lightnote.R.drawable._300
            301 -> cn.tw.sar.lightnote.R.drawable._301
            302 -> cn.tw.sar.lightnote.R.drawable._302
            303 -> cn.tw.sar.lightnote.R.drawable._303
            304 -> cn.tw.sar.lightnote.R.drawable._304
            305 -> cn.tw.sar.lightnote.R.drawable._305
            306 -> cn.tw.sar.lightnote.R.drawable._306
            307 -> cn.tw.sar.lightnote.R.drawable._307
            308 -> cn.tw.sar.lightnote.R.drawable._308
            309 -> cn.tw.sar.lightnote.R.drawable._309
            310 -> cn.tw.sar.lightnote.R.drawable._310
            311 -> cn.tw.sar.lightnote.R.drawable._311
            312 -> cn.tw.sar.lightnote.R.drawable._312
            313 -> cn.tw.sar.lightnote.R.drawable._313
            314 -> cn.tw.sar.lightnote.R.drawable._314
            315 -> cn.tw.sar.lightnote.R.drawable._315
            316 -> cn.tw.sar.lightnote.R.drawable._316
            317 -> cn.tw.sar.lightnote.R.drawable._317
            318 -> cn.tw.sar.lightnote.R.drawable._318
            350 -> cn.tw.sar.lightnote.R.drawable._350
            351 -> cn.tw.sar.lightnote.R.drawable._351
            399 -> cn.tw.sar.lightnote.R.drawable._399
            400 -> cn.tw.sar.lightnote.R.drawable._400
            401 -> cn.tw.sar.lightnote.R.drawable._401
            402 -> cn.tw.sar.lightnote.R.drawable._402
            403 -> cn.tw.sar.lightnote.R.drawable._403
            404 -> cn.tw.sar.lightnote.R.drawable._404
            405 -> cn.tw.sar.lightnote.R.drawable._405
            406 -> cn.tw.sar.lightnote.R.drawable._406
            407 -> cn.tw.sar.lightnote.R.drawable._407
            408 -> cn.tw.sar.lightnote.R.drawable._408
            409 -> cn.tw.sar.lightnote.R.drawable._409
            410 -> cn.tw.sar.lightnote.R.drawable._410
            456 -> cn.tw.sar.lightnote.R.drawable._456
            457 -> cn.tw.sar.lightnote.R.drawable._457
            499 -> cn.tw.sar.lightnote.R.drawable._499
            500 -> cn.tw.sar.lightnote.R.drawable._500
            501 -> cn.tw.sar.lightnote.R.drawable._501
            502 -> cn.tw.sar.lightnote.R.drawable._502
            503 -> cn.tw.sar.lightnote.R.drawable._503
            504 -> cn.tw.sar.lightnote.R.drawable._504
            507 -> cn.tw.sar.lightnote.R.drawable._507
            508 -> cn.tw.sar.lightnote.R.drawable._508
            509 -> cn.tw.sar.lightnote.R.drawable._509
            510 -> cn.tw.sar.lightnote.R.drawable._510
            511 -> cn.tw.sar.lightnote.R.drawable._511
            512 -> cn.tw.sar.lightnote.R.drawable._512
            513 -> cn.tw.sar.lightnote.R.drawable._513
            514 -> cn.tw.sar.lightnote.R.drawable._514
            515 -> cn.tw.sar.lightnote.R.drawable._515
            900 -> cn.tw.sar.lightnote.R.drawable._900
            901 -> cn.tw.sar.lightnote.R.drawable._901
            999 -> cn.tw.sar.lightnote.R.drawable._999
            else -> cn.tw.sar.lightnote.R.drawable._999
        }
    }
}