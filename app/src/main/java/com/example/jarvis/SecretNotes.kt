package com.example.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_secret_notes.*
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*

class SecretNotes : AppCompatActivity() {

    var adapter = SecretNotesAdapter(mutableListOf<SecretNoteData>())
    lateinit var secretNotesList: MutableList<SecretNoteData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_notes)
        supportActionBar?.title = "Your Secret Notes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var recyclerView = findViewById<RecyclerView>(R.id.secretNotesList1)
        var sharedPreference = SharedPreference(this)
//        sharedPreference.pushSecretNotesList("SecretNotes","")
        var gson = Gson()
        var secretNotesListString = sharedPreference.getSecretNotesList("SecretNotes")
        if(secretNotesListString != null){
            var secretNotesListJson = secretNotesListString.split("|")
            secretNotesList = mutableListOf()
            if (secretNotesListJson != null) {
                for(i in secretNotesListJson){
                    if(i != ""){
                        secretNotesList.add(gson.fromJson(i, SecretNoteData::class.java))
                    }
                }
            }
            adapter = SecretNotesAdapter(secretNotesList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.setHasFixedSize(true)
        }
        //drag delete option
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                (adapter as SecretNotesAdapter).removeItem(viewHolder)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(secretNotesList1)
    }

    fun addSecretNote(view: View) {
        val calendar = Calendar.getInstance();
        val year = calendar.get(Calendar.YEAR);
        val month = calendar.get(Calendar.MONTH) + 1;
        val day = calendar.get(Calendar.DAY_OF_MONTH);
        val today = "$day/$month/$year"
        val sdf = SimpleDateFormat("hh:mm a")
        val currentTime = sdf.format(Date())
        val secretNote = SecretNoteData(findViewById<EditText>(R.id.secretNote).text.toString(), today, currentTime)
        val sharedPreference = SharedPreference(this)
        val gson = Gson()
        val secretDataJson = gson.toJson(secretNote)
        val secretNotesString = sharedPreference.getSecretNotesList("SecretNotes")
        var secretNotesListString = listOf<String>()
        if(secretNotesString == "null"){
            sharedPreference.pushSecretNotesList("SecretNotes", secretDataJson)
        }
        else{
            if(secretNotesString != null){
                secretNotesListString = secretNotesString.split("|")
                secretNotesListString += secretDataJson
                sharedPreference.pushSecretNotesList("SecretNotes", secretNotesListString.joinToString("|"))
            }
        }
        secretNotesList.add(secretNote)
        adapter.notifyDataSetChanged()
        Log.v("Test_Vimal", "New updated list : $secretNotesList")
        findViewById<EditText>(R.id.secretNote).setText("")
    }
}
