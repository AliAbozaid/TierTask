package app.tier.utils

import android.content.Context
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import kotlin.math.roundToInt

fun Context.showErrorSnackBar(
    container: View? = null,
    anchorView: View? = null,
    duration: Int = Constant.SnackBar.ERROR_DURATION,
    @StringRes buttonText: Int? = null,
    message: String,
    action: (() -> Unit)? = null,
    onDismissCallback: (() -> Unit)? = null
) {
    showSnackbar(
        container = container,
        anchorView = anchorView,
        duration = duration,
        message = message,
        buttonText = buttonText,
        action = action,
        onDismissCallback = onDismissCallback
    )
}

private fun showSnackbar(
    container: View? = null,
    message: String,
    icon: Int? = null,
    anchorView: View? = null,
    @StringRes buttonText: Int? = null,
    action: (() -> Unit)? = null,
    duration: Int = Snackbar.LENGTH_LONG,
    onDismissCallback: (() -> Unit)? = null
) {
    val snackbar = createSnackbar(
        container = container,
        message = message,
        icon = icon,
        anchorView = anchorView,
        buttonText = buttonText,
        action = action,
        duration = duration,
        onDismissCallback = onDismissCallback
    )
    snackbar?.show()
}

private fun createSnackbar(
    container: View? = null,
    message: String? = null,
    messageSpanned: Spanned? = null,
    icon: Int? = null,
    anchorView: View? = null,
    @StringRes buttonText: Int? = null,
    action: (() -> Unit)? = null,
    duration: Int = Snackbar.LENGTH_LONG,
    onDismissCallback: (() -> Unit)? = null
): Snackbar? {
    val snackBar = Snackbar.make(
        container ?: anchorView?.rootView
            ?.findViewById(android.R.id.content)
            ?: return null,
        message ?: messageSpanned ?: "",
        duration
    )

    (
        snackBar.view.findViewById(
            com.google.android.material.R.id.snackbar_text
        ) as TextView
        ).maxLines =
        5

    if (action != null) {
        snackBar.setAction(
            buttonText
                ?: throw IllegalArgumentException("Missing button text")
        ) {
            action.invoke()
        }
    }
    if (icon != null) {
        val textView =
            snackBar.view.findViewById<TextView>(
                com.google.android.material.R.id.snackbar_action
            )
        val imgClose = ImageView(snackBar.context)
        val padding = snackBar.context.dpValueToPx(12).roundToInt()
        imgClose.setPadding(padding, padding, padding, padding)
        imgClose.scaleType = ImageView.ScaleType.CENTER_INSIDE
        imgClose.setImageResource(icon)
        (textView.parent as SnackbarContentLayout).addView(
            imgClose,
            0,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    if (onDismissCallback != null) {
        snackBar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(
                transientBottomBar: Snackbar?,
                event: Int
            ) {
                onDismissCallback.invoke()
                super.onDismissed(transientBottomBar, event)
            }
        })
    }

    anchorView?.let { snackBar.anchorView = it }
    return snackBar
}
