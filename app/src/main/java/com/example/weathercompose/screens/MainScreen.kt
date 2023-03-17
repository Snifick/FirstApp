package com.example.weathercompose.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.R
import com.example.weathercompose.data.WeatherModel
import com.example.weathercompose.myCity
import com.example.weathercompose.ui.theme.clr
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


const val API_KEY = "1bf54af3fd5a40259df182309231003"


@Composable
fun MainScreen(currentDay: MutableState<WeatherModel>, onClickSync: ()->Unit, onClickSearch: ()->Unit) {

    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(1f),
            backgroundColor = clr,
            elevation = 0.dp,
            shape = RoundedCornerShape(15.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentDay.value.time,
                        style = TextStyle(fontSize = 15.sp, color = Color.Black),
                        modifier = Modifier.padding(8.dp)
                    )
                    AsyncImage(
                        model = "https:" + currentDay.value.icon,
                        contentDescription = "cd",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(35.dp)
                    )
                }
                Text(text = currentDay.value.city, style = TextStyle(fontSize = 24.sp, color = Color.Black))
                Text(
                    text = if(currentDay.value.currentTemp.isNotEmpty())
                        currentDay.value.currentTemp.toFloat().toInt().toString() + "°C"
                       else "${currentDay.value.maxTemp}°C / ${currentDay.value.minTemp}°C" ,
                    style = TextStyle(fontSize = 55.sp, color = Color.Black)
                )
                Text(
                    text = "${currentDay.value.condition}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Black)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { onClickSearch.invoke()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "cd"
                        )
                    }
                    Text(
                        text = "${currentDay.value.minTemp}°C/${currentDay.value.maxTemp}°C",
                        style = TextStyle(fontSize = 14.sp, color = Color.Black)
                    )
                    IconButton(onClick = { onClickSync.invoke()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_cloud_sync_24),
                            contentDescription = "cd"
                        )
                    }

                }
            }
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(20.dp)
    ) {
        TabRow(selectedTabIndex = tabIndex, indicator = { pos ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, pos)
            )

        }, backgroundColor = clr) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }

                    },
                    text = { Text(text = text) })

            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            val list = when(index ) {
                0-> getWeatherBuHours(currentDay.value.hours)
                1-> daysList.value
                else -> daysList.value
            }
            MainList(list = list, currentDay = currentDay)


        }
    }

}

private fun getWeatherBuHours(hours: String): List<WeatherModel> {
    if (hours.isEmpty()) return listOf()
    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherModel>()
    for (i in 0 until  hoursArray.length()){
        val item  = hoursArray[i] as JSONObject
        list.add(
            WeatherModel("",
                time = item.getString("time"),
                currentTemp = item.getString("temp_c"),
                condition = "${item.getJSONObject("condition").getString("text")}",
                    icon =  "${item.getJSONObject("condition").getString("icon")}",
                "","",""
            )
        )
    }
    Log.d("qwe",list.toString())
    return list
}