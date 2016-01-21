package com.kriminal.helpers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by kriminal on 15/01/2016.
 *  * *******************************************************************************
 * Copyright (c) 2016 kriminal666.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
public class SetTime implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context context;
    private TextInputLayout inputLayout;
    private Vibrator vibe;

    /**
     * Constructor
     *
     * @param editText
     * @param context
     * @param inputLayout if not exists send null
     */
    public SetTime(EditText editText, Context context, TextInputLayout inputLayout,Vibrator vibe){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.myCalendar = Calendar.getInstance();
        this.context = context;
        this.inputLayout = inputLayout;
        this.vibe = vibe;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        vibe.vibrate(60);
            //Disable layout errors
            if(this.inputLayout != null && inputLayout.isErrorEnabled()) {
                this.inputLayout.setErrorEnabled(false);
            }
            //Disable Keyboard
            Utils.disableKeyboard(editText,context);

            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);
            new TimePickerDialog(context, this, hour, minute, true).show();
        }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        if(minute <10){
            this.editText.setText(hourOfDay + ":0" + minute);
        }else {
            this.editText.setText(hourOfDay + ":" + minute);
        }
    }

}
