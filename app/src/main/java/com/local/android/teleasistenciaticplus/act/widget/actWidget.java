package com.local.android.teleasistenciaticplus.act.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.act.main.actMain;

/**
 * Implementación de la acción del Widget que levanta la Actividad Principal.
 */
public class actWidget extends AppWidgetProvider
{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++)
            actualizarWidget(context, appWidgetManager, appWidgetIds[i]);
    }


    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first layout_widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last layout_widget is disabled
    }

    static void actualizarWidget(Context context, AppWidgetManager widgetManager, int widgetId)
    {
        // Creamos los Intent y PendingIntent para lanzar la actividad principal de la APP
        PendingIntent widgetPendingIntent;
        Intent widgetIntent;
        widgetIntent = new Intent(context,actMain.class);
        widgetPendingIntent = PendingIntent.getActivity(context, widgetId, widgetIntent,
                PendingIntent.FLAG_UPDATE_CURRENT );
        // Recupero las vistas del layout del widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        // Asigno un listener para el botón del widget y lanzar el PendingIntent.
        views.setOnClickPendingIntent(R.id.ib_boton_rojo, widgetPendingIntent);
        // Solicito al widget manager que actualice el layout del widget
        widgetManager.updateAppWidget(widgetId, views);
        Log.i("actWidget.onUpdate()","Widget actualizado.");
    }

    /////////////////////////////////
    // IMPLEMENTACIÓN BÁSICA DEL RECEPTOR DE EVENTOS DEL WIDGET. SE DEJA PARA FUTUROS USOS.
    /////////////////////////////////
 /*   public void onReceive(Context context, Intent intent)
    {
        //super.onReceive(context, intent);

        // Recupero una instancia de mi widgetManager
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context.getPackageName(),
                    actWidget.class.getName());

        int[] WidgetIds = widgetManager.getAppWidgetIds(thisWidget);
        Log.i("actWidget.onReceive()","widgetId"+widgetManager.getAppWidgetIds(thisWidget));
        Log.i("actWidget.onReceive()","Recibido evento con acción: "+intent.getAction());
        // Fuerzo la actualización del Widget
        onUpdate(context, widgetManager, WidgetIds);
    }*/
}
