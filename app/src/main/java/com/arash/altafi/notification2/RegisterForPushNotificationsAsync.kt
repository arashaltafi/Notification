package com.arash.altafi.notification2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import me.pushy.sdk.Pushy
import java.net.URL

class RegisterForPushNotificationsAsync(
    activity: Activity
    ) : AsyncTask<Void, Void, Any>() {

    @SuppressLint("StaticFieldLeak")
    private val getActivity = activity

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void): Any {
        try {
            // Register the device for notifications
            val deviceToken = Pushy.register(getActivity)

            // Registration succeeded, log token to logcat
            Log.d("Pushy", "Pushy device token: $deviceToken")

            // Send the token to your backend server via an HTTP GET request
            URL("https://{YOUR_API_HOSTNAME}/register/device?token=$deviceToken").openConnection()

            // Provide token to onPostExecute()


            // Save token locally / remotely
            saveDeviceToken(deviceToken)

            return deviceToken
        } catch (exc: Exception) {
            // Registration failed, provide exception to onPostExecute()
            return exc
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Any) {

        // Registration failed?
        val message: String = if (result is Exception) {
            // Log to console
            Log.e("Pushy", result.message.toString())

            // Display error in alert
            result.message.toString()
        } else {
            // Registration success, result is device token
            "Pushy device token: $result\n\n(copy from logcat)"
        }

        // Display dialog
        android.app.AlertDialog.Builder(getActivity)
            .setTitle("Pushy")
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    fun getDeviceToken(): String? {
        return getSharedPreferences().getString("deviceToken", null)
    }

    private fun saveDeviceToken(deviceToken: String) {
        getSharedPreferences()
            .edit()
            .putString("deviceToken", deviceToken)
            .apply()
    }

    private fun getSharedPreferences(): SharedPreferences {
        return getActivity.applicationContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
    }
}