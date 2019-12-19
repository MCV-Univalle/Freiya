package com.example.user.talleristamod.PackageGamePreguntas

import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.user.talleristamod.GlobalVariables.GlobalVariables
import com.example.user.talleristamod.PackageGameRaceQr.ActivityFollowCodeQr
import com.example.user.talleristamod.PackageGameRaceQr.BarcodeCaptureActivity
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivitySelectBadge
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

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        fun binItems(data:AdapterStudentsClass){
            if (GlobalVariables.ACTIVITY_FUNCTION === "Badges") {

                val name:TextView = itemView.findViewById(R.id.tvStudentNameHolder)
                name.text = data.name
                val buttton: Button = itemView.findViewById(R.id.buttonSelectStudent)
                buttton.setOnClickListener(View.OnClickListener {
                    val intent = Intent(itemView.context, ActivitySelectBadge::class.java)
                    intent.putExtra("idStudent", data.id)
                    itemView.context.startActivity(intent)
                })

            }else {

                val name:TextView = itemView.findViewById(R.id.tvStudentNameHolder)
                name.text = data.name
                val buttton: Button = itemView.findViewById(R.id.buttonSelectStudent)
                buttton.setOnClickListener(View.OnClickListener {
                val databaseImaginaries = DatabaseImaginaries(itemView.context)
                databaseImaginaries.selectIndividualStudent(data.id)

            })}
        }
    }
}