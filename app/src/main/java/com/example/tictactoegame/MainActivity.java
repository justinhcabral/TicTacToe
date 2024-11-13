package com.example.tictactoegame;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<int[]> combinationsList = new ArrayList<>();
    private int [] winningCombination = null;

    private int [] boxPositions = {0,0,0,0,0,0,0,0,0};

    private int playerTurn;
    private int totalSelectedBoxes = 1;
    private LinearLayout playerOneLayout, playerTwoLayout;
    private TextView playerOneName, playerTwoName;

    private Drawable playerOneSym;
    private Drawable playerTwoSym;
    private ImageView[] imageViews = new ImageView[9];
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean imageSwap = getIntent().getBooleanExtra("imageSwap", false);

        final Button backBtn = findViewById(R.id.btn_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPlayers.class);
                startActivity(intent);
            }
        });


        final ImageView ivPlayerOne = findViewById(R.id.iv_player1);
        final ImageView ivPlayerTwo = findViewById(R.id.iv_player2);

          playerOneName = findViewById(R.id.tv_player_one_name);
          playerTwoName = findViewById(R.id.tv_player_two_name);
          playerOneLayout = findViewById(R.id.ll_player_one_layout);
          playerTwoLayout = findViewById(R.id.ll_player_two_layout);

        image1 = findViewById(R.id.iv_image1);
        image2 = findViewById(R.id.iv_image2);
        image3 = findViewById(R.id.iv_image3);
        image4 = findViewById(R.id.iv_image4);
        image5 = findViewById(R.id.iv_image5);
        image6 = findViewById(R.id.iv_image6);
        image7 = findViewById(R.id.iv_image7);
        image8 = findViewById(R.id.iv_image8);
        image9 = findViewById(R.id.iv_image9);

          setupBoard();
          randomizeTurn();
          generateCombinations();


        if (imageSwap) {
            ivPlayerOne.setImageResource(R.drawable.circle);
            playerOneSym = ResourcesCompat.getDrawable(getResources(), R.drawable.circle, null);
            ivPlayerTwo.setImageResource(R.drawable.cross);
            playerTwoSym = ResourcesCompat.getDrawable(getResources(), R.drawable.cross, null);
        } else {
            ivPlayerOne.setImageResource(R.drawable.cross);
            playerOneSym = ResourcesCompat.getDrawable(getResources(), R.drawable.cross, null);
            ivPlayerTwo.setImageResource(R.drawable.circle);
            playerTwoSym = ResourcesCompat.getDrawable(getResources(), R.drawable.circle, null);
        }

// TODO: Try to find a way to not hard code the winning combinations, make it so that the program deems it a win as long as three consecutive symbols are connected.
          final String getPlayerOneName = getIntent().getStringExtra("playerOne");
          final String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

          playerOneName.setText(getPlayerOneName);
          playerTwoName.setText(getPlayerTwoName);
    }

    private void setupBoard(){
        ImageView [] imageViewIds = {
                image1, image2, image3,
                image4, image5, image6,
                image7, image8, image9
        };

        for(int i = 0; i < imageViews.length; i++) {
            imageViews[i] = imageViewIds[i];
            final int boxIndex = i;
            imageViews[i].setOnClickListener(v->{
                if(isBoxSelectable(boxIndex)){
                    performAction((ImageView) v, boxIndex);
                }
            });
        }
    }

    private void generateCombinations() {
        // Rows
        for (int i = 0; i < 3; i++) {
            combinationsList.add(new int[]{i * 3, i * 3 + 1, i * 3 + 2});
        }
        // Columns
        for (int i = 0; i < 3; i++) {
            combinationsList.add(new int[]{i, i + 3, i + 6});
        }
        // Diagonals
        combinationsList.add(new int[]{0, 4, 8});
        combinationsList.add(new int[]{2, 4, 6});
    }

    private ImageView getBoxImageViewById(int index) {
        switch (index) {
            case 0: return image1;
            case 1: return image2;
            case 2: return image3;
            case 3: return image4;
            case 4: return image5;
            case 5: return image6;
            case 6: return image7;
            case 7: return image8;
            case 8: return image9;
            default: return null;
        }
    }

    private void performAction(ImageView imageView, int selectedBoxPosition) {
        // Mark the selected box with the current player's symbol
        boxPositions[selectedBoxPosition] = playerTurn;

        // Set the image for the selected box based on the player's symbol
        imageView.setImageDrawable(playerTurn == 1 ? playerOneSym : playerTwoSym);

        // Check if the current player has won
        if (checkPlayerWin()) {

            // Highlight the winning combination
            highlightWinningCombination();

//             Play the win song
            mediaPlayer = MediaPlayer.create(this, R.raw.win_song);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

            // Show the win dialog
            String winner = (playerTurn == 1 ? playerOneName : playerTwoName).getText().toString();
            WinDialog winDialog = new WinDialog(MainActivity.this, winner + " has won the match", new Runnable() {
                @Override
                public void run() {
                    restartMatch(); // Call the method that restarts the game in MainActivity
                }
            });
            winDialog.setCancelable(false);
            winDialog.show();

        } else if (totalSelectedBoxes == 9) {
            // In case of a draw
            WinDialog winDialog = new WinDialog(MainActivity.this, "Draw!!!", new Runnable() {
                @Override
                public void run() {
                    restartMatch(); // Call the method that restarts the game in MainActivity
                }
            });
            winDialog.setCancelable(false);
            winDialog.show();
        } else {
            // Change the player turn and update the total selected boxes
            changePlayerTurn(playerTurn == 1 ? 2 : 1);
            totalSelectedBoxes++;
            Log.d("MainActivity", "Total selected boxes: " + totalSelectedBoxes);
        }
    }

    private void stopWinSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void changePlayerTurn(int currentPlayerTurn){

        playerTurn = currentPlayerTurn;

        if(playerTurn == 1){
            playerOneLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
        } else{
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerOneLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }
    }

    private boolean checkPlayerWin() {
        for (int[] combination : combinationsList) {
            if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {

                winningCombination = combination;
                return true;
            }
        }
        return false;
    }

    private void highlightWinningCombination() {
        // Assume `winningCombination` is populated and non-null
        int winningBoxIndex = winningCombination[0];
        ImageView winningBoxImage = getBoxImageViewById(winningBoxIndex);

        if (winningBoxImage == null) return; // Safeguard against null reference

        // Determine the symbol in the winning box by directly comparing to known drawables
        Drawable winningSymbolDrawable = winningBoxImage.getDrawable();
        Drawable winningHighlight;

        // Check if the winning box contains the cross or circle symbol
        Drawable crossDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.cross, null);
        Drawable circleDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.circle, null);

        if (winningSymbolDrawable != null &&
                winningSymbolDrawable.getConstantState().equals(crossDrawable.getConstantState())) {
            // Winning symbol is cross
            winningHighlight = ResourcesCompat.getDrawable(getResources(), R.drawable.cross_win, null);
        } else if (winningSymbolDrawable != null &&
                winningSymbolDrawable.getConstantState().equals(circleDrawable.getConstantState())) {
            // Winning symbol is circle
            winningHighlight = ResourcesCompat.getDrawable(getResources(), R.drawable.circle_win, null);
        } else {
            // Unexpected case: if the drawable is neither cross nor circle
            return;
        }

        // Apply the winning highlight to each box in the winning combination
        for (int index : winningCombination) {
            ImageView imageView = getBoxImageViewById(index);
            if (imageView != null) {
                imageView.setImageDrawable(winningHighlight); // Set the highlight image
            }
        }
    }

    private void randomizeTurn() {
        Random random = new Random();
        playerTurn = random.nextInt(2) + 1;  // Generates either 1 or 2
    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    void restartMatch() {
        stopWinSong(); // Stop the song if playing
        boxPositions = new int[9]; // Reset the board
        playerTurn = 1; // Reset player turn
        totalSelectedBoxes = 1;
        winningCombination = null;
        boxPositions = new int[9];
        randomizeTurn();
        for (ImageView imageView : imageViews) {
            imageView.setImageResource(R.drawable.transparent_back);
        }
    }
}