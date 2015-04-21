package com.local.android.teleasistenciaticplus.lib.sms;

import com.local.android.teleasistenciaticplus.lib.helper.AppSharedPreferences;
import com.local.android.teleasistenciaticplus.lib.helper.AppTime;

/**
 * Created by FESEJU on 25/03/2015.
 * Clase helper encargada de generar el texto de los mensajes SMS que luego enviará SmsDispatcher
 */
public class SmsTextGenerator {

    String nombre;
    String apellidos;
    String currentDateandTime = new AppTime().getTimeDate();

    public SmsTextGenerator() {

        String[] nombreApellidos = new AppSharedPreferences().getUserData();
        this.nombre = nombreApellidos[0];
        this.apellidos = nombreApellidos[1];
    }

    public String getTextGenerateSmsIamOK(String phoneNumberDestination) {
        // Andres García comunica que se encuentra bien a las 12:00 del día 12/03/2015

        String smsBodyText = "TELEASISTENCI@TIC+: " + nombre + " " + apellidos + " comunica que se encuentra bien a las " + currentDateandTime;
        return smsBodyText;
    }

    public String getTextGenerateSmsAviso(String phoneNumberDestination) {
        // Andres García comunica que se encuentra bien a las 12:00 del día 12/03/2015
        String smsBodyText = "TELEASISTENCI@TIC+: " + nombre + " " + apellidos + " ha generado un aviso " + currentDateandTime;
        return smsBodyText;
    }

    public String getTextGenerateSmsDucha(String phoneNumberDestination) {
        String smsBodyText = "TELEASISTENCI@TIC+: " + nombre + " " + apellidos + " ha generado un aviso de ducha" + currentDateandTime;
        return smsBodyText;
    }

}
