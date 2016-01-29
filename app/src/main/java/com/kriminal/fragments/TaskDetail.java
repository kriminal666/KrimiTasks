package com.kriminal.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.kriminal.dateslider.DateSlider;
import com.kriminal.dateslider.DefaultDateSlider;
import com.kriminal.helpers.SetTime;
import com.kriminal.helpers.Utils;
import com.kriminal.database.Task;
import com.kriminal.database.TasksDAO;
import com.kriminal.main_activity.R;
import com.kriminal.preferences.GetPreferences;
import com.kriminal.sweet_alert.SweetAlert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    private EditText mTaskTitle;
    private EditText mTaskDescription;
    private EditText mTaskDate;
    private EditText mTaskTime;
    private Button mBtnUpdate;
    private Vibrator mVibe;
    private TasksDAO mTaskDao;
    private String mAction;
    private int mTask_id;
    private TextInputLayout mTitle_layout;
    private TextInputLayout mDescription_layout;
    private TextInputLayout mDate_layout;
    private TextInputLayout mTime_layout;
    private Calendar myCalendar;
    private GetPreferences mPreferences;
    private boolean mVibrator;

    // define the listener which is called once a user selected the date.
    private DateSlider.OnDateSetListener mDateSetListener =
            new DateSlider.OnDateSetListener() {
                public void onDateSet(DateSlider view, Calendar selectedDate) {
                    if (mTaskDate != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(Utils.DATE_FORMAT);
                        mTaskDate.setText(sdf.format(selectedDate.getTime()));
                    }
                }
            };

    // define the listener which is called once a user selected the Time.
    private DateSlider.OnDateSetListener mTimeSetListener =
            new DateSlider.OnDateSetListener() {
                public void onDateSet(DateSlider view, Calendar selectedDate) {
                    // update the dateText view with the corresponding date
                    if(mTaskTime != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(Utils.TIME_FORMAT);
                        mTaskTime.setText(sdf.format(selectedDate.getTime()));
                    }
                }
            };


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

    //Set de Title
    private void setTitleResourceId() {

        int title = 0;

        switch(mAction){
            case Utils.ACTION_UPDATE:
                title = R.string.title_actionBUpdateTask;
                break;
            case Utils.ACTION_INSERT:
                title = R.string.title_actionBCreateTask;
                break;
            default:
                title = R.string.drawerTitle;

        }
        getActivity().setTitle(title);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        //Get data sended
        Bundle extras = getArguments();

        if (extras != null) {
            mAction = extras.getString(Utils.ACTION);
            mTask_id = extras.getInt(Utils.ID);
        }
        //Change title
        setTitleResourceId();

        //Get DAO object
        mTaskDao = new TasksDAO(getActivity());
        //Get mPreferences
        mPreferences = new GetPreferences(getActivity());
        mVibrator = mPreferences.buttonsVibration();
        //Vibrator
        mVibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        //Get controls
        mTitle_layout = (TextInputLayout) view.findViewById(R.id.title_text_input_layout);
        mDescription_layout = (TextInputLayout) view.findViewById(R.id.description_text_input_layout);
        mDate_layout = (TextInputLayout) view.findViewById(R.id.date_text_input_layout);
        mTime_layout = (TextInputLayout) view.findViewById(R.id.time_text_input_layout);
        mTaskTitle = (EditText) view.findViewById(R.id.task_title);
        mTaskDescription = (EditText) view.findViewById(R.id.task_description);
        mTaskDate = (EditText) view.findViewById(R.id.task_date);
        mTaskTime = (EditText) view.findViewById(R.id.task_time);
        mBtnUpdate = (Button) view.findViewById(R.id.btn_update_task);
        Log.d(Utils.TAG, "before check button");
        if (mBtnUpdate != null) {
            updateFields();
            mBtnUpdate.setOnClickListener(this);
        }

        //disable layout errors
        Utils.disableInputAdvise(mTitle_layout, mTaskTitle,getActivity());
        Utils.disableInputAdvise(mDescription_layout, mTaskDescription,getActivity());


        //Disable copy paste
        //Disable Keyboard
        mTaskDate.setInputType(InputType.TYPE_NULL);
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(mTaskDate.getWindowToken(), 0);
        mTaskTime.setInputType(InputType.TYPE_NULL);
        im.hideSoftInputFromWindow(mTaskTime.getWindowToken(), 0);


        //set calendar
        myCalendar = Calendar.getInstance();
        /*final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };*/


        mTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVibrator) {
                    mVibe.vibrate(60); // 60 is time in ms
                }


                Utils.disableKeyboard(mTaskDate, getActivity());

                //Disable layout errors if enabled
                if (mDate_layout.isErrorEnabled()) {
                    mDate_layout.setErrorEnabled(false);
                }

               /* new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/

                new DefaultDateSlider(getActivity(), mDateSetListener,myCalendar).show();

            }
        });

        //Set timer
        new SetTime(mTaskTime, getActivity(), mTime_layout, mVibe,mVibrator,mTimeSetListener);


        //Set new menu
        setHasOptionsMenu(true);



        return view;

    }

    /**
     * Set fields data when updating or going to insert new one
     */
    private void updateFields() {

        //When insert
        if(mAction.equals(Utils.ACTION_INSERT)){
            //Set button text
            mBtnUpdate.setText(R.string.save);

            //Set date and time fields
            mTaskTime.setText(Utils.getCurrentTime());
            mTaskDate.setText(Utils.getCurrentDate());

        }else{
        //When update
            //Set button text
            mBtnUpdate.setText(R.string.save);

            //Get task from database
            ArrayList<Task> taskList = mTaskDao.select(mTask_id);
            if(taskList==null){
                SweetAlert.titleWithContent(getActivity(),getActivity().getResources().getString(R.string.title_info),getActivity().getResources()
                .getString(R.string.noTasks)).show();
                return;
            }

            //Set fields
            mTaskTitle.setText(taskList.get(0).getTitle());
            mTaskDescription.setText(taskList.get(0).getDescription());
            mTaskDate.setText(taskList.get(0).getDate());
            mTaskTime.setText(taskList.get(0).getTime());

        }

    }

    //Change date in EditText
    private void updateDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(Utils.DATE_FORMAT);
        if(mTaskDate !=null) {
            mTaskDate.setText(sdf.format(myCalendar.getTime()));
        }

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
        Log.d(Utils.TAG, "onAttach");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        Log.d(Utils.TAG, "Onclick");
        //Vibrate on click
        if (mVibrator) {
            mVibe.vibrate(60); // 60 is time in ms
        }
        //Get Button
        switch (v.getId()) {
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

        switch (mAction) {
            case Utils.ACTION_INSERT:

                if (mTaskDao.insertTask(task)) {
                    SweetAlert.successMessage(getActivity(),getActivity().getResources().getString(R.string.success_title),
                            getActivity().getResources().getString(R.string.inserted)).show();
                    goBack();
                } else {
                    return;
                }
                break;
            case Utils.ACTION_UPDATE:
                if (mTaskDao.updateTask(task)) {
                    SweetAlert.successMessage(getActivity(),getActivity().getResources().getString(R.string.success_title),
                            getActivity().getResources().getString(R.string.updated)).show();
                    goBack();
                } else {
                    return;
                }
                break;

        }


    }

    /**
     * Go back
     */
    private void goBack(){
        getActivity().onBackPressed();
    }

    //set task values
    private Task setTask() {

        //Check fields
        if (!checkTaskValues()) return null;

        Task task = new Task();

        if (mAction.equals(Utils.ACTION_UPDATE)){

            task.setId(mTask_id);
        }
        task.setTitle(mTaskTitle.getText().toString());
        task.setDescription(mTaskDescription.getText().toString());
        task.setDate(Utils.parseDateToStore(mTaskDate.getText().toString()));
        task.setTime(mTaskTime.getText().toString());

        return task;
    }

    //Check if fields are not empty
    private boolean checkTaskValues() {

        if (Utils.isEmptyEditText(mTaskTitle)) {

            Utils.setInputAdvise(mTaskTitle, mTitle_layout, getResources().getString(R.string.input_error_Title));

            return false;
        }

        if (Utils.isEmptyEditText(mTaskDescription)) {

            Utils.setInputAdvise(mTaskDescription, mDescription_layout, getResources().getString(R.string.input_errorDescription));

            return false;
        }

        if (Utils.isEmptyEditText(mTaskDate)) {

            Utils.setInputAdvise(mTaskDate, mDate_layout, getResources().getString(R.string.input_error_date));

            return false;
        }

        if (Utils.isEmptyEditText(mTaskTime)) {

            Utils.setInputAdvise(mTaskTime, mTime_layout, getResources().getString(R.string.input_error_Time));

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
