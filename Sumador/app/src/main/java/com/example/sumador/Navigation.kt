package com.example.sumador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }

        composable(
            route = Screen.DetailScreen.route + "/{num1}/{num2}",
            arguments = listOf(
                navArgument("num1"){
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument("num2"){
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ){entry ->
            DetailScreen(
                navController = navController,
                num1 = entry.arguments?.getString("num1"),
                num2 = entry.arguments?.getString("num2"),
                )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var num1 by rememberSaveable {mutableStateOf("")}
    var num2 by rememberSaveable {mutableStateOf("")}

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        //Campo 1
        TextField(
            value = num1,
            onValueChange = {
            num1 = it
            },
            label = { Text(text = stringResource(id = R.string.numero1))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Campo 2
        TextField(
            value = num2,
            onValueChange = {
                num2 = it
            },
            label = { Text(text = stringResource(id = R.string.numero2))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Agregamos el boton
        Button(
            onClick = {
                navController.navigate(Screen.DetailScreen.withArgs(num1,num2))
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(id = R.string.sumar))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController ,num1: String? , num2: String?) {
    remember { mutableStateOf("") }
    val sum = (num1!!.toInt() + num2!!.toInt()).toString()
    var operacion = "$num1 + $num2 = $sum"
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        if (num1 != null && num2 != null) {
            TextField(
                value = "$num1 + $num2 = $sum",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    itemsIndexed(
                        listOf(operacion)
                    ){index, string ->
                        Text(text = stringResource(id = R.string.realizado),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp))
                        Text(
                            text = string,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

            }
            Button(
                onClick = {
                  navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)

            ) {
                Text(text = stringResource(id = R.string.volver))
            }

        }
    }
}