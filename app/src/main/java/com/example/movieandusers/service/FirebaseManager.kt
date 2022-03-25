package com.example.movieandusers.service

import android.location.Location
import android.text.format.DateFormat
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


object FirebaseManager {
    private val TAG = "FirebaseManager"
    fun sendLocationTest(location: Location) {
        val db = FirebaseFirestore.getInstance()
        // Create a new user with a first and last name
        val postValues: MutableMap<String, Any> = HashMap()
        postValues["lat"] = location.latitude
        postValues["lon"] = location.longitude

        val childUpdates: MutableMap<String, Any> = HashMap()
        val key: String = getCurrentTime() + " " + getCurrentDate()
        childUpdates[key] = postValues
        db.collection("locations")
            .add(childUpdates)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

    private fun getCurrentDate(): String {
        val tsLong = System.currentTimeMillis()
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = tsLong
        val date = DateFormat.format("dd-MM-yyyy", cal).toString()
        val time = DateFormat.format("HH:mm:ss", cal).toString()
        return date
    }

    private fun getCurrentTime(): String {
        val tsLong = System.currentTimeMillis()
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = tsLong
        val date =
            DateFormat.format("dd-MM-yyyy", cal).toString()
        return DateFormat.format("HH:mm:ss", cal).toString()
    }
}