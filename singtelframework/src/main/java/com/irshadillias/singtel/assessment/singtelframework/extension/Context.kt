package com.irshadillias.singtel.assessment.singtelframework.extension

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()