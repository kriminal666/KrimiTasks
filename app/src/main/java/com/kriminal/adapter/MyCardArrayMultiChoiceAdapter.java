package com.kriminal.adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Vibrator;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.kriminal.fragments.TasksView;
import com.kriminal.main.R;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayMultiChoiceAdapter;
import it.gmariotti.cardslib.library.view.base.CardViewWrapper;

/**
 * Created by kriminal on 12/01/2016.
 *
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

 **/
public class MyCardArrayMultiChoiceAdapter extends CardArrayMultiChoiceAdapter {

    private Vibrator vibe;



    public MyCardArrayMultiChoiceAdapter(Context context, List<Card> cards) {
        super(context, cards);
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE) ;

    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        //It is very important to call the super method
        super.onCreateActionMode(mode, menu);
       // mActionMode=mode; // to manage mode in your Fragment/Activity
        //If you would like to inflate your menu
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.carddemo_multichoice, menu);
        return true;
    }
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            Toast.makeText(getContext(), "Share;" + formatCheckedCard(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.menu_discard) {
            vibe.vibrate(60); // 60 is time in ms
            discardSelectedItems(mode);
            return true;
        }
        return false;
    }
    private void discardSelectedItems(ActionMode mode) {
        ArrayList<Card> items = getSelectedCards();
        for (Card item : items) {
         //We call mark as finished method and we delete the card from array
            TasksView.markFinished(Integer.parseInt(item.getId()));
            remove(item);
        }
        mode.finish();
    }
    private String formatCheckedCard() {
        SparseBooleanArray checked = mCardListView.getCheckedItemPositions();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i) == true) {
                sb.append("\nPosition=" + checked.keyAt(i));
            }
        }
        return sb.toString();
    }
    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b, CardViewWrapper cardViewWrapper, Card card) {
    //Do something here if we want when card is selected in multichoice
    }
}
