package pk.edu.itu.audioexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    }

    public void runTimeBomb(View view){
        LayoutInflater mylayoutInflater = getLayoutInflater();
        View v1 = mylayoutInflater.inflate(R.layout.interestingspots,null);
        ll.addView(v1);
    }
}