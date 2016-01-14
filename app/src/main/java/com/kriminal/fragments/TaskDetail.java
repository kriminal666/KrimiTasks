package com.kriminal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
        //get control
        TextView textView = (TextView) view.findViewById(R.id.detailText);

        // data send from taskView after loading this fragment
        Bundle extras = getArguments();
        if (extras != null) {
            int id = extras.getInt("id");
            String action = extras.getString("ACTION");
            textView.setText("Data send: "+ "id:"+id+" action: "+action);
        }

        //Get DAO object
        taskDao = new TasksDAO(getActivity());


        //Get controls
        taskTitle = (EditText) getActivity().findViewById(R.id.task_title);
        taskDescription = (EditText) getActivity().findViewById(R.id.task_description);
        taskDate = (EditText) getActivity().findViewById(R.id.task_date);
        taskTime = (EditText) getActivity().findViewById(R.id.task_time);
        btnUpdate = (Button) getActivity().findViewById(R.id.btn_update_task);
        if (btnUpdate!=null){

            btnUpdate.setText(R.string.update);
            btnUpdate.setAnimation(new Animation() {
                @Override
                public boolean willChangeTransformationMatrix() {
                    return super.willChangeTransformationMatrix();
                }
            });

            btnUpdate.setOnClickListener(this);
        }


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
        super.onCreateOptionsMenu(menu,inflater);


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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        //Vibrate on click
        vibe.vibrate(60); // 60 is time in ms
        //Get Button
        switch(v.getId()){
            case R.id.btn_update_task:
                insertTask();
        }

    }

    private void insertTask() {
        Task task = new Task();

        task.setTitle(taskTitle.getText().toString());
        task.setDescription(taskDescription.getText().toString());
        task.setDate(taskDate.getText().toString());
        task.setTime(taskTime.getText().toString());

        boolean success = taskDao.insertTask(task);

        Toast.makeText(getActivity(),""+success, Toast.LENGTH_SHORT).show();

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
