package ru.kn_n.testapplication2.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.squareup.picasso.Picasso




fun makeClient(context: Context) : GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("1046064538612-oj7n71hpc0kdrnetuj6rv4j4oslsh5oi.apps.googleusercontent.com")
        .requestId()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

fun makeDate(date: String): String{
    val apiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val newDate = LocalDateTime.parse(date, apiFormat)
    return newDate.toString()
}
