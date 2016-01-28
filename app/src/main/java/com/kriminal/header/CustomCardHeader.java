package com.kriminal.header;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kriminal.helpers.Utils;
import com.kriminal.main_activity.R;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by Kriminal on 18/01/2016.
 ********************************************************************************
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
 *
 */
public class CustomCardHeader extends CardHeader {

    private String title;
    private String date ;
    private String time;
    private int color;



    public CustomCardHeader(Context context) {
        super(context, R.layout.card_header_inner_content);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        Log.d(Utils.TAG, "override de custom header");

        if (view!=null){
            //Get controls
             TextView inner_title= (TextView) view.findViewById(R.id.header_title);
             TextView inner_date = (TextView) view.findViewById(R.id.header_data);
            TextView inner_time = (TextView) view.findViewById(R.id.header_time);
            //Set controls
            if(inner_title != null){
                inner_title.setText(this.title);
            }
            if(inner_date != null){
                inner_date.setText(this.date);
            }
            if(inner_time != null){
                inner_time.setText(this.time);
            }



        }
        parent.setBackgroundColor(this.color);
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int colour) {
        this.color = colour;
    }
}
