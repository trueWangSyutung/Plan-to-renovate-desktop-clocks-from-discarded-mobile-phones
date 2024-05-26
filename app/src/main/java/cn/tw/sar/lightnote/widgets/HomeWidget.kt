package cn.tw.sar.lightnote.widgets

import android.content.Context
import android.icu.text.ListFormatter.Width
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.tw.sar.lightnote.R
import cn.tw.sar.lightnote.bean.SongList
import cn.tw.sar.lightnote.enums.StyleType
import cn.tw.sar.lightnote.util.ImageItem
import kotlin.concurrent.thread


@Composable
@Preview(
    showBackground = true,
)
fun TimesView(
    width: Float = 300f,
    height: Float = 300f,
    backgroundColor: Color = Color.White,
    subBackgroundColor: Color =  Color(0xFFF5EEE6),
    fontColor : Color = Color.Black,
    time: String = "18:56:52",
    date: String = "2022-06-05",
    lunar: String = "五月初五",
    modifier: Modifier = Modifier,
    textAlign : TextAlign = TextAlign.Start
){
    Column(
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
            .background(
                color = backgroundColor
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp,20.dp,20.dp,20.dp)
                .background(
                    color = subBackgroundColor,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = lunar, color = fontColor,
                fontSize = 20.sp,
                textAlign = textAlign
            )
            Text(text = time, color = fontColor,
                fontSize = 65.sp,
                textAlign = textAlign
            )
            Text(text = date, color = fontColor,
                fontSize = 20.sp,
                textAlign = textAlign
            )
        }
    }

}

@Preview(
    showBackground = true,

)
@Composable
fun LineBar(
    maxPage: Int = 3,
    width: Int = 30,
    page: Int = 1,
    onUpEnd: () -> Unit = {
        println("end")
    },
    onDownEnd: () -> Unit = {
        println("end")
    },
    onDotsClick: (Int) -> Unit = {
        println("click")
    }
){
    var targetOffsetX = remember { mutableStateOf(0f) }//Activity的最终偏移目标
    var dragging by remember { mutableStateOf(false) }//是否正在被滑动
    // 显示下方的滚动条
    Column(
        modifier = Modifier
            .width(30.dp)
            .fillMaxHeight()
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    //跟踪滑动偏移
                    targetOffsetX.value += delta
                },
                onDragStarted = {
                    dragging = true
                },

                onDragStopped = {
                    //滑动停止时，根据偏移目标，判断是否需要切换页面
                    // 判断偏移量[=]d9f
                    if (targetOffsetX.value > 0) {
                        onDownEnd()

                        targetOffsetX.value = 0f

                    } else if (targetOffsetX.value < 0) {
                        onUpEnd()
                        targetOffsetX.value = 0f
                    } else {
                        // 判断点击的元素


                    }

                },
                startDragImmediately = true


            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        for (i in 1..maxPage) {
            if (i == page) {

                Image(
                    painter = painterResource(id = R.drawable.dots),
                    contentDescription = null,
                    modifier = Modifier
                        .size((width-10).dp)
                        .clickable {
                            onDotsClick(i)
                        }
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.dots_un),
                    contentDescription = null,
                    modifier = Modifier
                        .size((width-10).dp)
                        .clickable {
                            onDotsClick(i)
                        }
                )
            }
        }


    }
}

@Composable

fun Subjection(
    width: Float = 400f,
    height: Float = 400f,
    backgroundColor: Color = Color.White,
    subBackgroundColor: Color =  Color(0xFFF5EEE6),
    fontColor : Color = Color.Black,
    time: String = "18:56:52",
    date: String = "2022-06-05",
    lunar: String = "五月初五",
    modifier: Modifier = Modifier,
    textAlign : TextAlign = TextAlign.Start,
    context: Context,
    musics: MutableList<SongList>,
    music: SongList? = null,
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
    lyric: String = "",
    imgs: MutableList<ImageItem>,
    currImg: Int = 0
){
    var index = remember { mutableStateOf(1) }
    var scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
            .background(
                color = backgroundColor
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize().padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LineBar(
                maxPage = 3,
                page = index.value,
                width = 30,
                onDownEnd = {
                    Log.d("index", index.value.toString())
                    index.value = index.value - 1
                    if (index.value < 1) {
                        index.value = 3
                    }

                },
                onUpEnd = {
                    Log.d("index", index.value.toString())
                    index.value = index.value + 1
                    if (index.value > 3) {
                        index.value = 1
                    }

                },
                onDotsClick = {
                    index.value = it
                    Log.d("index", index.value.toString())
                }
            )
            if (index.value == 1) {
                TimesView(
                    width = width-30,
                    height = height,
                    backgroundColor = backgroundColor,
                    subBackgroundColor = subBackgroundColor,
                    fontColor = fontColor,
                    time = time,
                    date = date,
                    lunar = lunar,
                    textAlign = textAlign
                )
            }
            else if (index.value == 2) {
                MusicWidget(
                    width = width-30,
                    height = height,
                    backgroundColor = backgroundColor,
                    subBackgroundColor = subBackgroundColor,
                    fontColor = fontColor,
                    textAlign = textAlign,
                    modifier = Modifier,
                    context = context,
                    musics = musics,
                    music = music,
                    loadMusics = {
                        Log.d("load", "load")
                        loadMusics()
                    },
                    select = {
                        Log.d("select", "select")
                        select(it)
                    },
                    process = process,
                    duraction = duraction,
                    isPlaying = isPlaying,
                    play = {
                        Log.d("play", "play")
                        play()
                    },
                    pause = {
                        Log.d("pause", "pause")
                        pause()
                    },
                    next = {
                        Log.d("next", "next")
                        next()
                    },
                    previous = {
                        Log.d("previous", "previous")
                        previous()
                    },
                    seek = {
                        Log.d("seek", "seek")
                        seek(it)
                    },
                    lyric = lyric


                )
            }
            else if (index.value == 3) {
                ImageWidget(
                    width = width-30,
                    height = height,
                    backgroundColor = backgroundColor,
                    subBackgroundColor = subBackgroundColor,
                    fontColor = fontColor,
                    textAlign = textAlign,
                    modifier = Modifier,
                    context = context,
                    imgs = imgs,
                    curr = currImg
                )
            }

        }

    }

}

