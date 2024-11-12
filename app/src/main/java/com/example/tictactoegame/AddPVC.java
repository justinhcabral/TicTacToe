package com.example.tictactoegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPVC extends AppCompatActivity {

    private boolean imageSwap = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_vs_cpu);

        final EditText playerOne = findViewById(R.id.et_player_one);
        final ImageView ivPlayerOne = findViewById(R.id.iv_player1);
        final ImageView ivPlayerTwo = findViewById(R.id.iv_player2);
        final Button btnSwitchSym = findViewById(R.id.btn_switch_symbols);
        final Button btnStartGame = findViewById(R.id.btn_start_game);
        final Button btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPVC.this, Menu.class);
                startActivity(intent);
            }
        });

        btnSwitchSym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwap = !imageSwap;

                if(imageSwap){
                    ivPlayerOne.setImageResource(R.drawable.circle);
                    ivPlayerTwo.setImageResource(R.drawable.cross);
                } else {
                    ivPlayerOne.setImageResource(R.drawable.cross);
                    ivPlayerTwo.setImageResource(R.drawable.circle);
                }
            }
        });

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getPlayerOneName = playerOne.getText().toString();

                if(getPlayerOneName.isEmpty()){
                    Toast.makeText(AddPVC.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddPVC.this, GameVCPU.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("imageSwap", imageSwap);
                    startActivity(intent);
                }
            }
        });
    }
}
