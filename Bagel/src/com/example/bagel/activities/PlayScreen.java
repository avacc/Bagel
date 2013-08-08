package com.example.bagel.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.bagel.R;

public class PlayScreen extends Activity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_screen);
    }
}