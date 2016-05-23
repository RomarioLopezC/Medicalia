package com.example.user.medicalia.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.medicalia.DrawerActivity;
import com.example.user.medicalia.R;
import com.example.user.medicalia.Utils.DateDialog;
import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.adapter.ScheduleAdapter;
import com.example.user.medicalia.models.Appointment;
import com.example.user.medicalia.models.Day;
import com.example.user.medicalia.models.Patient;
import com.example.user.medicalia.models.Schedule;
import com.example.user.medicalia.models.SpecialDay;
import com.example.user.medicalia.remote.ScheduleAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ScheduleFragment extends Fragment {

    private static final String TAG = ScheduleFragment.class.getSimpleName();

    @Bind(R.id.recycler_view_schedule_day)
    public RecyclerView recycler_view_schedule_day;

    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;
    private Patient currentUser;
    private String token;
    private ProgressDialog progressDialog;
    private Day day;

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);
        drawerActivity = (DrawerActivity) getActivity();

        setToolbar(view);

        getUserData();

        setDay();

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(drawerActivity, day.getHours());
        recycler_view_schedule_day.setHasFixedSize(true);
        recycler_view_schedule_day.setLayoutManager(new LinearLayoutManager(drawerActivity));
        recycler_view_schedule_day.setAdapter(scheduleAdapter);

        return view;
    }

    private void getUserData() {
        String jsonUser = getArguments().getString(getString(R.string.user_key), "");
        currentUser = Utils.toUserAtributtes(jsonUser);
        token = getString(R.string.token) + currentUser.getUserAttributes().getToken();
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_schedule);
        drawerActivity.setSupportActionBar(toolbar);
        drawerActivity.getSupportActionBar().setTitle(getString(R.string.calendar));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                drawerActivity, drawerActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerActivity.drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.show_schedule) {
            changeDay();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void setDay() {
        //fetchSchedule();
        List<SpecialDay> specialDays = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();

        String start = "2000-01-01T17:52:30.579Z";
        String end = "2000-01-01T17:52:30.579Z";
        String start_lunch = "2000-01-01T17:52:30.579Z";
        String end_lunch = "2000-01-01T17:52:30.579Z";

        Schedule schedule = new Schedule(start, end, start_lunch, end_lunch,
                specialDays, appointments);

        day = new Day(schedule);
    }

    private void fetchSchedule() {
        showLoadingDialog();
        Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();
        ScheduleAPI.Factory.getInstance().getSchedule(token, "4").enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                int code = response.code();

                switch (code) {
                    case 200:
                        Log.d(TAG, String.valueOf(code));
                        Schedule schedule = response.body();
                        //textView_schedule.setText(schedule.getStart());

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


    public void changeDay() {
        Toast.makeText(getActivity(), "Hola", Toast.LENGTH_SHORT).show();
        DateDialog dateDialog = new DateDialog();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        dateDialog.show(fragmentTransaction, "DatePicker");
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
