package com.example.movieandusers.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.movieandusers.BuildConfig
import com.google.android.gms.location.*
import java.util.*

class LocationService: Service() {
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var accuracy: Double = 0.0

    companion object {
        const val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            createNotificationChanel()
        else
            startForeground(
                1,
                Notification()
        )
        requestLocationUpdates()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d("LocationService: ", "onTaskRemoved")
        startService(rootIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val channelName = "Background Service"
        val chan = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager =
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder =
                NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification: Notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Geolocalizacion se está ejecutando")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
        startForeground(2, notification)
        Log.d("LocationService: ", "startForeground")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startTimer()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimerTask()
    }

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private fun startTimer() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                if (latitude != 0.0 && longitude != 0.0) {
                    Log.d(
                            "Location::",
                            "$latitude - $longitude"
                    )
                }
            }
        }
        timer!!.schedule(
                timerTask,
                0,
                (5*(60*1000))
        )
        Log.d("startTimer: ", "startTimerTask")
    }

    private fun stopTimerTask() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
            Log.d("LocationService: ", "stopTimerTask")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        Log.d("Location Service", "requestLocationUpdates")
        val request = LocationRequest()
        request.interval = (15*(60*1000))
        //request.fastestInterval = (5*(60*1000))
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this)

        val permission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location = locationResult.lastLocation
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                        accuracy = location.accuracy.toDouble()
                        Log.d("Location Service", "location update $location")
                        FirebaseManager.sendLocationTest(location)
                    }
                }
            }, null)
        }
        else{
            Log.d("LocationService: ", "requestLocationUpdates, permissions denied")
            val dictionary = HashMap<String, Any>()
            dictionary["LocationService"] = "permissions denied"
            //FirebaseManager.sendLogEvent(dictionary)
        }
    }
}
