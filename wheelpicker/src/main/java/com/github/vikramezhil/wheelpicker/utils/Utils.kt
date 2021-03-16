package com.github.vikramezhil.wheelpicker.utils

import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner

/**
 * Gets the view lifecycle owner
 * @receiver View The view
 * @return The lifecycle owner attached to the view
 */
fun View.getLifeCycleOwner(): LifecycleOwner? {
    var viewContext = context
    while (viewContext is ContextWrapper) {
        if (viewContext is LifecycleOwner) {
            return viewContext
        }

        viewContext = viewContext.baseContext
    }

    return null
}