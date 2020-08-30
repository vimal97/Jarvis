package com.example.jarvis

import android.app.PendingIntent.getActivity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.FieldPosition


class SecretNotesAdapter(private val secretNotes: MutableList<SecretNoteData>): RecyclerView.Adapter<SecretNotesAdapter.SecretNotesHolder>() {

    lateinit var removedItem: SecretNoteData
    var removedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecretNotesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.secret_notes_container, parent, false)
        return SecretNotesHolder(itemView)
    }

    override fun getItemCount(): Int = secretNotes!!.size

    override fun onBindViewHolder(holder: SecretNotesHolder, position: Int) {
        if(secretNotes[position] != null){
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
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder){
        val sharedPreference: SharedPreference = SharedPreference(viewHolder.itemView.context)
        val gson = Gson()
        removedItem = secretNotes[viewHolder.adapterPosition]
        removedPosition = viewHolder.adapterPosition
        Log.v("Test_Vimal", "Removed position is : $removedPosition")
        secretNotes.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
        var flag = false
        Snackbar.make(viewHolder.itemView, "Item removed !!", Snackbar.LENGTH_LONG).setAction("UNDO"){
            secretNotes.add(removedPosition, removedItem)
            notifyItemInserted(removedPosition)
            var secretNotesStringList = mutableListOf<String>()
            for(i in secretNotes){
                secretNotesStringList.add(gson.toJson(i))
            }
            sharedPreference.pushSecretNotesList("SecretNotes", secretNotesStringList.joinToString("|"))
            flag = true
        }.show()
        if(!flag){
            val secretNotesString = sharedPreference.getSecretNotesList("SecretNotes")
            var secretNotesListString = mutableListOf<String>()
            if(secretNotesString != null){
                secretNotesListString = secretNotesString.split("|") as MutableList<String>
                secretNotesListString.remove(gson.toJson(removedItem))
                sharedPreference.pushSecretNotesList("SecretNotes", secretNotesListString.joinToString("|"))
            }
        }

    }

    class SecretNotesHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var task : TextView = itemView.findViewById(R.id.note)
        val time : TextView = itemView.findViewById(R.id.time)
        val date : TextView = itemView.findViewById(R.id.date)
    }
}