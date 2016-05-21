package com.example.user.medicalia.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.medicalia.R;
import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.models.Patient;
import com.example.user.medicalia.models.Schedule;
import com.example.user.medicalia.remote.ScheduleAPI;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ScheduleFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = ScheduleFragment.class.getSimpleName();

    private Patient currentUser;
    private String token;
    private ProgressDialog progressDialog;

    @Bind(R.id.textView_schedule)
    public TextView textView_schedule;

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);

        String jsonUser = getArguments().getString(getString(R.string.user_key), "");
        currentUser = Utils.toUserAtributtes(jsonUser);
        token = getString(R.string.token) + currentUser.getUserAttributes().getToken();

        getSchedule();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getSchedule() {
        showLoadingDialog();
        Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();
        ScheduleAPI.Factory.getInstance().getSchedule(token, "4").enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                int code = response.code();

                switch (code){
                    case 200:
                        Log.d(TAG, String.valueOf(code));
                        Schedule schedule = response.body();
                        textView_schedule.setText(schedule.getStart());

                        break;
                    default:
                        Log.e(TAG, String.valueOf(code));
                        try {
                            Snackbar.make(getActivity().getCurrentFocus(), "Error del Servidor" + response.errorBody().string(), Snackbar.LENGTH_SHORT).show();
                            Log.e(TAG, response.errorBody().string());
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(getActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
    }

    @OnClick(R.id.button_calendar)
    public void changeDay(View view) {
        Toast.makeText(getActivity(), "Hola", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day+"-"+month+"-"+year;
        textView_schedule.setText(date);
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
