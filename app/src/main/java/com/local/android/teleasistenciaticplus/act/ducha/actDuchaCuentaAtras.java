package com.local.android.teleasistenciaticplus.act.ducha;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.lib.sms.SmsLauncher;
import com.local.android.teleasistenciaticplus.modelo.TipoAviso;

public class actDuchaCuentaAtras extends Activity {

    //TAG para depuración
    private final String TAG = getClass().getSimpleName();

    public CountDownTimer TheCountDown; //clase para la cuenta atrás

    //párametros para la clase CountDownTimer
    public int futureTime; //tiempo total de la cuenta atrás
    public int interval;  //intervalo de refresco del minutero


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_act_ducha_cuenta_atras);

        //Recuperamos del intent los minutos seleccionados por el usuario
        futureTime = (int) getIntent().getExtras().get("minutos");

        //El intervalo de refresco se estable a segundos
        interval = 1000;

        //Iniciamos la cuenta atrás
        startCountDown();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_ducha_cuenta_atras, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startCountDown() {

        //Capturamos el tiempo (en minutos) introducido por el usuario
        final TextView mTextField = (TextView) findViewById(R.id.mTextField);

        futureTime = futureTime * 60000;


        TheCountDown = new CountDownTimer(futureTime, interval) {

            @Override
            public void onTick(long millisUntilFinished) {

                if (millisUntilFinished  < 60000) {
                    mTextField.setText("00:" + millisUntilFinished / 1000);
                } else {
                    //TODO parse the textfield to show minutes and seconds
                    int minutos = (int) (millisUntilFinished / 60000);
                    int segundos = (int) ( ( (millisUntilFinished / 1000) - (minutos * 60)) );
                    if(millisUntilFinished > 600000) {
                        mTextField.setText("" + minutos + ":" + segundos);
                    }else {
                        mTextField.setText("0" + minutos + ":" + segundos);
                    }

                }

            }

            @Override
            public void onFinish() {
                //TODO: launch SMS
                mTextField.setText("Send SMS");
                //Enviar los tres SMS
                //Tipo de SMS : envio de ducha
                SmsLauncher miSmsLauncher = new SmsLauncher( TipoAviso.DUCHANOATENDIDA  );
                Boolean listaContactosVacia = miSmsLauncher.generateAndSend();

            }
        }.start();

    }

    public void cancelCountDown(View v) {


        TheCountDown.cancel();

        TextView mTextField = (TextView) findViewById(R.id.mTextField);
        mTextField.setText("00:00");

    }



}
