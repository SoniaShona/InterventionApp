package com.example.interventionapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ajouter_intervention.*
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AjouterIntervention : AppCompatActivity()  {

    lateinit var interventions : ArrayList<Intervention>
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_intervention)
        interventions = intent.getSerializableExtra("Liste") as ArrayList<Intervention>



        ajouter.setOnClickListener {
            var intervention= Intervention(numEditText.text.toString().toInt(), cal, plombierEditText.text.toString(), typeEditText.text.toString()
            )
            interventions.add(intervention)
            writetofile()
            Toast.makeText(this,"Intervention ajoutée",Toast.LENGTH_LONG).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        annuler.setOnClickListener{
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        chooseDatebtn.setOnClickListener {
            DatePickerDialog(this@AjouterIntervention, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ){
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateTextView.text = SimpleDateFormat( "MM/dd/yyyy", Locale.US).format(cal.getTime())
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
