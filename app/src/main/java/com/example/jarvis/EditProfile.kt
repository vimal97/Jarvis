package com.example.jarvis

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.net.URI

class EditProfile : AppCompatActivity() {

    val sharedPreference = SharedPreference(this)
    val gson = Gson()
    private lateinit var profileImageURI: URI

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //fetching the profile details
        val fetchedProfile = sharedPreference.getProfileData("ProfileData")
        if(fetchedProfile != "null" && fetchedProfile != "" && fetchedProfile != null){
            Log.v("Test_Vimal",fetchedProfile)
            val temp = gson.fromJson<ProfileData>(fetchedProfile,ProfileData::class.java)
            if(temp.name == ""){
                findViewById<EditText>(R.id.profileName).hint = "Enter the name"
                findViewById<EditText>(R.id.profileEmail).hint = "Enter your email Id"
                findViewById<EditText>(R.id.profileCompany).hint = "Enter your company name"
//                findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.circleImageView).setBackgroundResource(R.drawable.profile_image)
            }
            else{
                findViewById<EditText>(R.id.profileName).setText(temp.name)
                findViewById<EditText>(R.id.profileEmail).setText(temp.email)
                findViewById<EditText>(R.id.profileCompany).setText(temp.company)
//                findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.circleImageView).setBackgroundResource(R.drawable.profile_image)
            }
        }
        else{
            findViewById<EditText>(R.id.profileName).hint = "Enter the name"
            findViewById<EditText>(R.id.profileEmail).hint = "Enter your email Id"
            findViewById<EditText>(R.id.profileCompany).hint = "Enter your company name"
//            findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.circleImageView).setBackgroundResource(R.drawable.profile_image)
        }
    }

    fun updateProfile(view: View) {
        val name = findViewById<EditText>(R.id.profileName).text.toString()
        val email = findViewById<EditText>(R.id.profileEmail).text.toString()
        val company = findViewById<EditText>(R.id.profileCompany).text.toString()
        sharedPreference.pushProfileData("ProfileData", gson.toJson(ProfileData(name, email, company, profileImageURI)))
        Toast.makeText(applicationContext, "Profile Updated Succesfully",Toast.LENGTH_SHORT).show()
    }

    fun updateProfilePicture(view: View) {


    }
}
