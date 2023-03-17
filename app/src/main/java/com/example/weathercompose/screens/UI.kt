package com.example.weathercompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.data.WeatherModel
import com.example.weathercompose.ui.theme.clr

@Composable
fun MainList(list:List<WeatherModel>,currentDay: MutableState<WeatherModel>)
{
    LazyColumn(modifier = Modifier.fillMaxSize()){
        itemsIndexed( list
        ){

                index, item ->
            ListItem(item,currentDay)
        }
    }
}


@Composable
fun ListItem(item:WeatherModel,currentDay: MutableState<WeatherModel>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
            .clickable {
                if (item.hours.isEmpty()) return@clickable
                currentDay.value = item
            }
            .clip(shape = RoundedCornerShape(7.dp)),
        backgroundColor = clr,
        elevation = 0.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            Column(modifier = Modifier.padding(start = 8.dp,
            top = 5.dp,
            bottom = 5.dp)
            ) {
                Text(text = item.time,fontSize = 16.sp)
                Text(text = item.condition, fontSize = 12.sp)
            }
            Text(text = if(item.currentTemp.isEmpty())
                    "${item.maxTemp}°C / ${item.minTemp}°C"
                else "${item.currentTemp}°C"
                    , fontSize = 25.sp)
            AsyncImage(
                model = "https:" + item.icon,
                contentDescription = "cd",
                modifier = Modifier
                    .size(35.dp)
            )
        }
    }
}
@Composable
fun DialogSearch(dialogState: MutableState<Boolean>, onSubmit:(String)-> Unit){
    val dialogText = remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {
                                   dialogState.value = false

    }, confirmButton = {
        TextButton(onClick = {
            onSubmit(dialogText.value)
            dialogState.value = false
        }) {
            Text(text = "OK")

        }
    }, dismissButton = {"Cancel"},
    title = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Введите название города")
            TextField(value = dialogText.value, onValueChange = {
                dialogText.value = it

            })

        }

    })
}