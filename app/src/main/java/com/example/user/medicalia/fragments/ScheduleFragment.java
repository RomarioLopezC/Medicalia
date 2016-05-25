package com.example.user.medicalia.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.DatePicker;

import com.example.user.medicalia.DrawerActivity;
import com.example.user.medicalia.R;
import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.adapter.ScheduleAdapter;
import com.example.user.medicalia.models.Appointment;
import com.example.user.medicalia.models.Day;
import com.example.user.medicalia.models.Hour;
import com.example.user.medicalia.models.Patient;
import com.example.user.medicalia.models.Schedule;
import com.example.user.medicalia.models.SpecialDay;
import com.example.user.medicalia.remote.ScheduleAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ScheduleFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = ScheduleFragment.class.getSimpleName();

    @Bind(R.id.recycler_view_schedule_day)
    public RecyclerView recycler_view_schedule_day;

    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;
    private String token;
    private ProgressDialog progressDialog;
    private Day normalDay;
    private Schedule normalSchedule;
    private Calendar today;

    private List<SpecialDay> specialDays;
    private List<Appointment> appointments;
    private Patient currentUser;

    private DatePickerDialog datePickerDialog;

    private OnFragmentInteractionListener mListener;
    private ScheduleAdapter scheduleAdapter;

    public ScheduleFragment() {
        // Required empty public constructor
        today = Calendar.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);
        drawerActivity = (DrawerActivity) getActivity();

        String jsonCurrentUser = drawerActivity.getSharedPreferences(getString(R.string.name_shared_preferences), Context.MODE_APPEND)
                .getString(getString(R.string.current_user_key), "{\n" +
                        "  \"blood_type\": \"B++\",\n" +
                        "  \"birthday\": \"9999-12-12\",\n" +
                        "  \"height\": 1,\n" +
                        "  \"weight\": 69,\n" +
                        "  \"allergies\": \"none\",\n" +
                        "  \"gender\": \"macho\",\n" +
                        "  \"user\": {\n" +
                        "    \"email\": \"test@hotmail.com\",\n" +
                        "    \"name\": \"Test\",\n" +
                        "    \"lastname\": \"Perez\",\n" +
                        "    \"mobile\": \"9993939393\",\n" +
                        "    \"token\": \"22c07aa8cca26a484b707e363dd90f3d\",\n" +
                        "    \"user_type\": null\n" +
                        "  }\n" +
                        "}");

        currentUser = Utils.toUserAtributtes(jsonCurrentUser);

        setToolbar(view);

        getUserData();

        setDay();

        scheduleAdapter = new ScheduleAdapter(drawerActivity, new ArrayList<Hour>());
        recycler_view_schedule_day.setHasFixedSize(true);
        recycler_view_schedule_day.setLayoutManager(new LinearLayoutManager(drawerActivity));
        recycler_view_schedule_day.setAdapter(scheduleAdapter);

        return view;
    }

    private void getUserData() {


        String jsonUser = getArguments().getString(getString(R.string.user_key), null);
        if (jsonUser == null){
            token = getString(R.string.token) + currentUser.getUserAttributes().getToken();
        }else{
            currentUser = Utils.toUserAtributtes(jsonUser);
            token = getString(R.string.token) + currentUser.getUserAttributes().getToken();
        }

    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_schedule);
        drawerActivity.setSupportActionBar(toolbar);

        drawerActivity.getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                drawerActivity, drawerActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerActivity.drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setTitle(int year, int month, int day) {
        String mes = "";
        switch (month) {
            case 0:
                mes = "Enero";
                break;
            case 1:
                mes = "Febrero";
                break;
            case 2:
                mes = "Marzo";
                break;
            case 3:
                mes = "Abril";
                break;
            case 4:
                mes = "Mayo";
                break;
            case 5:
                mes = "Junio";
                break;
            case 6:
                mes = "Julio";
                break;
            case 7:
                mes = "Agosto";
                break;
            case 8:
                mes = "Septiembre";
                break;
            case 9:
                mes = "Octubre";
                break;
            case 10:
                mes = "Noviembre";
                break;
            case 11:
                mes = "Diciembre";
        }

        String title = day + " de " + mes;
        drawerActivity.getSupportActionBar().setTitle(title);
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
        fetchSchedule();
    }

    private void fetchSchedule() {
        showLoadingDialog();
        Log.d("Token", token);
        String doctorID = "1";

        if (getArguments().get("DoctorId") != null){
            doctorID = String.valueOf(getArguments().get("DoctorId"));
        }

        Log.d("Token", doctorID);
        ScheduleAPI.Factory.getInstance().getSchedule(token, doctorID).enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                int code = response.code();

                switch (code) {
                    case 200:
                        Log.d(TAG, String.valueOf(code));
                        normalSchedule = response.body();

                        specialDays = normalSchedule.getSpecialDays();
                        appointments = normalSchedule.getAppointments();

                        normalDay = new Day(normalSchedule);

                        drawDayList(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));

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
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 1000);
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        drawDayList(year, month, day);
        datePickerDialog.dismiss();
    }

    private void drawDayList(int year, int month, int day) {
        progressDialog.show();

        List<Appointment> todayAppointments = new ArrayList<>();
        Schedule schedule = normalSchedule;
        today.set(year, month, day);

        // Se escogen las citas del dia del hoy
        for (Appointment appointment : appointments) {
            Calendar calendarAppointment = appointment.getStartTimeCalendar();

            if (isTheSameDay(calendarAppointment)) {
                todayAppointments.add(appointment);
            }

        }

        // Se modifica el horario para los dias especiales
        for (SpecialDay specialDay : specialDays) {
            Calendar otherCalendar = specialDay.getDayCalendar();

            if (isTheSameDay(otherCalendar)) {

                schedule = new Schedule(specialDay.getStart(),
                        specialDay.getEnd(),
                        specialDay.getStartLunch(),
                        specialDay.getEndLunch(), null, null);

            }

        }

        schedule.setAppointments(todayAppointments);

        Day newDay = new Day(schedule);
        scheduleAdapter.swap(newDay.getHours());
        setTitle(year, month, day);
        progressDialog.dismiss();
    }

    private boolean isTheSameDay(Calendar otherCalendar) {
        return otherCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                otherCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                otherCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
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
