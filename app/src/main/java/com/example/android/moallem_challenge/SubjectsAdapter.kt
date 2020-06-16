package com.example.android.moallem_challenge

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * class documentation
 */
class SubjectsAdapter(private val subjects: ArrayList<Subject>) :
    RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.subject, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = subjects.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.subject.text = subjects[position].name
        holder.icon.setImageResource(subjects[position].iconRes)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subject: TextView = itemView.findViewById(R.id.subject_text_view)
        val icon: ImageButton = itemView.findViewById(R.id.subject_image_button)
    }


}