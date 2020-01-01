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
    
    private int notificationId =0;
    public void createNotification(View view){
        final TextView notText=findViewById(R.id.notificationText);
        notText.setText("pietje");
        createNotificationChannel();
        String id = getString(R.string.channel_id);

        Intent intent = new Intent(this, TimerActivity.class);// AlertDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        notificationId++;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Notificationtitel:" + notificationId)
                .setContentText("Notificationcontent")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
// notificationId is a unique int for each notification that you must define

        notificationManager.notify(notificationId, builder.build());

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String id = getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
