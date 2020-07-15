package com.example.interventionapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.intervetion_item.view.*
import java.util.*
import java.io.Serializable
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class InterventionAdapter (var liste : ArrayList<Intervention> ,val context : Context) : RecyclerView.Adapter<ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val ite = layoutInflater.inflate(R.layout.intervetion_item, parent, false)
        return ViewHolder(ite)
    }

    override fun getItemCount(): Int {
        return liste.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.numero_tv.text ="Intervetion numéro " + liste[position].numero.toString()
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        holder.itemView.date_tv.text = "Le : " + sdf.format(liste[position].date.getTime())
        holder.itemView.nom_plombier_tv.text = "Plombier : "+ liste[position].nomPlombier
        holder.itemView.type_tv.text = "Type : " + liste[position].type


        holder.itemView.setOnClickListener{
            var intent = Intent(context,ModifierIntervention::class.java)
            var bundle = Bundle()
            bundle.putSerializable("Liste", liste as Serializable? )
            intent.putExtra("position",position)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }
}