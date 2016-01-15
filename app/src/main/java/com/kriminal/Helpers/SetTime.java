package com.kriminal.Helpers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by kriminal on 15/01/2016.
 */
public class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context context;
    private TextInputLayout inputLayout;

    /**
     * Constructor
     *
     * @param editText
     * @param context
     * @param inputLayout if not exists send null
     */
    public SetTime(EditText editText, Context context, TextInputLayout inputLayout){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.context = context;
        this.inputLayout = inputLayout;

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        if(hasFocus){
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
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        this.editText.setText( hourOfDay + ":" + minute);
    }

}
