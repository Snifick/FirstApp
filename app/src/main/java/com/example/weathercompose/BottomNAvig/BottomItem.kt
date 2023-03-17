package com.example.weathercompose.BottomNAvig

import com.example.weathercompose.R

sealed class BottomItem(val title:String, val iconId:Int, val route:String){
    object Screen1:BottomItem("Screen1", R.drawable.baseline_home_24, "screen_1")
    object Screen2:BottomItem("Screen2", R.drawable.baseline_home_repair_service_24, "screen_2")
    object Screen3:BottomItem("Screen3", R.drawable.baseline_location_on_24, "screen_3")
    object Screen4:BottomItem("Screen4", R.drawable.baseline_near_me_24, "screen_4")

}
