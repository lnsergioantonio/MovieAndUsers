package com.example.movieandusers.ext

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun AppCompatActivity.checkPermission(permission: String) = run {
    (ActivityCompat.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED)
}

fun AppCompatActivity.shouldRequestPermissionRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestAllPermissions(permissions: Array<String>, requestCode:Int) {
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

fun AppCompatActivity.requestOnePermission(permission: String, requestCode:Int) {
    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
}

fun AppCompatActivity.isPermissionGranted(
    grantPermissions: Array<String>, grantResults: IntArray,
    permission: String
): Boolean {
    for (i in grantPermissions.indices) {
        if (permission == grantPermissions[i]) {
            return grantResults[i] == PackageManager.PERMISSION_GRANTED
        }
    }
    return false
}

fun FragmentActivity.checkPermission(permission: String) = run {
    (ActivityCompat.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED)
}

fun FragmentActivity.shouldRequestPermissionRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun Fragment.requestAllPermissions(permissions: Array<String>, requestCode:Int) {
    requestPermissions( permissions, requestCode)
}

fun Fragment.requestOnePermission(permission: String, requestCode:Int) {
    requestPermissions( arrayOf(permission), requestCode)
}

fun FragmentActivity.isPermissionGranted(
    grantPermissions: Array<String>, grantResults: IntArray,
    permission: String
): Boolean {
    for (i in grantPermissions.indices) {
        if (permission == grantPermissions[i]) {
            return grantResults[i] == PackageManager.PERMISSION_GRANTED
        }
    }
    return false
}


fun Context?.toast(text: String, duration: Int = Toast.LENGTH_LONG){
    this?.let { Toast.makeText(it, text, duration).show() }
}