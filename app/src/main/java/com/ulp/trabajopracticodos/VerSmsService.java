package com.ulp.trabajopracticodos;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.Telephony;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class VerSmsService extends Service {

    private Timer timer = new Timer();
    private ContentResolver cr ;
    public VerSmsService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cr = this.getContentResolver();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                verUltimosSms();
                Log.d("salida","---------------------");
            }
        },0,9000);
        return START_STICKY;

    }

    @Override
    public boolean stopService(Intent name) {
        super.stopService(name);
        timer.cancel();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Log.d("salida","Servicio Suspendido");
    }

    @Override
    public IBinder onBind(Intent intent) {return  null;}

    private void verUltimosSms(){
        Uri sms = Uri.parse("content://sms/inbox");
        Cursor cursor=cr.query(sms,null,null,null,null);
        if(cursor.getCount()>0){
            int e=cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            int b=cursor.getColumnIndex(Telephony.Sms.BODY);
            int f=cursor.getColumnIndex(Telephony.Sms.DATE);
            int d=cursor.getColumnIndex(Telephony.Sms.DATE_SENT);
            int s=cursor.getColumnIndex(Telephony.Sms.STATUS);
            int contador =0;
            while (cursor.moveToNext() && contador<5)
            {
                String enviadoPor = cursor.getString(e);
                String bodysms = cursor.getString(b);
                String fechaEnviado = cursor.getString(f);
                String dateSent = cursor.getString(d);
                String estado = cursor.getString(s);
                Log.d("salida ", "Enviado por: " + enviadoPor + ",Mensaje: " + bodysms + ",fecha:" + fechaEnviado + ", fecha de enviado: " +dateSent+", estado:"+estado);
                contador++;

            }
        }
    }

}