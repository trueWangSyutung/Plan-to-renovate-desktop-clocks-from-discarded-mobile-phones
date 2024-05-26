package cn.tw.sar.lightnote.widgets

import android.R.attr.textColor
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.tw.sar.lightnote.bean.SongList
import cn.tw.sar.lightnote.dao.MusicApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.qweather.sdk.bean.weather.WeatherNowBean
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicWidget(
    width: Float = 400f,
    height: Float = 400f,
    backgroundColor: Color = Color.White,
    subBackgroundColor: Color =  Color(0xFFF5EEE6),
    fontColor : Color = Color.Black,
    weather: WeatherNowBean? = WeatherNowBean(),
    modifier: Modifier = Modifier,
    textAlign : TextAlign = TextAlign.Start,
    context: Context = LocalContext.current,
    musics : MutableList<SongList> = mutableListOf(),
    music : SongList? = null,
    loadMusics: () -> Unit,
    select: (SongList) -> Unit,

    process: Int = 0,
    duraction : Int = 0,
    isPlaying: Boolean = false,

    play: () -> Unit,
    pause: () -> Unit,
    next: () -> Unit,
    previous: () -> Unit,
    seek: (Int) -> Unit,
    lyric: String = ""


) {
    var sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    var user = sharedPreferences.getString("user", "null")
    var retrofit =  Retrofit.Builder()
        //设置网络请求BaseUrl地址
        .baseUrl("https://music.wxd2zrx.top")
        //设置数据解析器
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    val api: MusicApi = retrofit.create(MusicApi::class.java)

    if (user == "null") {
        Log.d("MusicWidget", "User is null, UnLogin!")
        var phoneInput = remember {
            mutableStateOf("")
        }
        var codeInput = remember {
            mutableStateOf("")
        }
        var num = remember {
            mutableStateOf(0)
        }
        Column(
            modifier = modifier
                .width(width.dp)
                .height(height.dp)
                .padding(10.dp)
                .background(
                    color = backgroundColor, shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp),
            ) {
            Text("登陆酷狗音乐",
                color = fontColor,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
            )
            TextField(
                value = phoneInput.value,
                onValueChange =  {
                    phoneInput.value = it
                },
                label = {
                    Column {
                        Text("Phone Number")
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween


            ) {
                TextField(
                    value = codeInput.value,
                    onValueChange =  {
                        codeInput.value = it
                    },
                    label = {
                        Column {
                            Text("Code")
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.6f),
                    shape = MaterialTheme.shapes.extraLarge,
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,

                    )
                )
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = subBackgroundColor,
                        contentColor = fontColor
                    ),
                    onClick = {
                        if (num.value == 0) {
                            // 检查手机号是否合法
                            if (phoneInput.value.length != 11) {
                                Toast.makeText(context, "Phone number is not valid!", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (!phoneInput.value.startsWith("1")) {
                                Toast.makeText(context, "Phone number is not valid!", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            thread {
                                var res = api.sendCode(phoneInput.value).execute().body()
                                if (res == null) {
                                    return@thread
                                }
                                if (res.status==1){
                                    for (i in 0..60) {
                                        num.value = 60 - i
                                        Thread.sleep(1000)
                                    }
                                    num.value = 0
                                }
                            }


                        }else{
                            Toast.makeText(context, "Code has been sent!", Toast.LENGTH_SHORT).show()
                        }

                    }
                ) {
                    Text(
                        if (num.value == 0) "发送验证码" else "发送验证码(${num.value})")
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = subBackgroundColor,
                    contentColor = fontColor
                ),
                onClick = {
                if (phoneInput.value.length != 11) {
                    Toast.makeText(context, "Phone number is not valid!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (!phoneInput.value.startsWith("1")) {
                    Toast.makeText(context, "Phone number is not valid!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (codeInput.value.length != 6) {
                    Toast.makeText(context, "Code is not valid!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                thread {
                    var res = api.login(phoneInput.value, codeInput.value).execute().body()
                    if (res == null) {
                        return@thread
                    }
                    if (res.status == 1) {
                        sharedPreferences.edit().putString("user", Gson().toJson(res.data)).apply()
                        sharedPreferences.edit().putString("token", res.data?.token).apply()
                        user = Gson().toJson(res.data)
                    }else{
                    }
                }
            }) {
                Text("登陆")
            }
        }
    }else{
        Column(
            modifier = modifier
                .width(width.dp)
                .height(height.dp)
                .padding(10.dp)
                .background(
                    color = backgroundColor, shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp),
            ) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        color = backgroundColor, shape = MaterialTheme.shapes.medium
                    )


            ) {


                var isShowList = remember {
                    mutableStateOf(false)
                }


                if (isShowList.value) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .background(
                                color = backgroundColor, shape = MaterialTheme.shapes.medium
                            )
                            .verticalScroll(
                                rememberScrollState()
                            )
                    ) {
                        for (it in musics) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 10.dp)
                                    .clickable {
                                        isShowList.value = false
                                        select(it)
                                    }
                                    .background(
                                        color = subBackgroundColor,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .padding(20.dp),
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start

                            ) {
                                // http://imge.kugou.com/stdmusic/100/20210525/20210525155932774854.jpg
                                // 加载网络图片
                                var url = it.sizableCover?.replace("{size}", "100")

                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                )


                                Column(
                                    modifier = modifier
                                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = androidx.compose.ui.Alignment.Start
                                ) {
                                    Text(it.songname!! + " - " + it.authorName!!)
                                    Text(it.albumName!!)
                                }

                            }
                        }
                    }
                }else{
                    //var musicLast = sharedPreferences.getString("musicLast", "null")
                    var currMusic = music
                    if (currMusic == null){
                        currMusic = null
                    } else{
                        sharedPreferences.edit().putString("musicLast", Gson().toJson(currMusic)).apply()

                    }

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .background(
                                color = backgroundColor, shape = MaterialTheme.shapes.medium
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        if (currMusic == null) {
                            var url =  "https://developer.android.google.cn/static/codelabs/basic-android-kotlin-compose-load-images/img/70e008c63a2a1139_960.png"
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                "No Selected", color = fontColor,
                                fontSize = 40.sp,
                                textAlign = textAlign,
                            )

                        }else{

                            var url = currMusic?.sizableCover?.replace("{size}", "100")?: "https://developer.android.google.cn/static/codelabs/basic-android-kotlin-compose-load-images/img/70e008c63a2a1139_960.png"
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                currMusic?.songname!!, color = fontColor,
                                fontSize = 20.sp,
                                textAlign = textAlign,
                            )

                            Text(
                                currMusic?.albumName!! + "  " + currMusic?.authorName!!,
                                color = fontColor,
                                fontSize = 15.sp,
                                textAlign = textAlign,
                            )
                            Text(text = lyric, color = fontColor,
                                fontSize = 15.sp,
                                textAlign = textAlign
                            )

                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                LinearProgressIndicator(
                                    progress = process.toFloat() / duraction.toFloat(),
                                    modifier = Modifier
                                        .fillMaxWidth(0.75f)
                                        .padding(10.dp),

                                    )

                                Text("${process/60000}:${(process%60000)/1000}:${((process%60000)%1000)/10}/${duraction/60000}:${(duraction%60000)/1000}:${((duraction%60000)%1000)/10}", color = fontColor,
                                    fontSize = 10.sp,
                                    textAlign = textAlign,
                                    modifier = modifier
                                        .fillMaxWidth()
                                )
                            }
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    ,
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = subBackgroundColor,
                                        contentColor = fontColor
                                    ),
                                    onClick = {
                                        previous()
                                    }
                                ) {
                                    Text("上一首")
                                }
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = subBackgroundColor,
                                        contentColor = fontColor
                                    ),
                                    onClick = {
                                        if (isPlaying) {
                                            pause()
                                        }else{
                                            play()
                                        }

                                    }
                                ) {
                                    Text(if (isPlaying) "暂停" else "播放")
                                }
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = subBackgroundColor,
                                        contentColor = fontColor
                                    ),
                                    onClick = {
                                        next()
                                    }
                                ) {
                                    Text("下一首")
                                }
                            }


                        }






                    }

                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = subBackgroundColor,
                            contentColor = fontColor
                        ),
                        modifier = modifier
                            .fillMaxWidth(),
                        onClick = {
                            isShowList.value = !isShowList.value
                            if (isShowList.value) {
                                loadMusics()
                            }
                        }
                    ) {
                        Text(if (isShowList.value) "关闭今日推荐列表" else "打开今日推荐列表")
                    }

                }

            }

        }
    }

}