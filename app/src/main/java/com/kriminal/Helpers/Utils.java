package com.kriminal.Helpers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * Created by Kriminal on 12/01/2016.
 */
public class Utils {

    public static final String TAG = "TAG";
    public static final String SELECT_ALL ="select_all";
    public static final String SELECT_ONE = "select_one";
    public static final String SELECT_FINISH = "select_finish";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION = "action" ;
    public static final String ID = "id";

    /**
     * Check if Edit text is empty
     * @param editText
     * @return true if empty, false if filled
     */
    public static boolean isEmptyEditText(EditText editText){

        if (editText.getText().toString().length() == 0){
            return true;
        }

        return false;

    }


    /**
     * Set input advises
     *
     * @param editText
     * @param layout
     * @param advise
     */
    public static void setInputAdvise(EditText editText, TextInputLayout layout, String advise){

        layout.setErrorEnabled(true);
        layout.setError(advise);
        //editText.getBackground().setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Disable Errors when focus and close keyboard on focus lost
     *
     * @param layout
     * @param editText
     */
    public static void disableInputAdvise(final TextInputLayout layout, final EditText editText, final Context context){


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (layout.isErrorEnabled()) {

                        layout.setErrorEnabled(false);
                    }

                }else {
                    disableKeyboard(editText,context);
                }
            }
        });
    }

    /**
     * Disable keyboard on editText focus
     *
     * @param editText
     * @param ctx
     */
    public static void disableKeyboard(EditText editText, Context ctx){
        //Disable keyboard
        InputMethodManager im = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }



}
