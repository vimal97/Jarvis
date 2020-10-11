package com.example.jarvis

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
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
        sharedPreference.pushProfileData("ProfileData", "")
        val fetchedProfile = sharedPreference.getProfileData("ProfileData")
        if(fetchedProfile != "null" && fetchedProfile != "" && fetchedProfile != null){
            Log.v("Test_Vimal",fetchedProfile)
            val temp = gson.fromJson<ProfileData>(fetchedProfile,ProfileData::class.java)
            if(temp.name == ""){
                findViewById<EditText>(R.id.profileName).hint = "Enter the name"
                findViewById<EditText>(R.id.profileEmail).hint = "Enter your email Id"
                findViewById<EditText>(R.id.profileCompany).hint = "Enter your company name"
                findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.circleImageView).setImageURI(temp.profileImage)
            }
            else{
                findViewById<EditText>(R.id.profileName).setText(temp.name)
                findViewById<EditText>(R.id.profileEmail).setText(temp.email)
                findViewById<EditText>(R.id.profileCompany).setText(temp.company)
            }
        }
        else{
            findViewById<EditText>(R.id.profileName).hint = "Enter the name"
            findViewById<EditText>(R.id.profileEmail).hint = "Enter your email Id"
            findViewById<EditText>(R.id.profileCompany).hint = "Enter your company name"
            sharedPreference.pushProfileData("ProfileData", gson.toJson(ProfileData("", "", "", Uri.parse("http://stackoverflow.com"))))
        }
    }

    fun updateProfile(view: View) {
        val name = findViewById<EditText>(R.id.profileName).text.toString()
        val email = findViewById<EditText>(R.id.profileEmail).text.toString()
        val company = findViewById<EditText>(R.id.profileCompany).text.toString()
        val profileData = sharedPreference.getProfileData("ProfileData")
        Log.v("Test_Vimal", "Before updation : $profileData")
        var profileDataJson = Gson().fromJson<ProfileData>(profileData, ProfileData::class.java)
        profileDataJson.name = name
        profileDataJson.email = email
        profileDataJson.company = company
        if(profileDataJson.profileImage == null){
            profileDataJson.profileImage = Uri.parse("")
        }
        sharedPreference.pushProfileData("ProfileData", gson.toJson(profileDataJson))
        Toast.makeText(applicationContext, "Profile Updated Succesfully",Toast.LENGTH_SHORT).show()
    }

    fun updateProfilePicture(view: View) {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Choose and image"), 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123 && resultCode == Activity.RESULT_OK && data != null){
            val profileUri = data.data
            findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.circleImageView).setImageURI(profileUri)
            val sharedPreference = SharedPreference(this)
            val profileData = sharedPreference.getProfileData("ProfileData")
            Log.v("Test_Vimal", "URI : $profileUri")
            val gson = Gson()
            var profileDataJson = gson.fromJson<ProfileData>(profileData, ProfileData::class.java)
            if(profileUri != null){
                profileDataJson.profileImage = profileUri
            }
            Log.v("Test_Vimal", "Updated profile Image : $profileDataJson")
            sharedPreference.pushProfileData("ProfileData", gson.toJson(profileDataJson))
        }
    }
}
