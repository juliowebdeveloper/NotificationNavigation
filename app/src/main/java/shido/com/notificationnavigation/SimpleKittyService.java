package shido.com.notificationnavigation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class SimpleKittyService extends Service {

    public static final String START_ACTION = "start";
    public static final String STOP_ACTION = "stop";
    private static final int SERVICE_NOTIFY= 100;

    public SimpleKittyService() {
    }

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, SimpleKittyService.class);
        intent.setAction(SimpleKittyService.START_ACTION);
        return intent;
    }

    public static Intent getStopIntent(Context context){
        Intent intent = new Intent(context, SimpleKittyService.class);
        intent.setAction(SimpleKittyService.STOP_ACTION);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if(START_ACTION.equalsIgnoreCase(action)){
            handleStart();
        } else if (STOP_ACTION.equalsIgnoreCase(action)){
            handleStop();
        }else{
            Log.e("Error", "Ação desconhecida");
        }

        return START_NOT_STICKY;
    }

    private void handleStop() {
        //Tira do nivel de foreground service e remove a notificação
        stopForeground(true);
        stopSelf();
        displayStatusMessage("Service is stopped");
    }

    private void handleStart() {
        //Intent relacionada ao service (essa intent possui o botão stop service)
        Intent activityIntent = new Intent(this, MainActivity.class); //Main Activity que irá trocar o fragment
        activityIntent.putExtra(MainActivity.SERVICE, "service");
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);


        //Criando uma intent chamada stopService e chamando esse metodo que irá ser responsavel por parar o serviço (para atribui-la à notification)
        Intent stopServiceIntent = getStopIntent(this);
        PendingIntent stopServicePendingIntent  = PendingIntent.getService(this,0, stopServiceIntent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.kittyicon).
                setContentTitle("Kitty Service")
                .setContentText("Service in the foreground").
                setContentIntent(activityPendingIntent)
                .addAction(R.drawable.stop, "Stop", stopServicePendingIntent);   //Adicionando a action de parada a partir da intent criada acima que irá parar o serviço


        Notification notification = builder.build();
        ///Para elevar para o nivel de foreground service = Criando uma notificação ao start
        //Ao clicar o usuario será levando (atraves da intent ) para a activity que controla esse service.
        startForeground(SERVICE_NOTIFY, notification);

        displayStatusMessage("Service is running");
    }



    private void displayStatusMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
