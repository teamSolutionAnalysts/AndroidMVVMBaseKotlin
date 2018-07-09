package com.sa.baseproject.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


object ShareUtils {

    fun share(context: Context, share_text: String) {
        share(context, share_text)
    }


    fun shareImage(context: Context, uri: Uri, title: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/jpeg"
        context.startActivity(Intent.createChooser(shareIntent, title))
    }


    fun share(context: Context, extraText: String, subject: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, extraText)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(
                Intent.createChooser(intent, "Sent"))
    }
}
