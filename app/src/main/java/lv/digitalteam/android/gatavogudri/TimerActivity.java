package lv.digitalteam.android.gatavogudri;

import android.app.Notification;
import android.app.NotificationManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button timerButton;
    Boolean active = false;
    CountDownTimer countDownTimer;
    Button hardEggTimer;
    Button softEggTimer;
    NotificationManager notificationManager;

    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60; // (int) noapaļo, lai nebūtu decimāldaļas
        int seconds = (int) secondsLeft - minutes*60; // - minutes*60 atņem sekundes, kuras jau ir INT MINUTES

        // Format seconds
        String forZero;
        if (seconds == 0) {
            forZero = "00";
        } else if (seconds > 0 && seconds < 10) {
            forZero = "0" + Integer.toString(seconds);
        } else {
            forZero = Integer.toString(seconds);
        }

        // Format minutes
        String toString;
        if(minutes < 10) {
            toString = "0" + Integer.toString(minutes);
        } else {
            toString = Integer.toString(minutes);
        }

        //Display the time
        timerTextView.setText(toString + ":" + forZero);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //Timer TextView
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        //Buttons
        hardEggTimer = (Button) findViewById(R.id.hardEggTimer);
        softEggTimer = (Button) findViewById(R.id.softEggTimer);

        timerButton = (Button) findViewById(R.id.timerButton);
        timerButton.setText(getString(R.string.timer_go));

        timerButton.setOnClickListener(this);
        hardEggTimer.setOnClickListener(this);
        softEggTimer.setOnClickListener(this);

        //Timer Seek bar
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(1200); // 20 * 60 = 1200 seconds = 20 minutes
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    //Button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timerButton:

                if (active == false) {

                    timerButton.setText(getString(R.string.timer_stop));
                    active = true;
                    timerSeekBar.setEnabled(false);
                    hardEggTimer.setEnabled(false);
                    softEggTimer.setEnabled(false);

                    notificationManager = (NotificationManager) TimerActivity.this.getSystemService(TimerActivity.this.NOTIFICATION_SERVICE);

                    countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                        @Override
                        public void onTick(long untilDone) {
                            updateTimer((int) untilDone / 1000);
                            Log.i("Timer", "One second");
                        }

                        @Override
                        public void onFinish() {

                            try { //Lai novērstu crash, kad pārsēdzas uz citu fragment

                                timerTextView.setText("00:00");
                                timerSeekBar.setEnabled(true);
                                Log.i("Timer", "Finished!");

                                // Alarm sound for ending
                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // Ierīces default skaņa
                                Ringtone timerEnded = RingtoneManager.getRingtone(TimerActivity.this, notification);
                                timerEnded.setStreamType(AudioManager.STREAM_ALARM); // Lai skanētu, kad ir izslēgta skaņa
                                timerEnded.play();

                                //Notification
                                Notification notif = new Notification.Builder(TimerActivity.this)
                                        .setContentTitle(getString(R.string.app_name))
                                        .setContentText(getString(R.string.timer_has_finished))
                                        .setSmallIcon(R.mipmap.ic_launcher).build();
                                notificationManager.notify(0, notif);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }.start();
                } else {

                    timerTextView.setText("00:30");
                    timerSeekBar.setProgress(30);
                    countDownTimer.cancel();
                    timerButton.setText(getString(R.string.timer_go));
                    timerSeekBar.setEnabled(true);
                    active = false;
                    hardEggTimer.setEnabled(true);
                    softEggTimer.setEnabled(true);

                }

                break;

            //Set hard egg time
            case R.id.hardEggTimer:

                timerSeekBar.setProgress(420); //7 minutes
                timerTextView.setText("07:00");

                break;

            //Set soft egg time
            case R.id.softEggTimer:

                timerSeekBar.setProgress(180); //2 minutes
                timerTextView.setText("03:00");

                break;

        }

    }

}
