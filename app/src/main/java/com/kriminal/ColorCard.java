package com.kriminal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kriminal.main_activity.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Kriminal on 27/01/2016.
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
public class ColorCard extends Card {

    protected String mTitle;
    protected String mSubtitle;
    protected int mCount;
    protected int color;


    public ColorCard(Context context) {
        this(context, R.layout.color_card_inner_base);
    }

    public ColorCard(Context context, int innerLayout) {

        super(context, innerLayout);

    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {


        //Retrieve elements
        TextView title = (TextView) parent.findViewById(R.id.color_card_inner_title);
        TextView subtitle = (TextView) parent.findViewById(R.id.color_card_inner_subtitle);

        if (title != null) {
            title.setText(mTitle);
        }
        if (subtitle != null){
            subtitle.setText((mSubtitle));
        }
        parent.setBackgroundColor(color);



    }



    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;

    }

}
