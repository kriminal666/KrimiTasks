package com.kriminal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import com.kriminal.api.Utils;
import com.kriminal.database.Task;
import com.kriminal.database.TasksDAO;
import com.kriminal.main.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetail extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Controls
    private EditText taskTitle;
    private EditText taskDescription;
    private EditText taskDate;
    private EditText taskTime;
    private Button btnUpdate;
    private Vibrator vibe;
    private TasksDAO taskDao;
    private String action;
    private int task_id;
    private TextInputLayout title_layout;
    private TextInputLayout description_layout;
    private TextInputLayout date_layout;
    private TextInputLayout time_layout;


    public TaskDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskDetail newInstance(String param1, String param2) {
        TaskDetail fragment = new TaskDetail();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        //Get data sended
        Bundle extras = getArguments();

        if (extras != null){
            action = extras.getString(Utils.ACTION);
            task_id = extras.getInt(Utils.ID);

        }
        //Change title
        getActivity().setTitle("Hello");

        //Get DAO object
        taskDao = new TasksDAO(getActivity());
        //Vibrator
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;

        //Get controls
        title_layout = (TextInputLayout)view.findViewById(R.id.title_text_input_layout);
        description_layout = (TextInputLayout)view.findViewById(R.id.description_text_input_layout);
        date_layout = (TextInputLayout) view.findViewById(R.id.date_text_input_layout);
        time_layout = (TextInputLayout) view.findViewById(R.id.time_text_input_layout);
        taskTitle = (EditText) view.findViewById(R.id.task_title);
        taskDescription = (EditText) view.findViewById(R.id.task_description);
        taskDate = (EditText) view.findViewById(R.id.task_date);
        taskTime = (EditText) view.findViewById(R.id.task_time);
        btnUpdate = (Button) view.findViewById(R.id.btn_update_task);
        Log.v(Utils.TAG, "before check button");
        if (btnUpdate!=null){
            switch (action){
                case Utils.ACTION_INSERT :
                    btnUpdate.setText(R.string.save);
                    break;
                case Utils.ACTION_UPDATE :
                    btnUpdate.setText(R.string.update);
                    break;
            }

            btnUpdate.setOnClickListener(this);
        }

        //disable layout errors
        Utils.disableInputAdvise(title_layout, taskTitle);
        Utils.disableInputAdvise(description_layout, taskDescription);
        Utils.disableInputAdvise(date_layout, taskDate);
        Utils.disableInputAdvise(time_layout, taskTime);


        //Set new menu
        setHasOptionsMenu(true);

        return view;

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
        inflater.inflate(R.menu.detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


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
        Log.v(Utils.TAG, "onAttach");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        Log.v(Utils.TAG, "Onclick");
        //Vibrate on click
        vibe.vibrate(60); // 60 is time in ms
        //Get Button
        switch(v.getId()){
            case R.id.btn_update_task:

                insertUpdateTask();
                break;

        }

    }



    //Insert or update task
    private void insertUpdateTask() {

        //Get data
        Task task = setTask();

        if (task == null) return;

        switch(action){
            case Utils.ACTION_INSERT:
                if(taskDao.insertTask(task)){
                    Toast.makeText(getActivity(),R.string.inserted, Toast.LENGTH_SHORT).show();
                }else{
                    return;
                }
                break;
            case Utils.ACTION_UPDATE:
                if(taskDao.updateTask(task)){
                    Toast.makeText(getActivity(),R.string.updated, Toast.LENGTH_SHORT).show();
                }else{
                    return;
                }
                break;

        }




    }

    //set task values
    private Task setTask() {

        //Check fields
        if(!checkTaskValues()) return null;

        Task task = new Task();
        task.setTitle(taskTitle.getText().toString());
        task.setDescription(taskDescription.getText().toString());
        task.setDate(taskDate.getText().toString());
        task.setTime(taskTime.getText().toString());

        return task;
    }

    //Check if fields are not empty
    private boolean checkTaskValues(){

        if(Utils.isEmptyEditText(taskTitle)) {

            Utils.setInputAdvise(taskTitle,title_layout,getResources().getString(R.string.input_error_Title));

            return false;
        }

        if (Utils.isEmptyEditText(taskDescription)){

            Utils.setInputAdvise(taskDescription,description_layout,getResources().getString(R.string.input_errorDescription));

            return false;
        }

        if (Utils.isEmptyEditText(taskDate)){

            Utils.setInputAdvise(taskDate,date_layout,getResources().getString(R.string.input_error_date));

            return false;
        }

        if (Utils.isEmptyEditText(taskTime)){

            Utils.setInputAdvise(taskTime,time_layout,getResources().getString(R.string.input_error_Time));

            return false;
        }

        return true;

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
}
