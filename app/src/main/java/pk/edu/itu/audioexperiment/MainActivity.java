package pk.edu.itu.audioexperiment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    //Initialize variable
    TextView playerPosition, playerDuration;
    SeekBar seekBar;
    ImageView btRew, btPlay, btPause, btFf;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
    Button btn;

    // Storing data into SharedPreferences
    SharedPreferences sharedPreferences;

    // Creating an Editor object to edit(write to the file)
    SharedPreferences.Editor myEdit;

    int[] timestamps= new int[]{ 2000,3500,5000,6000,10000, 13000 };

   /* @Override
    protected void onDestroy() {
        Toast.makeText(getApplicationContext(), "ON DESTROY", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        myEdit.putInt("timestamp", 0);
        myEdit.commit();
    }

    */

    @Override
    protected void onResume() {
        super.onResume();
       // Toast.makeText(getApplicationContext(), "ON RESUME", Toast.LENGTH_SHORT).show();

        //get current position of media player
       int currentPosition = sharedPreferences.getInt("timestamp", 0);
       for(int i = 1;i< timestamps.length; i++){
           if(currentPosition >= timestamps[i-1] && currentPosition < timestamps[i]){
               currentPosition = timestamps[i-1];
           }
           //else if(currentPosition == timestamps[i]) it will stay the same

       }
        //check condition
       playerPosition.setText(convertFormat(currentPosition));
        //set progress on seek bar
       mediaPlayer.seekTo(currentPosition);
       mediaPlayer.start();
        //Hide play button
        btPlay.setVisibility(View.GONE);
        //Show pause button
        btPause.setVisibility(View.VISIBLE);


        //Initialize runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                //set progress on seek bar
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                //handler post delay for 0.5 seconds
                handler.postDelayed(this, 500);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(getApplicationContext(), "called", Toast.LENGTH_SHORT).show();
        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        myEdit.putInt("timestamp", 0);
        myEdit.commit();

        //Location code
        int p = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (p != PackageManager.PERMISSION_GRANTED) {

            //popup ata hai is se
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Toast.makeText(getApplicationContext(), "PERMISSION GRANTED", Toast.LENGTH_LONG).show();

            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            if (loc == null) {
                Toast.makeText(getApplicationContext(), "LOCATION NOT LOADED", Toast.LENGTH_LONG).show();

            }

            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ex) {}

            if(!gps_enabled && !network_enabled) {
                // notify user
                Toast.makeText(getApplicationContext(), "LOCATION TURNED OFF", Toast.LENGTH_LONG).show();
                /*new AlertDialog.Builder(getApplicationContext())
                        .setMessage(R.string.gps_network_not_enabled)
                        .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                getApplicationContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton(R.string.Cancel,null)
                        .show();

                 */
            }

        }








        //reading location

        //buttonShow
        btn = findViewById(R.id.btnshow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //time is in milisecond
                myEdit.putInt("timestamp", 12000);
               // myEdit.putBoolean("check", true);
                myEdit.commit();
                Intent intent=new Intent(MainActivity.this, randomStuff.class);
                startActivity(intent);
            }
        });

        //assign variable
        playerPosition = findViewById(R.id.player_position);
        playerDuration = findViewById(R.id.player_duration);
        seekBar = findViewById(R.id.seek_bar);
        btRew = findViewById(R.id.bt_rew);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btFf = findViewById(R.id.bt_ff);





        //Initialize media player
        mediaPlayer = MediaPlayer.create(this, R.raw.audioo);


        //Initialize runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                //set progress on seek bar
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                //handler post delay for 0.5 seconds
                handler.postDelayed(this, 500);
            }
        };

        //Get duration of media player
        int duration = mediaPlayer.getDuration();
        //Convert millisecond to minute and seconds
        String sDuration = convertFormat(duration);
        //set duration on text view
        playerDuration.setText(sDuration);

        //set max on seek bar
        seekBar.setMax(mediaPlayer.getDuration());
        //start handler
        handler.postDelayed(runnable, 0);


        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide play button
                btPlay.setVisibility(View.GONE);
                //Show pause button
                btPause.setVisibility(View.VISIBLE);
                //Start media player
                mediaPlayer.start();
                //set max on seek bar
                seekBar.setMax(mediaPlayer.getDuration());
                //start handler
                handler.postDelayed(runnable, 0);
            }
        });

        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide pause button
                btPause.setVisibility(View.GONE);
                //Show play button
                btPlay.setVisibility(View.VISIBLE);
                //Pause media player
                mediaPlayer.pause();
                //stop handler
                handler.removeCallbacks(runnable);
            }
        });

       /* btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current position of media player
                int currentPosition = mediaPlayer.getCurrentPosition();
                //get duration of media player
                int duration = mediaPlayer.getDuration();
                //check condition
                if(mediaPlayer.isPlaying() && duration!= currentPosition){
                    //when media is playing and duration is not equal to current position
                    //Fast forward 5 seconds
                    currentPosition = currentPosition + 5000;
                    //set current position on text view
                    playerPosition.setText(convertFormat(currentPosition));
                    //set progress on seek bar
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current position of media player
                int currentPosition = mediaPlayer.getCurrentPosition();
                //check condition
                if(mediaPlayer.isPlaying() && currentPosition>5000){
                    //when media is playing and current position is greater than 5 seconds
                    //rewind to 5 seconds
                    currentPosition = currentPosition - 5000;
                    //set current position on text view
                    playerPosition.setText(convertFormat(currentPosition));
                    //set progress on seek bar
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        */

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    //when drag the seek bar
                    //set progress on seekbar
                    mediaPlayer.seekTo(progress);
                }
                //set current position on text view
                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Hide pause button
                btPause.setVisibility(View.GONE);
                //show play button
                btPlay.setVisibility(View.VISIBLE);
                //set media player to initial position
                mediaPlayer.seekTo(0);
            }
        });
    }

    @SuppressLint("Defaultlocale")
    private String convertFormat(int duration){
        return String.format("%02d:%02d"
                , TimeUnit.MILLISECONDS.toMinutes(duration)
                , TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}

