package com.kriminal.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;


import com.kriminal.adapter.CardAnimationAdapter;
import com.kriminal.adapter.MyCardArrayMultiChoiceAdapter;
import com.kriminal.database.Task;
import com.kriminal.database.TasksDAO;
import com.kriminal.helpers.Utils;
import com.kriminal.database.SQLiteHelper;
import com.kriminal.header.CustomCardHeader;
import com.kriminal.main_activity.R;
import com.kriminal.preferences.GetPreferences;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

/**
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TasksView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TasksView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static Context myContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Title
    private int title = -1;

    //Shared preferences
    private GetPreferences preferences;



    //CARDS
    private MyCardArrayMultiChoiceAdapter mMyCardArrayMultiChoiceAdapter;
    private Vibrator vibe;

    //DataBase
    private ArrayList<Task> queryResult;
    private TasksDAO taskDAO;
    private ArrayList<Card> cardList;


    public TasksView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksView.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksView newInstance(String param1, String param2) {
        TasksView fragment = new TasksView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public  void onStart(){
        super.onStart();
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
        if(title ==-1) {

            title = R.string.title_actionBTodo;

        }

        //Get preferences object
        preferences = new GetPreferences(getActivity());
        //Get database access object
        taskDAO = new TasksDAO(getActivity());

        setTitle();

        //Set navigation header title and subtitle
        setNavHeaderTitleAndSubtitle();

        //Card array list
        if(cardList == null){
            cardList = new ArrayList<Card>();
        }
        //query result
        if(queryResult == null) {
            queryResult = new ArrayList<Task>();
        }


        updateDisplay();

    }

    /**
     * Set navigation header title and subtitle
     */
    private void setNavHeaderTitleAndSubtitle() {

        String navHeader[] = preferences.navHeaderTitleAndSubtitle();
       TextView navHeaderTitle = (TextView) getActivity().findViewById(R.id.nav_header_title);
       TextView navHeaderSubtitle = (TextView) getActivity().findViewById(R.id.nav_header_subtitle);

       if(navHeaderTitle!= null){
           navHeaderTitle.setText(navHeader[Utils.NAV_HEADER_TITLE]);
       }
       if(navHeaderSubtitle!= null){
           navHeaderSubtitle.setText(navHeader[Utils.NAV_HEADER_SUBTITLE]);
       }

    }

    //Set the title
    private void setTitle() {

      getActivity().setTitle(title);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myContext = getActivity().getBaseContext();

        //Set new menu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks_view, container, false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //We override this method to add own menu for this fragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.todo_tasks:
                title = R.string.title_actionBTodo;
                setTitle();
                //Execute query
                getTasks(Utils.SELECT_ALL_TODO);
                //Update list
                updateDisplay();
                break;
            case R.id.finished_tasks:
                title = R.string.title_actionBFinish;
                setTitle();
                getTasks(Utils.SELECT_ALL_FINISHED);
                updateDisplay();
                break;
            case R.id.all_tasks:
                title = R.string.title_actionBAll;
                setTitle();
                getTasks(Utils.SELECT_ALL_TASKS);
                //Updatelist
                updateDisplay();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Get tasks
     */
    private void getTasks(int typeSelect) {

        if(queryResult != null){
            queryResult.clear();
        }

        queryResult = taskDAO.select(typeSelect);


    }



    /*@Override
    public void onPause() {
        super.onPause();

        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    //Method to fill cardList
    private void updateDisplay() {
        //check query result
       /* if (queryResult == null || queryResult.isEmpty()){
            Snackbar.make(getView(),R.string.noTasks,Snackbar.LENGTH_LONG).show();
            return;

        }*/
        //Clear before fill
        cardList.clear();

        //Get the list view
        CardListView tasksView = (CardListView) getActivity().findViewById(R.id.tasksList);

        //Fill 20 example cards
        for (int i = 0; i < 20; i++) {
            Card taskCard = new Card(getActivity());
            taskCard.setId(String.valueOf(i));
            CustomCardHeader header = new CustomCardHeader(getActivity());
            Log.d(Utils.TAG,"antes de set inner header");
            header.setTitle("Go to the market " + i);
            header.setDate("15/12/2017");
            header.setTime("12:00");

            //Add overflow buttons to card with menu click listener
            header.setPopupMenu(R.menu.card_overflow_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                    vibe.vibrate(60); // 60 is time in ms
                    switch (menuItem.getItemId()) {

                        case (R.id.updateTaskMenu):
                            //Future actions here
                            vibe.vibrate(60);
                            Toast.makeText(getActivity(), "UpdateTask", Toast.LENGTH_SHORT).show();
                            break;
                        case (R.id.deleteTaskMenu):
                            vibe.vibrate(60);
                            Toast.makeText(getActivity(), "DeleteTask", Toast.LENGTH_SHORT).show();
                            //Call the method to delete
                            //deleteTeacher(Integer.valueOf(baseCard.getId()));
                            break;
                    }
                }
            });
            taskCard.addCardHeader(header);

            //Add thumbnail
            if(preferences.cardThumbnail()){
                //Create thumbnail
                CardThumbnail thumb = new CardThumbnail(getActivity());

                //Set resource
                thumb.setDrawableResource(R.drawable.todo1_48);

                //Add thumbnail to a card
                taskCard.addCardThumbnail(thumb);

            }

            //Make card swapeable with undo
            taskCard.setSwipeable(true);
            taskCard.setOnSwipeListener(new Card.OnSwipeListener() {
                @Override
                public void onSwipe(Card card) {
                    //we mark for deletion
                    vibe.vibrate(60); // 60 is time in ms
                    //markForDeletion(card.getId(),"y");

                }
            });
            //swipe undo action
            taskCard.setOnUndoSwipeListListener(new Card.OnUndoSwipeListListener() {
                @Override
                public void onUndoSwipe(Card card) {
                    //we undo the mark for deletion
                    vibe.vibrate(60); // 60 is time in ms
                    //markForDeletion(card.getId(),"n");

                }
            });

            taskCard.setTitle("TASK " + i);
            taskCard.setShadow(true);
            taskCard.setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    vibe.vibrate(60); // 60 is time in ms
                    //Change the fragment to see detail
                    changeFragment(Integer.parseInt(card.getId()));

                }
            });
            //Set on long click listener
            taskCard.setOnLongClickListener(new Card.OnLongCardClickListener() {
                @Override
                public boolean onLongClick(Card card, View view) {
                    vibe.vibrate(60); // 60 is time in ms
                    return mMyCardArrayMultiChoiceAdapter.startActionMode(getActivity());
                }
            });

            //add card to list
            cardList.add(taskCard);

        }

        //Set adapter
        mMyCardArrayMultiChoiceAdapter = new MyCardArrayMultiChoiceAdapter(getActivity(), cardList);
        mMyCardArrayMultiChoiceAdapter.setEnableUndo(true);
        String animation = preferences.cardAnimation();

        AnimationAdapter animationAdapter = getAnimationAdapter(tasksView, animation);
        if (tasksView != null){

            tasksView.setExternalAdapter(animationAdapter,mMyCardArrayMultiChoiceAdapter);
            tasksView.setChoiceMode(tasksView.CHOICE_MODE_MULTIPLE_MODAL);

        }
        

    }

    /**
     * get animation adapter using user prefs
     * @param tasksView
     * @param animation
     * @return
     */
    private AnimationAdapter getAnimationAdapter(CardListView tasksView, String animation) {
        AnimationAdapter animationAdapter;
        switch(animation){
            case Utils.ANIM_ALPHA:
                animationAdapter = CardAnimationAdapter.setAlphaAdapter(mMyCardArrayMultiChoiceAdapter, tasksView);
                break;
            case Utils.ANIM_LEFT:
                animationAdapter = CardAnimationAdapter.setLeftAdapter(mMyCardArrayMultiChoiceAdapter, tasksView);
                break;
            case Utils.ANIM_RIGHT:
                animationAdapter = CardAnimationAdapter.setRightAdapter(mMyCardArrayMultiChoiceAdapter, tasksView);
                break;
            case Utils.ANIM_BOTTOM :
                animationAdapter = CardAnimationAdapter.setBottomAdapter(mMyCardArrayMultiChoiceAdapter, tasksView);
                break;
            case Utils.ANIM_BOTTOM_RIGHT:
                animationAdapter = CardAnimationAdapter.setBottomRightAdapter(mMyCardArrayMultiChoiceAdapter, tasksView);
                break;
            case Utils.ANIM_SCALE :
                animationAdapter = CardAnimationAdapter.setScaleAdapter(mMyCardArrayMultiChoiceAdapter, tasksView);
                break;
            default:
                animationAdapter = CardAnimationAdapter.setBottomRightAdapter(mMyCardArrayMultiChoiceAdapter, tasksView);
        }
        return animationAdapter;
    }

    /**
     * Change fragment to show detail
     * @param id
     */
    private void changeFragment(int id){

        Log.d(Utils.TAG, "changeFragment");

        //Change the fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //set animations
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
        Fragment   taskDetail = new TaskDetail();
        //transaction.hide(TaskView.this);

        transaction.replace(R.id.container,taskDetail);
        transaction.addToBackStack(null);
        transaction.commit();


        //Pass the id to the fragment detail
        Bundle extras = new Bundle();
        extras.putInt(Utils.ID,id);
        extras.putString(Utils.ACTION,Utils.ACTION_UPDATE);
        taskDetail.setArguments(extras);



    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * 
     */
    public static void markFinished(int id){
        Toast.makeText(myContext, "finished tasks", Toast.LENGTH_SHORT).show();

    }

}
