package com.kriminal.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kriminal.helpers.Utils;

/**
 * Created by Kriminal on 19/01/2016.
 * *******************************************************************************
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
public class GetPreferences {



    //Shared data
    private SharedPreferences sharedPrefs;

    /**
     * Constructor. Gets shared preferences object
     * @param context
     */
    public GetPreferences(Context context) {

        if(sharedPrefs ==null) {
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        }

    }

    /**
     * Get if user wants thumbnail in card or not
     *
     * @return
     */
    public boolean cardThumbnail() {
        return sharedPrefs.getBoolean(Utils.PREF_SWITCH_THUMBNAIL, false);
    }

    /**
     * Get user selected card animation
     * @return
     */
    public String cardAnimation() {
        return sharedPrefs.getString(Utils.PREF_CARD_ANIMATION,"");
    }

    /**
     * Get nav header title and subtitle
     * @return String array [0]= title, [1]=subtitle
     */
    public String[] navHeaderTitleAndSubtitle(){

        return new String[] {sharedPrefs.getString(Utils.PREF_NAV_HEADER_TITLE,""),
                             sharedPrefs.getString(Utils.PREF_NAV_HEADER_SUBTITLE,"")};

    }

    /**
     * Get if user wants swipe cards
     * @return
     */
    public boolean cardSwipe(){
        return sharedPrefs.getBoolean(Utils.PREF_SWITCH_CARD_SWIPE,false);
    }

    /**
     * Get user prefs for buttons vibration
     * @return
     */
    public boolean buttonsVibration(){
        return sharedPrefs.getBoolean(Utils.PREF_SWITCH_APP_VIBRATION,false);
    }

    /**
     * Get if user wants colored cards
     * @return
     */
    public boolean coloredCards(){
        return sharedPrefs.getBoolean(Utils.PREF_ACTIVATE_COLOR_CARD,false);

    }

    /**
     * Get Primary background
     * @return
     */
    public int getCardBackground1(){
        return sharedPrefs.getInt(Utils.PREF_CARD_COLOR_BACKGROUND1,0);

    }

    /**
     * Get Secondary background
     * @return
     */
    public int getCardBackground2(){
        return sharedPrefs.getInt(Utils.PREF_CARD_COLOR_BACKGROUND2,0);
    }
}

