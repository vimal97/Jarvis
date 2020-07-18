package com.example.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class SecretNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_notes)
        supportActionBar?.title = "Your Secret Notes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var recyclerView = findViewById<RecyclerView>(R.id.secretNotesList)
        var sharedPreference = SharedPreference(this)
        var gson = Gson()
        var secretNotesListString = sharedPreference.getSecretNotesList("SecretNotes")
        if(secretNotesListString != null){
            var secretNotesListJson = secretNotesListString.split("|")
            var secretNotesList: MutableList<SecretNoteData> = mutableListOf()
            if (secretNotesListJson != null) {
                for(i in secretNotesListJson){
                    secretNotesList.add(gson.fromJson(i, SecretNoteData::class.java))
                }
            }
            recyclerView.adapter = SecretNotesAdapter(secretNotesList)
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.setHasFixedSize(true)
        }
    }
}
