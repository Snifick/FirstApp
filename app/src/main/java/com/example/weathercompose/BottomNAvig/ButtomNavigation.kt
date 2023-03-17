package com.example.weathercompose.BottomNAvig

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.weathercompose.ui.theme.clrBot
import com.example.weathercompose.ui.theme.clrICON

@Composable
fun BottomNavigation(
    navController: NavController
){
    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
        BottomItem.Screen4,
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = clrBot
    )
    {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRout = backStackEntry?.destination?.route
        listItems.forEach {
            item ->
            BottomNavigationItem(selected = currentRout == item.route, onClick = {
                   navController.navigate(item.route)

            },
            icon = {
                Icon(painter = painterResource(id = item.iconId), contentDescription = "Icon")
            },
                label = {
                    Text(text = item.title, fontSize = 9.sp)
                },
                selectedContentColor = clrICON,
                unselectedContentColor = Color.Gray
                )


        }
    }
}