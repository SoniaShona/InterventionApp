package com.example.interventionapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.text.SimpleDateFormat
import java.util.*
import java.io.Serializable
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){

    var interventionlist : ArrayList<Intervention> = ArrayList()
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        interventionlist = readFile()

        //Log.d("Liste","la liste " + interventionlist.toString())

        var interventionAdapter = InterventionAdapter(interventionlist,this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = interventionAdapter

        recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        dateChoisie.text = SimpleDateFormat( "MM/dd/yyyy", Locale.US).format(cal.getTime())
        removeFilterBtn.isEnabled = false


        filterBtn.setOnClickListener{
                var liste = ArrayList<Intervention>()
                for (item in interventionlist) {
                    if(SimpleDateFormat("MM/dd/yyyy", Locale.US).format(item.date.getTime()).compareTo(SimpleDateFormat("MM/dd/yyyy", Locale.US).format(cal.getTime()))==0) liste.add(item)
                }
                recyclerview.swapAdapter(InterventionAdapter(liste,this),false)
            filterBtn.isEnabled = false
            removeFilterBtn.isEnabled = true
        }

        removeFilterBtn.setOnClickListener{
            recyclerview.swapAdapter(InterventionAdapter(interventionlist,this),false)
            filterBtn.isEnabled = true
            removeFilterBtn.isEnabled = false
        }


        ajouterIntervention.setOnClickListener{
            var intent = Intent(this,AjouterIntervention::class.java)
            var bundle = Bundle()
            bundle.putSerializable("Liste", interventionlist as Serializable? )
            intent.putExtras(bundle)
            startActivity(intent)
        }

        chooseDate.setOnClickListener{
            DatePickerDialog(
                this@MainActivity, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateChoisie.text = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(cal.getTime())
        }
    }

    fun readFile() : ArrayList<Intervention>{
        val fileName = "intervention.txt"
        val fis: FileInputStream = openFileInput(fileName)
        var iss = ObjectInputStream(fis)
        val interventions: ArrayList<Intervention> = iss.readObject() as ArrayList<Intervention>
        iss.close()
        fis.close()
        return interventions
    }

}
