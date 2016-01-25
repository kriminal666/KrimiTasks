package com.kriminal.helpers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.kriminal.database.TasksDAO;
import com.kriminal.main_activity.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Kriminal on 12/01/2016.
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
public class Utils {

    public static final String TAG = "TAG";
    public static final String SELECTED_ALL_TODO ="select_all_todo";
    public static final String SELECTED_ALL_FINISHED = "select_all_finished";
    public static final String SELECTED_ALL= "select_all";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION = "action" ;
    public static final String ID = "id";
    public static final int INSERT_ID = -1 ;
    public static final String TYPE_OF_LIST_SHOWING = "type_of_list_showing";

    //prefs keys
    public static final String PREF_SWITCH_THUMBNAIL = "switch_card_thumbnail";
    public static final String PREF_CARD_ANIMATION = "card_load_animation_list";
    public static final String PREF_NAV_HEADER_TITLE = "nav_header_title";
    public static final String PREF_NAV_HEADER_SUBTITLE = "nav_header_subtitle";
    public static final String PREF_SWITCH_CARD_SWIPE = "switch_card_swipe";
    public static final String PREF_SWITCH_APP_VIBRATION = "switch_app_vibration";


    //animation values
    public static final String ANIM_ALPHA = "1001";
    public static final String ANIM_LEFT = "1002";
    public static final String ANIM_RIGHT = "1003";
    public static final String ANIM_BOTTOM = "1004";
    public static final String ANIM_BOTTOM_RIGHT = "1005";
    public static final String ANIM_SCALE= "1006";

    //Database selects
    public static final int SELECT_ALL_TODO = 0;
    public static final int SELECT_ALL_FINISHED = -1;
    public static final int SELECT_ALL_TASKS= -2;

    //Titles
    public static final int NAV_HEADER_TITLE = 0;
    public static final int NAV_HEADER_SUBTITLE = 1;

    //FormaTS
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";

    //miscellanious
    public static final String YES = "YES";
    public static final String NO = "NO";

    private static Calendar calendar = Calendar.getInstance();






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

                } else {
                    disableKeyboard(editText, context);
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
        editText.setInputType(InputType.TYPE_NULL);
        InputMethodManager im = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Get current date
     * @return
     */
    public static String getCurrentDate(){

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(calendar.getTime());


    }

    /**
     * Get current Time
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(calendar.getTime());
    }



}
