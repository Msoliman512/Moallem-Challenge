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
class SubjectsAdapter(private val subjects: ArrayList<Subject>, var subjectClickListener: OnSubjectIconClickListener) :
    RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.subject, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = subjects.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedSubject = subjects[position]
        holder.subject.text = selectedSubject.name
        holder.icon.setImageResource(selectedSubject.iconRes)

        holder.bind(selectedSubject,subjectClickListener)
    }

    /**
     *  some documentation xD
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subject: TextView = itemView.findViewById(R.id.subject_text_view)
        val icon: ImageButton = itemView.findViewById(R.id.subject_image_button)

        /**
         * function to attach a listener of type OnSubjectClickListener to the Subjects recyclerview items
         * @param subject1
         * @param clickListener
         */
        fun bind(subject1: Subject, clickListener: OnSubjectIconClickListener)
        {
            subject.text = subject1.name
            icon.setImageResource(subject1.iconRes)

            icon.setOnClickListener{
                clickListener.onSubjectIconClick(subject1)
            }
        }
    }
    /**
     * interface documentation
     */
    interface OnSubjectIconClickListener{
        /**
         * a callback method to override in the main activity to access subjects
         * @param subject
         */
        fun onSubjectIconClick(subject: Subject)
    }

}