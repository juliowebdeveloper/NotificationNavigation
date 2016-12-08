package shido.com.notificationnavigation;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private static final String ACTION_DISPLAY_COURSE_FROM_NOTIFICATION = "shido.com.notificationnavigation.action.DISPLAY_COURSE_FROM_NOTIFICATION";
    private static final String COURSE_INDEX ="course index" ;
    private static final int NOTIFY_ID =1;
    public MainFragment() {
        // Required empty public constructor
    }

    private Button btnCourseNotification;
    private Button btnCustomNotification;
    private Button btnStartService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            setupButtons(rootView);
            
        return rootView;
    }

    private void setupButtons(View rootView) {
        btnCourseNotification = (Button) rootView.findViewById(R.id.btnCourseNotification);
        btnCourseNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCourseNotificationOnClick(btnCourseNotification);
            }
        });

        btnStartService = (Button) rootView.findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               btnStartServiceOnClick(btnStartService);
            }
        });

        btnCustomNotification = (Button) rootView.findViewById(R.id.btnCustomNotification);
        btnCustomNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCustomNotificationOnClick(btnCustomNotification);
            }
        });

    }


    private void btnCourseNotificationOnClick(Button b){
        Context context = getActivity();

        Intent i = new Intent(ACTION_DISPLAY_COURSE_FROM_NOTIFICATION);
        i.putExtra(COURSE_INDEX, 0);
        //PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);


        //Criando um taskstack e passando qual é o pai daquela intent, para quando o usuario clicar no
        //botão voltar, ele volte para aquela intent pai (lista por exemplo)
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(i);
        PendingIntent pi = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        //Se tiver uma pending intent semelhante à essa, sobreescreva a informação

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.cat_icon)
        .setAutoCancel(false)
        .setContentTitle("New Video")
        .setContentText("Android Fragments Course Updated")
        .setContentIntent(pi);
        Notification notific = builder.build();

        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFY_ID, notific);



    }
    private void btnStartServiceOnClick(Button b){
        Context context = getActivity();
        //Chamando a intent do service
        /*Intent intent = new Intent(context, SimpleKittyService.class);
        intent.setAction(SimpleKittyService.START_ACTION);
        context.startService(intent);*/

        Intent i = SimpleKittyService.getStartIntent(context);
        context.startService(i);

    }

    private void btnCustomNotificationOnClick(Button b){

        Context context = getActivity();
        //Utilizando RemoteViews para poder trazer funcionalidades do app para o OS
        //Ja que é o OS que toma conta das Notifications, para fazer essa ponte é necessario usar RemoteViews
        RemoteViews notificationViews = new RemoteViews(getContext().getPackageName(), R.layout.notification_simple);

        Intent stopIntent = SimpleKittyService.getStopIntent(context);
        Intent startIntent = SimpleKittyService.getStartIntent(context);

        //Sempre usar as pending intents para encapsular as intents
        PendingIntent stopPendingIntent = PendingIntent.getService(context, 0, stopIntent, 0);
        //Associando o botão de stop com o id da view com a pending intent que ele irá se relacionar
        notificationViews.setOnClickPendingIntent(R.id.btnStop, stopPendingIntent);

        PendingIntent startPendingIntent = PendingIntent.getService(context, 0, startIntent, 0);
        notificationViews.setOnClickPendingIntent(R.id.btnPlay, startPendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context) ;

        builder.setSmallIcon(R.drawable.kittyicon).
        setContent(notificationViews);
        Log.i("TESTE", "entrou button");
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFY_ID, notification);

    }


}
