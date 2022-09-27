package ir.erfansn.composablescreens.travel.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

enum class OrientationMode { Portrait, Landscape }

@SuppressLint("SourceLockedOrientationActivity")
@Composable
internal fun OrientationLocker(
    orientationMode: OrientationMode
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.findActivity()?.requestedOrientation = if (orientationMode == OrientationMode.Portrait) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }
}

private fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}