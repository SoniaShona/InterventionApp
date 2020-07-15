package com.example.interventionapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_modifier_intervention.*
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ModifierIntervention : AppCompatActivity(){

    var cal = Calendar.getInstance()
    lateinit  var interventions : ArrayList<Intervention>
    var position =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifier_intervention)

        interventions = intent.getSerializableExtra("Liste") as ArrayList<Intervention>
        position = intent.getIntExtra("position",-1)

        numEditTextM.setText( interventions[position].numero.toString())

        dateTextViewM.text = SimpleDateFormat( "MM/dd/yyyy", Locale.US).format(interventions[position].date.getTime())
        plombierEditTextM.setText(interventions[position].nomPlombier)
        typeEditTextM.setText(interventions[position].type)



        datebtn.setOnClickListener{
            DatePickerDialog(this@ModifierIntervention, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        saveModif.setOnClickListener{
            interventions[position].numero =  numEditTextM.text.toString().toInt()
            interventions[position].date = cal
            interventions[position].nomPlombier = plombierEditTextM.text.toString()
            interventions[position].type = typeEditTextM.text.toString()
            writetofile()
            Toast.makeText(this,"Modifications enregistrées",Toast.LENGTH_LONG).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        deleteIntervention.setOnClickListener{
            interventions.remove(interventions[position])
            Toast.makeText(this,"L'intervention n "+position+" a été supprimée", Toast.LENGTH_SHORT).show()
            var f = File("intervention.txt").delete()
            writetofile()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }


    val dateSetListener= object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ){
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateTextViewM.text = SimpleDateFormat( "MM/dd/yyyy", Locale.US).format(interventions[position].date.getTime())
        }
    }


    fun writetofile(){
        val fileName = "intervention.txt"
        val fos: FileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(interventions)
        os.close()
        fos.close()
    }
}
