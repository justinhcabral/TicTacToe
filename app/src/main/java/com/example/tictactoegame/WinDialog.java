package com.example.tictactoegame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialog extends Dialog {

    private final String message;
    private final MainActivity mainActivity;

    public WinDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.win_dialog_layout);

        final TextView messageTxt = findViewById(R.id.tv_message_txt);
        final Button btnStartAgain = findViewById(R.id.btn_start_again);

        messageTxt.setText(message);

        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.restartMatch();
                dismiss();
            }
        });

    }
}
