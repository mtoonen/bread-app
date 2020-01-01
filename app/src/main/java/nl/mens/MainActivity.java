package nl.mens;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static TextView countdownTimerText;
    private static EditText minutes;
    private static Button startTimer;
    private static CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        countdownTimerText = (TextView) findViewById(R.id.counttime);
        minutes = (EditText) findViewById(R.id.enterMinutes);
        startTimer = (Button) findViewById(R.id.startTimer);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
//private int counter;
    public void runCounter(View view) {
        if (countDownTimer == null) {
            String getMinutes = minutes.getText().toString();
            if (!getMinutes.equals("") && getMinutes.length() > 0) {

                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds
                countDownTimer =
                        new CountDownTimer(noOfMinutes, 1000) {
                            @Override

                            public void onTick(long millisUntilFinished) {
                                long millis = millisUntilFinished;
                                //Convert milliseconds into hour,minute and seconds
                                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                                countdownTimerText.setText(hms);//set text
                                minutes.setText(hms);

                            }

                            @Override
                            public void onFinish() {
                                countdownTimerText.setText("Finished");
                            }
                        };
                startTimer.setText("Reset");
                countDownTimer.start();
            } else
                Toast.makeText(MainActivity.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
        }
        else {
            countDownTimer.cancel();
            countDownTimer = null;
            startTimer.setText("Start");
            countdownTimerText.setText("");
            minutes.setText("");

        }



    }

    public void createNotification(View view){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "123")
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Notificationtitel")
                .setContentText("Notificationcontent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }


}
