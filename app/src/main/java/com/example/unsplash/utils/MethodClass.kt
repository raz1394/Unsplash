package com.example.unsplash.utils

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.unsplash.api.Resource

fun Context.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> {
            toast("Something went wrong!!")
        }
        else -> {
            toast("Something went wrong")
            if (retry != null) {
                retry()
            }
        }
    }

}

fun Context.toast(message: String, action: (() -> Unit)? = null) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun requestWritePermission(context: Activity?, requestCode: Int): Boolean {
    val list = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var check = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        for (permission in list) {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                check = false
                ActivityCompat.requestPermissions(context, list.toTypedArray(), requestCode)
            }
        }
        if (check) {
            return true
        }
    }
    return false
}

fun downloadDocs(baseActivity: Context, url: String?, title: String?): Boolean {
    val extension = url?.substring(url.lastIndexOf("."))
    val downloadReference: Long
    val dm: DownloadManager =
        baseActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val uri = Uri.parse(url)
    val request = DownloadManager.Request(uri)
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_PICTURES,
        "unsplash"
    )

    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setTitle("Downloading $title")

    downloadReference = dm.enqueue(request)

    return true
}

