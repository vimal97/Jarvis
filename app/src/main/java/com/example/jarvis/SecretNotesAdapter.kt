package com.example.jarvis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.reminder_container.view.*

class SecretNotesAdapter(private val secretNotes: List<SecretNoteData>?): RecyclerView.Adapter<SecretNotesAdapter.SecretNotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecretNotesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.secret_notes_container, parent, false)
        return SecretNotesHolder(itemView)
    }

    override fun getItemCount(): Int = secretNotes?.size

    override fun onBindViewHolder(holder: SecretNotesHolder, position: Int) {
        holder.task.text = secretNotes?.get(position)?.note
        holder.time.text = secretNotes?.get(position)?.time
    }

    class SecretNotesHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var task : TextView = itemView.viewTask
        val time : TextView = itemView.viewTime
    }

}