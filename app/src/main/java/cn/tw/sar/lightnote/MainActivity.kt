package cn.tw.sar.lightnote

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import cn.tw.sar.easylauncher.utils.LocalCalenderUtils
import cn.tw.sar.easylauncher.utils.getDarkModeBackgroundColor
import cn.tw.sar.easylauncher.utils.getDarkModeTextColor
import cn.tw.sar.lightnote.bean.SongList
import cn.tw.sar.lightnote.dao.HitokotoApi
import cn.tw.sar.lightnote.dao.MusicApi
import cn.tw.sar.lightnote.service.AudioServiceOnBind
import cn.tw.sar.lightnote.service.AudioServiceOnBind.AudioBinder
import cn.tw.sar.lightnote.service.LocationService
import cn.tw.sar.lightnote.ui.theme.LIteNoteTheme
import cn.tw.sar.lightnote.util.ImageItem
import cn.tw.sar.lightnote.util.LunarCalender
import cn.tw.sar.lightnote.util.WidgetUtils
import cn.tw.sar.lightnote.widgets.Subjection
import cn.tw.sar.lightnote.widgets.Weather
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Unit
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.HeConfig
import com.qweather.sdk.view.QWeather
import com.qweather.sdk.view.QWeather.OnResultWeatherNowListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import kotlin.concurrent.thread


class MainActivity : ComponentActivity(), LocationService.LocationCallBack  {
    private val REQUEST_READ_EXTERNAL_STORAGE = 1
    private val REQUEST_LOCALTION = 2
    fun requestsPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&  ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 申请权限
            var permissions = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            requestPermissions(permissions, REQUEST_LOCALTION)
            return
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                 arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_EXTERNAL_STORAGE);
        }

    }
    private var imgs = mutableStateListOf<ImageItem>()
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 已获取相册读取权限，可以进行后续操作

            } else {
                // 未获取相册读取权限，需处理相应逻辑
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if (requestCode == REQUEST_LOCALTION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 已获取定位权限，可以进行后续操作

            } else {
                // 未获取定位权限，需处理相应逻辑
            }
        } else {

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    private var weatherInfo = mutableStateOf<WeatherNowBean?>(
        null
    )
    private var currlocalName = mutableStateOf(
        ""
    )

    fun getWeather(latitude: Double = 0.0, longitude: Double = 0.0,localName : String = "") {
        // 获取GPS数据
        Log.d("MainActivity", "getWeather: ${locationInfo.toString()}")
        // 获取经纬度
        Log.d("MainActivity", "getWeather: $latitude $longitude")
        val TAG = "MainActivity"
        QWeather.getWeatherNow(
            this@MainActivity,
            "$longitude,$latitude",
            Lang.ZH_HANS,
            Unit.METRIC,
            object : OnResultWeatherNowListener {
                override fun onError(e: Throwable) {
                    Log.i(TAG, "getWeather onError: $e")
                }

                override fun onSuccess(weatherBean: WeatherNowBean) {
                    Log.i(TAG, "getWeather onSuccess: " + Gson().toJson(weatherBean))
                    weatherInfo.value = weatherBean
                    runOnUiThread {
                        currlocalName.value = localName
                    }

                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                    if (Code.OK == weatherBean.code) {
                        val now = weatherBean.now
                    } else {
                        //在此查看返回数据失败的原因
                        val code = weatherBean.code
                        Log.i(TAG, "failed code: $code")
                    }
                }
            })




    }
    private  var locationInfo: android.location.Location? = null

    private var hitokoto = mutableStateOf(
        "Loading..."
    )
    private var timeStr = mutableStateOf(
        "00:00"
    )
    private var rlStr = mutableStateOf(
        "2022/06/05, 星期日"
    )
    private var nlStr = mutableStateOf(
        "五月初五"
    )
    private var nlYear = mutableStateOf(
        "农历二零二二年五月初五"
    )

    fun updateTime() {
        var sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        var isShowSecond = sharedPreferences.getBoolean("5", true)

        // 获取当前时间
        val time = System.currentTimeMillis()
        // 获取当前日历
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        // 获取当前时间
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        var houtStr = "${hour}"
        val minute = calendar.get(Calendar.MINUTE)
        var minuteStr = "${minute}"
        if (isShowSecond) {
            val second = calendar.get(Calendar.SECOND)
            var secondStr = "${second}"
            if (hour < 10) {
                houtStr = "0${hour}"
            }
            if (minute < 10) {
                minuteStr = "0"+minute
            }
            if (second < 10) {
                secondStr = "0"+second
            }
            timeStr.value = "${houtStr}:${minuteStr}:${secondStr}"
        }else{
            // 将时间拼成2位数
            if (hour < 10) {
                houtStr = "0${hour}"
            }
            if (minute < 10) {
                minuteStr = "0"+minute
            }
            timeStr.value = "${houtStr}:${minuteStr}"

        }



    }

    fun updateDate() {
        val calendar = Calendar.getInstance()

        // 获取当前日期
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val week = calendar.get(Calendar.DAY_OF_WEEK)
        var weekStrs = arrayOf(
            "星期日",
            "星期一",
            "星期二",
            "星期三",
            "星期四",
            "星期五",
            "星期六"
        )

        rlStr.value = "${year}/${month}/" +
                "${day}, ${weekStrs[week-1]}"

        // 获取农历
        // val lunarUtils = cn.tw.sar.easylauncher.utils.LunarUtils(year, month, day)
        val lunarCalender = LunarCalender()
        var lunarCyclical = lunarCalender.cyclical(year, month, day)
        var launrAnimal = lunarCalender.animalsYear(year)

        var lunarString = lunarCalender.getLunarString(year, month, day);
        var fartival = lunarCalender.getFestival(year, month, day)



        val lunar = "${lunarString} ${fartival}"


        nlStr.value = lunar
        nlYear.value = LocalCalenderUtils.getLocalCalender(
            this,year, month, day
        )
    }
    @Composable
    fun Clock()
    {
        var sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        var isShowSecond = sharedPreferences.getBoolean("5", true)


        // 采用子线程更新时间
        var calendar = Calendar.getInstance()
        var minute = calendar.get(Calendar.MINUTE)

        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        updateTime()
        updateDate()
        var second = calendar.get(Calendar.SECOND)

        thread {
            // 每 1min 更新一次
            while (true) {
                if (isShowSecond) {
                    var newSecond = Calendar.getInstance().get(Calendar.SECOND)
                    if (newSecond != second) {
                        second = newSecond
                        runOnUiThread {
                            updateTime()
                        }
                    }
                }else{
                    var newminute = Calendar.getInstance().get(Calendar.MINUTE)
                    if (newminute != minute) {
                        minute = newminute
                        runOnUiThread {
                            updateTime()
                        }
                    }
                }


                var newYear = Calendar.getInstance().get(Calendar.YEAR)
                var newMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
                var newDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                if (newYear != year || newMonth != month || newDay != day) {
                    year = newYear
                    month = newMonth
                    day = newDay
                    runOnUiThread {
                        updateDate()
                        Log.d(">>>", "updateDate")
                    }
                }
                if (isShowSecond) {
                    Thread.sleep(1000)
                }else{
                    Thread.sleep(5000)
                }
            }
        }


    }

    fun UpdateWeather(){
        // 每 30min 更新一次
        var myService =  LocationService();
        // 设置回调接口
        myService.setCallback(this);
        // 启动 Service 并执行操作
        var serviceIntent = Intent(this@MainActivity, LocationService::class.java)
        startService(serviceIntent);

    }

    var musics = mutableStateListOf<SongList>()
    var music = mutableStateOf<SongList?>(null)
    private var audioServiceOnBind: AudioServiceOnBind? = null
    private val process = mutableStateOf(0)
    private val duraction = mutableStateOf(0)
    private val isPlaying = mutableStateOf(false)
    private val lyric = mutableStateOf("")
    private var currimg = mutableStateOf(0)

    @Composable
    fun ImageCLock(){
        thread {
            // 每10s切换一次页面
            while (true) {
                Thread.sleep(20000)

                currimg.value+=1
                if (currimg.value >= imgs.size) {
                    currimg.value = 0
                }

                Log.d("ImageCLock", "ImageCLock: ${currimg.value}")


            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestsPermission()
        // 获取定位
        HeConfig.init("HE2405252104481410", "34e6e73be7bb4bada3f860a6f17e88e8");
        HeConfig.switchToDevService();


        val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
        Log.d("MainActivity", "getWeather: ${locationManager.toString()}")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){
            setOnLocationListener { Location_latitude, Location_longitude, province, city, area, featureName ->
                Log.d(
                    "MainActivity",
                    "OnLocation: $Location_latitude $Location_longitude $province $city $area $featureName"
                )
                getWeather(Location_latitude.toDouble(),  Location_longitude.toDouble(),"$featureName")
            }
            UpdateWeather()


        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            imgs.addAll(WidgetUtils.readImagesFromGallery(this))
            Log.d("MainActivity", "onCreate: ${imgs.size}")
            for (img in imgs) {
                Log.d("MainActivity", "onCreate: ${img.path}")
            }
        }

        //使用ServiceConnection来监听Service状态的变化
        val conn: ServiceConnection = object : ServiceConnection {
            //当绑定成功时回调
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                //获取Binder对象
                val binder = service as AudioBinder
                //获取Service对象
                audioServiceOnBind = binder.getService()
                Log.d("onServiceConnected", "onServiceConnected ${audioServiceOnBind}")
            }

            //当绑定失败时回调
            override fun onServiceDisconnected(name: ComponentName?) {
                audioServiceOnBind = null
            }
        }

        locationInfo = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
        Log.d("MainActivity", "getWeather: ${locationInfo.toString()}")
        super.onCreate(savedInstanceState)
        var displayMetrics = resources.displayMetrics
        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        var widthDp = width / displayMetrics.density
        var heightDp = height / displayMetrics.density
        var retrofit =  Retrofit.Builder()
            //设置网络请求BaseUrl地址
            .baseUrl("https://v1.hitokoto.cn")
            //设置数据解析器
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val api: HitokotoApi = retrofit.create(HitokotoApi::class.java)
        thread {
            while (true) {
                var response = api.getHitokoto().execute()
                if (response.isSuccessful) {
                    var hitokotoBean = response.body()
                    hitokoto.value = "${hitokotoBean?.hitokoto}   -${hitokotoBean?.from}"  ?: "Loading..."
                }
                Thread.sleep(1000 * 60 * 5)
            }
        }
        setContent {
            LIteNoteTheme {
                WindowCompat.setDecorFitsSystemWindows(window, false)

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    // 因为我们目前没有暗色主题的UI，所以都是亮色主题UI，我们默认使用暗色的状态栏ICON
                    // ，防止出现标题栏字体颜色和UI背景融合的情况
                    systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
                }

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        getDarkModeBackgroundColor(
                            context = this@MainActivity, 0
                        )
                    ), color = MaterialTheme.colorScheme.background) {
                    //  width: Int = 400,
                    //    height: Int = 400,
                    //    backgroundColor: Color = Color.White,
                    //    subBackgroundColor: Color =  Color(0xFFF5EEE6),
                    //    fontColor : Color = Color.Black,
                    //    time: String = "18:56:52",
                    //    date: String = "2022-06-05",
                    //    lunar: String = "五月初五",
                    //    modifier: Modifier = Modifier,
                    //    textAlign : TextAlign = TextAlign.Start
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                getDarkModeBackgroundColor(
                                    context = this@MainActivity, 0
                                )
                            ),
                        topBar = {
                            // 顶部栏
                        },
                        bottomBar = {
                            // 底部栏
                        }
                    )  {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    getDarkModeBackgroundColor(
                                        context = this@MainActivity, 0
                                    )
                                )
                                .padding(10.dp),

                        ) {
                            var thread = Thread {
                                while (true) {
                                    var isEnd = audioServiceOnBind?.isPlayEnd()

                                    if (isEnd!!) {
                                        // 如果播放到 末尾，自动播放下一首
                                        audioServiceOnBind?.next()
                                        music.value = musics[audioServiceOnBind?.musicId ?: 0]

                                    }else{
                                        process.value = audioServiceOnBind?.getProgress()!!
                                        duraction.value = audioServiceOnBind?.getDuration()!!
                                        isPlaying.value = audioServiceOnBind?.isPlaying() == true
                                        lyric.value = audioServiceOnBind?.getLyric(
                                            process.value.toLong()
                                        ).toString()
                                    }


                                    Thread.sleep(1000)
                                }
                            }
                            Clock()
                            Subjection(
                                width = widthDp/2,
                                height = heightDp,
                                backgroundColor = getDarkModeBackgroundColor(
                                    context = this@MainActivity,0
                                ),
                                subBackgroundColor = getDarkModeBackgroundColor(
                                    context = this@MainActivity,1
                                ),
                                fontColor = getDarkModeTextColor(
                                    context = this@MainActivity
                                ),
                                time = timeStr.value,
                                date = rlStr.value,
                                lunar = nlYear.value + "\n" + nlStr.value,
                                context = this@MainActivity,
                                musics = musics,
                                music = music.value,
                                loadMusics = {
                                    val retrofit2 =  Retrofit.Builder()
                                        //设置网络请求BaseUrl地址
                                        .baseUrl("https://music.wxd2zrx.top")
                                        //设置数据解析器
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                    val api2: MusicApi = retrofit2.create(MusicApi::class.java)
                                    Log.d(">>>", "loadMusics")
                                    thread {
                                        val response = api2.recommend.execute().body()
                                        if (response != null) {
                                            if (response.status == 1) {
                                                runOnUiThread {
                                                    musics.clear()
                                                    musics.addAll(response.data?.songList!!)
                                                    music.value = response.data?.songList?.get(0)


                                                    val intentService =  Intent(this@MainActivity, AudioServiceOnBind::class.java)
                                                    intentService.putExtra("songs", Gson().toJson(musics))
                                                    intentService.putExtra("musicId", 0)

                                                    bindService(intentService, conn, Context.BIND_AUTO_CREATE);

                                                }

                                                while (audioServiceOnBind == null) {
                                                    Thread.sleep(1000)
                                                }
                                                runOnUiThread {
                                                    audioServiceOnBind?.setList(ArrayList(musics))

                                                    process.value = audioServiceOnBind?.getProgress()!!
                                                    duraction.value = audioServiceOnBind?.getDuration()!!
                                                    isPlaying.value = audioServiceOnBind?.isPlaying() == true
                                                }

                                            }
                                        }

                                    }
                                },
                                select = {
                                    music.value = it
                                    audioServiceOnBind?.musicId = musics.indexOf(it)

                                    thread.start()

                                },
                                process = process.value,
                                duraction = duraction.value,
                                isPlaying = isPlaying.value,
                                play = {
                                    audioServiceOnBind?.play()
                                    Log.d("play", "play ${audioServiceOnBind}")
                                    thread.start()
                                    isPlaying.value = audioServiceOnBind?.isPlaying() == true

                                },
                                pause = {
                                    Log.d("pause", "pause")
                                    audioServiceOnBind?.pause()
                                    thread.interrupt()
                                    isPlaying.value = audioServiceOnBind?.isPlaying() == true
                                },
                                next = {
                                    audioServiceOnBind?.pause()
                                    Log.d("next", "next")
                                    audioServiceOnBind?.next()
                                    music.value = musics[audioServiceOnBind?.musicId ?: 0]
                                    thread.start()
                                    isPlaying.value = audioServiceOnBind?.isPlaying() == true
                                },
                                previous = {
                                    audioServiceOnBind?.pause()
                                    Log.d("previous", "previous")
                                    audioServiceOnBind?.prev()
                                    music.value = musics[audioServiceOnBind?.musicId ?: 0]
                                    thread.start()
                                    isPlaying.value = audioServiceOnBind?.isPlaying() == true
                                },
                                seek = {
                                    Log.d("seek", "seek")
                                    //seek(it)

                                },
                                lyric = lyric.value,
                                imgs = imgs,
                                currImg = currimg.value
                            )
                            Weather(
                                width = widthDp/2,
                                height = heightDp,
                                backgroundColor = getDarkModeBackgroundColor(
                                    context = this@MainActivity,0
                                ),
                                subBackgroundColor = getDarkModeBackgroundColor(
                                    context = this@MainActivity,1
                                ),
                                weatherBackgroundColor = getDarkModeBackgroundColor(
                                    context = this@MainActivity,3
                                ),
                                fontColor = getDarkModeTextColor(
                                    context = this@MainActivity
                                ),
                                weather = weatherInfo.value,
                                hitokoto = hitokoto.value,
                                context = this@MainActivity,
                                localName = currlocalName.value
                            )
                        }
                    }

                }
            }
        }
    }
    private var onLocationListener: OnLocationListener? = null
    fun interface OnLocationListener {
        fun OnLocation(Location_latitude:String, Location_longitude:String,  province:String,  city:String,  area:String,  featureName:String);
    }

    fun setOnLocationListener(onLocationListener: OnLocationListener?) {
        this.onLocationListener = onLocationListener
    }

    override fun Location_Return(
        Location_latitude: Double,
        Location_longitude: Double,
        province: String?,
        city: String?,
        area: String?,
        featureName: String?
    ) {
        onLocationListener?.OnLocation(Location_latitude.toString(), Location_longitude.toString(), province?:"", city?:"", area?:"", featureName?:"")
    }
}
