package cn.tw.sar.easylauncher.utils

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.IslamicCalendar
import android.util.Log
import cn.tw.sar.lightnote.util.LunarCalender
import net.time4j.calendar.HebrewCalendar
import net.time4j.calendar.HijriCalendar
import net.time4j.calendar.PersianCalendar
import net.time4j.engine.StartOfDay
import java.time.LocalDate
import java.time.temporal.IsoFields


object LocalCalenderUtils {
    // 阿拉伯历法国家
    private val ARABIC_COUNTRIES = arrayOf(
        "DZ", "BH", "TD", "KM", "DJ", "EG", "ER", "IQ", "JO", "KW", "LB", "LY", "MR", "MA", "OM", "PS", "QA", "SA", "SO", "SD", "SY", "TN", "AE", "YE"
    )
    // 伊斯兰历法国家
    private val ISLAMIC_COUNTRIES = arrayOf(
        "AL", "AZ", "BD", "BF", "BI", "BJ", "CI", "CM", "DJ", "ER", "ET", "GA", "GM", "GN", "ID",  "JO", "KE", "KM", "KW", "LB", "ML", "MR", "MY", "NE", "NG", "OM", "PK", "PS", "QA", "SA", "SN", "SO", "SD", "SY", "TG", "TH", "TN", "TR", "TM", "UZ", "AE", "YE"
    )
    // 中国农历
    private val CHINESE_COUNTRIES = arrayOf(
        "CN", "HK", "MO"
    )
    // 波斯历法国家
    private val PERSIAN_COUNTRIES = arrayOf(
        "AF", "IR"
    )
    // 主体纪年法，朝鲜
    private val DANGUN_COUNTRIES = arrayOf(
        "KP"
    )
    // 民国纪年法，台湾地区
    private val MINGUO_COUNTRIES = arrayOf(
        "TW"
    )
    // 年号纪年法，日本
    private val JAPANESE_COUNTRIES = arrayOf(
        "JP"
    )
    // 希伯来历法
    private val HEBREW_COUNTRIES = arrayOf(
        "IL"
    )

    fun isChineseNation(context: Context):Boolean{
        val locale = context.resources.configuration.locale
        val country = locale.country
        if (CHINESE_COUNTRIES.contains(country) or MINGUO_COUNTRIES.contains(country)){
            Log.d("LocalCalenderUtils", "isChineseNation: true")
            return true
        }else{
            Log.d("LocalCalenderUtils", "isChineseNation: false")
            return false
        }
    }
    @SuppressLint("NewApi")
    fun getLocalCalender(
        context: Context,
        year: Int,
        month: Int,
        day: Int
    ): String {
        // 获取系统语言地区
        val locale = context.resources.configuration.locale
        // 获取系统语言地区
        val language = locale.language
        // 获取系统语言地区
        val country = locale.country
        val lunarCalender = LunarCalender()
        var lunarCyclical = lunarCalender.cyclical(year, month, day)
        var launrAnimal = lunarCalender.animalsYear(year)

        var lunarString = lunarCalender.getLunarString(year, month, day);
        var fartival = lunarCalender.getFestival(year, month, day)

        return "农历${lunarCyclical}${launrAnimal} "

    }
}