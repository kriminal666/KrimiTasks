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
    public static final String SELECT_ALL ="select_all";
    public static final String SELECT_ONE = "select_one";
    public static final String SELECT_FINISH = "select_finish";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION = "action" ;
    public static final String ID = "id";

    //prefs keys
    public static final String PREF_SWITCH_THUMBNAIL = "switch_card_thumbnail";
    public static final String PREF_CARD_ANIMATION = "card_load_animation_list";

    //animation values
    public static final String ANIM_ALPHA = "1001";
    public static final String ANIM_LEFT = "1002";
    public static final String ANIM_RIGHT = "1003";
    public static final String ANIM_BOTTOM = "1004";
    public static final String ANIM_BOTTOM_RIGHT = "1005";
    public static final String ANIM_SCALE= "1006";


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
