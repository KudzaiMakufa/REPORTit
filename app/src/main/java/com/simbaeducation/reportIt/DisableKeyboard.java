package com.simbaeducation.reportIt;

import android.os.Build;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class DisableKeyboard {

    public static void disableSoftInputFromAppearing(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = editText.getInputType(); // backup the input type
                editText.setInputType(InputType.TYPE_NULL); // disable soft input
                editText.onTouchEvent(event); // call native handler
                editText.setInputType(inType); // restore input type
                return true; // consume touch event
            }
        });
    }
}