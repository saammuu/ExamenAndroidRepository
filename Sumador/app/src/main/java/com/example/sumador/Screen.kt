package com.example.sumador

sealed class Screen(val route: String){
    //Definimos las pantallas que vamos a utilizar
    object MainScreen: Screen("main_screen")
    object DetailScreen: Screen("detail_screen")

    //Funcion que agrega n parametros a la ruta
    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
