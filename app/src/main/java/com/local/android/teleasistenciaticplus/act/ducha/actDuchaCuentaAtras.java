package com.local.android.teleasistenciaticplus.act.ducha;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.act.main.actMain;
import com.local.android.teleasistenciaticplus.lib.sms.SmsLauncher;
import com.local.android.teleasistenciaticplus.modelo.Constants;
import com.local.android.teleasistenciaticplus.modelo.GlobalData;
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

        //Generamos una notificación sonora
        final Notification beep_sound = new Notification.Builder(getApplicationContext())
                .setSound(Uri.parse("android.resource://" + GlobalData.getAppContext().getPackageName() + "/" + R.raw.beep_07))
                .build();

        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //futureTime = 5000;
        TheCountDown = new CountDownTimer(futureTime, interval) {

            Boolean alarmaDisparada = false;

            @Override
            public void onTick(long millisUntilFinished) {

                //TODO: ¿se puede reducir esta parte aplicando SimpleDateFormat?
                //TODO: poner un efecto en pantalla si la alarma acústica se ha disparado

                if (millisUntilFinished  < 60000) {
                    mTextField.setText("00:" + millisUntilFinished / 1000);

                    if (millisUntilFinished  < 10000) {
                        mTextField.setText("00:0" + millisUntilFinished / 1000);
                    }

                    notificationManager.notify(0, beep_sound);

                } else {

                    int minutos = (int) (millisUntilFinished / 60000);
                    int segundos = (int) ( ( (millisUntilFinished / 1000) - (minutos * 60)) );
                    if(millisUntilFinished > 600000) {
                        mTextField.setText("" + minutos + ":" + segundos);

                        if (segundos  < 10) {
                            mTextField.setText("" + minutos +":0" + segundos);
                        }


                    }else {
                        mTextField.setText("0" + minutos + ":" + segundos);
                        if (segundos  < 10) {
                            mTextField.setText("0" + minutos +":0" + segundos);
                        }
                    }

                }


            }

            @Override
            public void onFinish() {

                mTextField.setText("sms");
                //Enviar los tres SMS
                //Tipo de SMS : envio de ducha

                SmsLauncher miSmsLauncher = new SmsLauncher( TipoAviso.DUCHANOATENDIDA  );
                Boolean listaContactosVacia = miSmsLauncher.generateAndSend();

                //TODO Mostrar en un diálogo que el SMS se ha enviado
                Toast.makeText(getBaseContext(), "AVISO: SMS de tipo DUCHA enviado." , Toast.LENGTH_LONG).show();

                Intent intent = new Intent(GlobalData.getAppContext(), actMain.class);
                intent.putExtra("sms_ducha_enviado",true);

                startActivity(intent);

                if( Constants.SHOW_ANIMATION ) {

                    overridePendingTransition(R.animator.animation2, R.animator.animation1);

                }
                finish();
            }
        }.start();

    }

    public void cancelCountDown(View v) {


        TheCountDown.cancel();

        TextView mTextField = (TextView) findViewById(R.id.mTextField);
        mTextField.setText("00:00");

    }



}
