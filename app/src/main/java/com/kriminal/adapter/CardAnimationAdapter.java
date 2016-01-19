package com.kriminal.adapter;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import it.gmariotti.cardslib.library.view.CardListView;

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
 *
 */
public class CardAnimationAdapter {





    /**
     * Alpha animation
     */
    public static AnimationAdapter setAlphaAdapter(MyCardArrayMultiChoiceAdapter adapter, CardListView listView) {
        AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(listView);
        return animCardArrayAdapter;

    }



    /**
     * Left animation
     */
    public static AnimationAdapter setLeftAdapter(MyCardArrayMultiChoiceAdapter adapter, CardListView listView) {
        AnimationAdapter animCardArrayAdapter = new SwingLeftInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(listView);
        return animCardArrayAdapter;

    }

    /**
     * Right animation
     */
    public static AnimationAdapter setRightAdapter(MyCardArrayMultiChoiceAdapter adapter, CardListView listView) {
        AnimationAdapter animCardArrayAdapter = new SwingRightInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(listView);
        return animCardArrayAdapter;
    }

    /**
     * Bottom animation
     */
    public static AnimationAdapter setBottomAdapter(MyCardArrayMultiChoiceAdapter adapter, CardListView listView) {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(listView);
        return animCardArrayAdapter;
    }

    /**
     * Bottom-right animation
     */
    public static AnimationAdapter setBottomRightAdapter(MyCardArrayMultiChoiceAdapter adapter, CardListView listView) {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(new SwingRightInAnimationAdapter(adapter));
        animCardArrayAdapter.setAbsListView(listView);
        return animCardArrayAdapter;
    }

    /**
     * Scale animation
     */
    public static AnimationAdapter setScaleAdapter(MyCardArrayMultiChoiceAdapter adapter, CardListView listView) {
        AnimationAdapter animCardArrayAdapter = new ScaleInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(listView);
        return animCardArrayAdapter;
    }



}
