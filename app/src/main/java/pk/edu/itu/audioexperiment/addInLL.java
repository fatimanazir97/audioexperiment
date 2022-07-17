package pk.edu.itu.audioexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class addInLL extends AppCompatActivity {

    LinearLayout ll;

    @Override
    protected void onResume() {
        super.onResume();
        ll.removeAllViews();

        //code for reading data from internal storage

        runTimeBomb(ll);

        /*
        for(int i = 0; i <3 ;i++){
            TextView tv = new TextView(getApplicationContext());
            tv.setText("noteTitle");
            tv.setTextSize(24);
            tv.setPadding(10,10,10,10);
            tv.setTextColor(Color.BLACK);
            ll.addView(tv);
        }

         */

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in_ll);
        ll = findViewById(R.id.ll1);

        try {

            //***************EXTERNAL FILE************************
            File external = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File doc = new File(external, "database.txt");

                try {
                    FileInputStream fin = new FileInputStream(doc);
                    //empty array
                    byte[] byt = new byte[5000];
                    //count for how much data have been read
                    int count = fin.read(byt);

                    String data = new String(byt, 0, count);
                    JSONObject obj = new JSONObject(data);

                    Log.d("EEE", ""+data);
                    //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();

                    if (obj.has("ranges")) {
                        JSONArray ranges = obj.getJSONArray("ranges");

                    }

                } catch (Exception e) {
                    Log.d("EEE", "EXCEPTION");
                    e.printStackTrace();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        

    }

    public void runTimeBomb(View view){
        LayoutInflater mylayoutInflater = getLayoutInflater();
       // for(int i= 0; i < 2;i++) {
        View v1 = mylayoutInflater.inflate(R.layout.interestingspots,null);
        View v2 = mylayoutInflater.inflate(R.layout.interestingspots,null);
        TextView tv1= (TextView) v1.findViewById(R.id.is_name);
        TextView tv2= (TextView) v2.findViewById(R.id.is_name);
        ImageView img1= (ImageView) v1.findViewById(R.id.is_image);
        ImageView img2= (ImageView) v2.findViewById(R.id.is_image);
        tv1.setText("spot 1");
        tv2.setText("spot 2");
        img1.setImageResource(R.drawable.fountain);
        img2.setImageResource(R.drawable.spot);

            ll.addView(v1);
            ll.addView(v2);
       // }
    }
}