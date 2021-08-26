package co.edu.unipiloto.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds=0,secondsLap=0,secondsBetween=0, laps=0;
    private boolean running, inLap;
    private String lapStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState!=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    //Start the stopwatch running when the start button is click
    public void onClickStart(View view){
        running=true;
    }

    //Stop the stopwatch running when the stop button is clicked
    public void onClickStop(View view){
        running=false;
    }

    //Reset the stopwatch when the reset button is clicked
    public void onClickReset(View view){
        running=false;
        seconds=0;
    }

    public void onClickLap(View view){
        inLap=true;
        laps++;
    }



    public void runTimer(){
        final TextView timeView=(TextView)findViewById(R.id.time_view);
        final TextView timeLap=(TextView)findViewById(R.id.time_lap);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                if(inLap && laps<=3){
                    secondsBetween=seconds-secondsLap;
                    secondsLap=seconds;
                    inLap=false;
                    lapStr+="Lap: "+secondsBetween+" seconds\n";
                    timeLap.setText(lapStr);
                }


                handler.postDelayed(this,1000);
            }
        });
    }
}