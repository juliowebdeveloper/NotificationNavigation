package shido.com.notificationnavigation;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public final static int NOTIFY_ID = 1;

    public final static String SERVICE = "SERVICE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String isService = getIntent().getStringExtra(SERVICE);
            if(isService!=null){
                Log.d("TAG", isService);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.mainFragment, new ServiceControlFragment()).commit();


            }else {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.mainFragment, new MainFragment()).commit();
            }


    }



}
