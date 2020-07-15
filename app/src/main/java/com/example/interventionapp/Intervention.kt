package com.example.interventionapp

import java.util.*
import java.io.Serializable

class Intervention(numero : Int, date : Calendar, NomPlombier : String, type : String):  Serializable {

    var numero = numero
    var date = date
    var nomPlombier = NomPlombier
    var type = type

}