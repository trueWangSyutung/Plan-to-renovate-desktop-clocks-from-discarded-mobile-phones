package cn.tw.sar.lightnote.widgets

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.qweather.sdk.bean.weather.WeatherNowBean
import android.icu.text.ListFormatter.Width
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import cn.tw.sar.lightnote.R
import cn.tw.sar.lightnote.enums.StyleType
import cn.tw.sar.lightnote.util.IconsConvert
import kotlin.concurrent.thread

@Composable
@Preview(
    showBackground = true,
)
fun WeatherDetail(
    width: Float = 400f,
    height: Float = 400f,
    backgroundColor: Color = Color.White,
    subBackgroundColor: Color =  Color(0xFFF5EEE6),
    weatherBackgroundColor : Color = Color(0xFFE8E8E8),
    fontColor : Color = Color.Black,
    modifier: Modifier = Modifier,
    url : String = "https://www.baidu.com",

){
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
        // WebView
        AndroidView(
            factory = { context ->
                android.webkit.WebView(context).apply {
                    loadUrl(url)
                    // 设置背景颜色

                }
            },
            modifier = Modifier
                .fillMaxSize().background(
                    color = weatherBackgroundColor,
                    shape = MaterialTheme.shapes.medium
                ),
        )
    }
}


@Composable
@Preview(
    showBackground = true,
)
fun Weather(
    width: Float = 400f,
    height: Float = 400f,
    backgroundColor: Color = Color.White,
    subBackgroundColor: Color =  Color(0xFFF5EEE6),
    weatherBackgroundColor : Color = Color(0xFFE8E8E8),
    fontColor : Color = Color.Black,
    weather: WeatherNowBean? = WeatherNowBean(),
    modifier: Modifier = Modifier,
    textAlign : TextAlign = TextAlign.Start,
    hitokoto: String = "Loading...",
    context: Context = LocalContext.current,
    localName : String = "zh",
){
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
                .fillMaxHeight(0.5f)
                .background(
                    color = weatherBackgroundColor,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(10.dp)
                .clickable {
                    if (weather != null) {
                        // 跳转浏览器
                        var url = weather.basic.fxLink
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)

                    }
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            if (weather != null) {

                Icon(
                    painter = painterResource(id = IconsConvert.convertNumberToResourceID(weather.now?.icon?.toInt()!!)),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp, 0.dp, 0.dp, 0.dp)
                        .size(50.dp),
                    tint = fontColor
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = localName, color = fontColor,
                        fontSize = 20.sp,
                        textAlign = textAlign
                    )
                    Row {
                        Text(text = weather.now?.temp!!, color = fontColor,
                            fontSize = 40.sp,
                            textAlign = textAlign
                        )
                        Text(text = "°C", color = fontColor,
                            fontSize = 20.sp,
                            textAlign = textAlign
                        )
                    }
                    Text(text = weather.now?.text!!+" "+"${weather.now?.windDir!!} ${weather.now?.windScale!!}级", color = fontColor,
                        fontSize = 20.sp,
                        textAlign = textAlign
                    )

                }
            }else{
                Text(text = "Loading...", color = fontColor,
                    fontSize = 20.sp,
                    textAlign = textAlign
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
                .fillMaxHeight(1f)
                .background(
                    color = subBackgroundColor,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = hitokoto, color = fontColor,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}