package com.example.tictactoegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button btnPlayerVCpu = findViewById(R.id.btn_player_vs_cpu);
        final Button btnPlayerVPlayer = findViewById(R.id.btn_player_vs_player);

        btnPlayerVCpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, AddPVC.class);
                startActivity(intent);
            }
        });

        btnPlayerVPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, AddPlayers.class);
                startActivity(intent);
            }
        });
    }
}
