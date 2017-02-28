package personal.accelerometerboundedservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private AccelService myservice_;
    private Boolean bound;

    EditText myEditText;
    Button myButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        myEditText = (EditText) findViewById(R.id.editText);
        myButton = (Button) findViewById(R.id.button);

        myButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("clicked button");

                Button temp = (Button) findViewById(v.getId());


                Double avg_RMS = myservice_.getRMS();


                myEditText.setText(avg_RMS.toString());
            }
        });


        Intent intent = new Intent(this, AccelService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            AccelService.MyBinder binder_ = (AccelService.MyBinder) service;
            myservice_ = binder_.getService();
            bound = true;

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

            bound = false;

        }
    };


}
