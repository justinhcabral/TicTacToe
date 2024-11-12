package com.example.tictactoegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPlayers extends AppCompatActivity {

    private boolean imageSwap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        final EditText playerOne = findViewById(R.id.et_player_one);
        final EditText playerTwo = findViewById(R.id.et_player_two);
        final ImageView ivPlayerOne = findViewById(R.id.iv_player1);
        final ImageView ivPlayerTwo = findViewById(R.id.iv_player2);
        final Button btnSwitchSym = findViewById(R.id.btn_switch_symbols);
        final Button btnStartGame = findViewById(R.id.btn_start_game);
        final Button btnBackToMenu = findViewById(R.id.btn_back_to_menu);

        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlayers.this, Menu.class);
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
                final String getPlayerTwoName = playerTwo.getText().toString();

                if(getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()){
                    Toast.makeText(AddPlayers.this, "Please Enter Your Names", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddPlayers.this, MainActivity.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", getPlayerTwoName);
                    intent.putExtra("imageSwap", imageSwap);
                    startActivity(intent);
                }
            }
        });

    }
}