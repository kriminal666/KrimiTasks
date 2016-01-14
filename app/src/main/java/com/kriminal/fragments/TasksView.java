package com.kriminal.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Vibrator;
import android.widget.Toast;


import com.kriminal.adapter.MyCardArrayMultiChoiceAdapter;
import com.kriminal.api.Utils;
import com.kriminal.database.SQLiteHelper;
import com.kriminal.main.R;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

/**
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



    //CARDS
    private MyCardArrayMultiChoiceAdapter mMyCardArrayMultiChoiceAdapter;
    private Vibrator vibe;

    //DataBase
    private ArrayList<String> queryResult = new ArrayList<String>();
    private SQLiteHelper sqlHelper;



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
       updateDisplay();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myContext = getActivity().getBaseContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks_view, container, false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
        ArrayList<Card> cardList = new ArrayList<Card>();
        //Get the list view
         CardListView tasksView = (CardListView)getActivity().findViewById(R.id.tasksList);
        //Fill 20 example cards
        for (int i = 0; i<20; i++){
            Card taskCard = new Card(getActivity());
            taskCard.setId(String.valueOf(i));
            CardHeader header = new CardHeader(getActivity());
            header.setTitle("Task " + i);
            taskCard.addCardHeader(header);
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
        mMyCardArrayMultiChoiceAdapter = new MyCardArrayMultiChoiceAdapter(getActivity(),cardList);
        mMyCardArrayMultiChoiceAdapter.setEnableUndo(true);
        if (tasksView != null){

            tasksView.setAdapter(mMyCardArrayMultiChoiceAdapter);
            tasksView.setChoiceMode(tasksView.CHOICE_MODE_MULTIPLE_MODAL);

        }
        

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
