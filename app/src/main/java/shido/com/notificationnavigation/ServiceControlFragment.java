package shido.com.notificationnavigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ServiceControlFragment extends Fragment {


    private Button btnStopService;
    public ServiceControlFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service_control, container, false);
        btnStopService = (Button) rootView.findViewById(R.id.btnstopService);
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                Intent intent = SimpleKittyService.getStopIntent(context);
                context.startService(intent);
            }
        });
        return rootView;
    }


}
