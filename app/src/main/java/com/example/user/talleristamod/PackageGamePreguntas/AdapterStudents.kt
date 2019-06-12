package com.example.user.talleristamod.PackageGamePreguntas

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.user.talleristamod.R
import java.util.ArrayList

class AdapterStudents (var list: ArrayList<AdapterStudentsClass>): RecyclerView.Adapter<AdapterStudents.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v=LayoutInflater.from(parent.context).inflate(R.layout.adapter_recycler_view_show_students, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binItems(list[position])
    }

    //var databaseImaginaries: DatabaseImaginaries = DatabaseImaginaries(context)

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        fun binItems(data:AdapterStudentsClass){
            val name:TextView = itemView.findViewById(R.id.tvStudentNameHolder)

            name.text = data.name

            val buttton: Button = itemView.findViewById(R.id.buttonSelectStudent)

            buttton.setOnClickListener(View.OnClickListener {
                val databaseImaginaries = DatabaseImaginaries(itemView.context)
                databaseImaginaries.selectIndividualStudent(data.id)

            })
        }
    }
}