package cn.tw.sar.lightnote.widgets

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cn.tw.sar.lightnote.R
import cn.tw.sar.lightnote.bean.SongList
import cn.tw.sar.lightnote.util.ImageItem
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import com.qweather.sdk.bean.weather.WeatherNowBean
import kotlin.concurrent.thread


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageWidget(
    width: Float = 400f,
    height: Float = 400f,
    backgroundColor: Color = Color.White,
    subBackgroundColor: Color =  Color(0xFFF5EEE6),
    fontColor : Color = Color.Black,
    weather: WeatherNowBean? = WeatherNowBean(),
    modifier: Modifier = Modifier,
    textAlign : TextAlign = TextAlign.Start,
    context: Context = LocalContext.current,
    imgs : MutableList<ImageItem> = mutableListOf(),
    curr : Int = 0,
){


    var currIndex by remember { mutableStateOf(curr) }//当前页面索引

    Column(
        modifier = modifier
            .width(width.dp).padding(15.dp)
            .height(height.dp).clip(MaterialTheme.shapes.medium)
            .paint(
                // 绘制路径图片
                painter = rememberAsyncImagePainter(
                    model = imgs[currIndex].path,
                    contentScale = ContentScale.FillBounds,
                ),

                contentScale = ContentScale.FillBounds,
            )
            .padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
        // 绘制路径图片
        var targetOffsetX = remember { mutableStateOf(0f) }//Activity的最终偏移目标
        var dragging by remember { mutableStateOf(false) }//是否正在被滑动
        Row(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        //跟踪滑动偏移
                        targetOffsetX.value += delta
                        Log.d("ImageWidget", "ImageWidget: ${targetOffsetX.value}")
                    },
                    onDragStarted = {
                        dragging = true
                    },

                    onDragStopped = {
                        //滑动停止时，根据偏移目标，判断是否需要切换页面
                        // 判断偏移量[=]d9f
                        if (targetOffsetX.value < 0) {
                            currIndex = currIndex - 1
                            if (currIndex < 0) {
                                currIndex = 0
                            }
                            targetOffsetX.value = 0f

                        } else if (targetOffsetX.value > 0) {
                            currIndex = currIndex + 1
                            if (currIndex >= imgs.size) {
                                currIndex = imgs.size - 1
                            }
                            targetOffsetX.value = 0f
                        } else {
                            // 判断点击的元素


                        }

                    },
                    startDragImmediately = true


                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            for (i in 0.. imgs.size - 1){
                if (i == currIndex) {

                    Image(
                        painter = painterResource(id = R.drawable.dots),
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp)
                            .size((20).dp)
                            .clickable {
                                currIndex = i
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.dots_un),
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp)
                            .size((20).dp)
                            .clickable {
                                currIndex = i
                            }
                    )
                }
            }


        }

    }
}