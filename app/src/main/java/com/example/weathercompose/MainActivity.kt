package com.example.weathercompose


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weathercompose.BottomNAvig.MainScrean
import com.example.weathercompose.data.WeatherModel
import com.example.weathercompose.screens.API_KEY
import com.example.weathercompose.screens.DialogSearch
import com.example.weathercompose.screens.MainScreen
import com.example.weathercompose.screens.TabLayout
import com.example.weathercompose.ui.theme.WeatherComposeTheme
import org.json.JSONObject
const val myCity = "Mogilev"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MainScrean()
          
        }
    }
}
@Composable
fun Weather(context: Context){
    val daysList = remember {
        mutableStateOf(listOf<WeatherModel>())
    }
    val dialogState = remember {
        mutableStateOf(false)
    }


    val currentDay = remember {
        mutableStateOf(WeatherModel(
            "","","","","","","",""
        ))
    }
    if (currentDay.value.city.isEmpty())
        getData("$myCity", context, daysList, currentDay)

    if(dialogState.value )
        DialogSearch(dialogState = dialogState, onSubmit = {
            getData(it,context, daysList, currentDay)
        })

    WeatherComposeTheme {
        Image(
            painter = painterResource(id = R.drawable.clouds),
            contentDescription = "cd",
            contentScale = ContentScale.Crop
        )
        Column() {

            MainScreen(currentDay, onClickSync = {
                getData(currentDay.value.city, context, daysList, currentDay)

            },
                onClickSearch = {dialogState.value = true})
            TabLayout(daysList,  currentDay)
        }

    }
}



private fun getData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>,
                currentDay:MutableState<WeatherModel>) {

    val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
            "&q=$city" +
            "&days=3" +
            "&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(Request.Method.GET,
        url,
        { response ->
            Log.d("Mylog", "$response")
           val list =  getWeatherByDays(response)
            currentDay.value = list[0]
            daysList.value = list
        },
        {
            Log.d("Mylog", "all NOT Good")
        })
    queue.add(sRequest)
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    Log.d("mylog2", "зашли ")
    if (response.isEmpty()) return listOf()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
    val listWM = ArrayList<WeatherModel>()
    Log.d("mylog2", "инициализировали ")
    Log.d("mylog2", "length ${days.length()} ")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        Log.d("mylog2", "инициализировали  item")
        listWM.add(

            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition")
                    .getString("text"),
                item.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
               item.getJSONArray("hour").toString()

            )
        )
        Log.d("mylog2", "добавили $i ")

    }
    Log.d("mylog2", listWM.toString())
    listWM[0] = listWM[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c"),
    )
    return listWM

}
