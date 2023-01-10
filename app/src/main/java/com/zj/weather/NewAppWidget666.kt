package com.zj.weather

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SizeF
import android.widget.RemoteViews
import com.zj.utils.XLog

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [NewAppWidget666ConfigureActivity]
 */
class NewAppWidget666 : AppWidgetProvider() {
    override fun onReceive(context: Context?, intent: Intent?) {
        XLog.d(msg = "onReceive: intent.action:${intent?.action}")
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        XLog.i(msg = "onUpdate: appWidgetIds=${appWidgetIds.contentToString()}")
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        XLog.i(msg = "onDeleted: appWidgetIds=${appWidgetIds.contentToString()}")
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        XLog.i(msg = "onEnabled")
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        XLog.i(msg = "onDisabled")
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        XLog.i(msg = "onAppWidgetOptionsChanged: appWidgetId=$appWidgetId")
        // Get the new sizes.
        val sizes =
            newOptions?.getParcelableArrayList<SizeF>(AppWidgetManager.OPTION_APPWIDGET_SIZES)

        // Do nothing if sizes is not provided by the launcher.
        if (sizes.isNullOrEmpty()) {
            return
        }
        XLog.i(msg = "onAppWidgetOptionsChanged: size=${sizes}")
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = loadTitlePref(context, appWidgetId)
    XLog.d(msg = "updateAppWidget: widgetText=${widgetText}")
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget666)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}