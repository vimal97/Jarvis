package com.example.jarvis

import android.app.PendingIntent.getActivity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class SecretNotesAdapter(private val secretNotes: List<SecretNoteData>): RecyclerView.Adapter<SecretNotesAdapter.SecretNotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecretNotesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.secret_notes_container, parent, false)
        return SecretNotesHolder(itemView)
    }

    override fun getItemCount(): Int = secretNotes!!.size

    override fun onBindViewHolder(holder: SecretNotesHolder, position: Int) {
        if(secretNotes[position].note.length >= 20){
            holder.task.text = secretNotes[position].note.substring(0,20) + "..."
        }
        else{
            holder.task.text = secretNotes[position].note
        }
        holder.time.text = secretNotes[position].time
        holder.date.text = secretNotes[position].date
        holder.itemView.findViewById<ImageButton>(R.id.copySecret).setOnClickListener {
            val clipboard: ClipboardManager =
                holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", secretNotes[position].note)
            Toast.makeText(holder.itemView.context, "Text Copied !!",Toast.LENGTH_SHORT).show()
            clipboard.setPrimaryClip(clipData)
        }
    }

    class SecretNotesHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var task : TextView = itemView.findViewById(R.id.note)
        val time : TextView = itemView.findViewById(R.id.time)
        val date : TextView = itemView.findViewById(R.id.date)
    }
}