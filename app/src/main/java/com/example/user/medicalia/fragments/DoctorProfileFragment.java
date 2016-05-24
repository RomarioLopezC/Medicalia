package com.example.user.medicalia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.medicalia.DrawerActivity;
import com.example.user.medicalia.R;
import com.example.user.medicalia.models.Doctor;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DoctorProfileFragment extends Fragment {

    private static final String TAG = "DoctorProfile";
    public DrawerActivity drawerActivity = null;
    public Doctor currentDoctor;

    @Bind(R.id.user_profile_name)
    TextView _doctorName;
    @Bind(R.id.user_profile_short_bio) TextView _doctorBio;
    @Bind(R.id.Hospital) TextView _hostpital;
    @Bind(R.id.address) TextView _address;
    @Bind(R.id.office) TextView _office;

    private OnFragmentInteractionListener mListener;
    public Toolbar toolbar = null;

    public DoctorProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_doctor_profile, container, false);
        ButterKnife.bind(this,view);



        currentDoctor = (Doctor) getArguments().getSerializable("Doctor");

        drawerActivity = (DrawerActivity) getActivity();

        setToolbar(view);

        _doctorName.setText(currentDoctor.getUserAttributes().getFullName());
        _doctorBio.setText(currentDoctor.getSpecialty());
        _hostpital.setText(currentDoctor.getHospital());
        _address.setText(currentDoctor.getAddress());
        _office.setText(currentDoctor.getOffice());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawerActivity.setSupportActionBar(toolbar);
        //drawerActivity.getSupportActionBar().setTitle(getString(R.string.profile));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                drawerActivity, drawerActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerActivity.drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @OnClick(R.id.show_day)
    public void showDay(View view) {
        setScheduleFragment();
    }

    private void setScheduleFragment() {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DoctorId", currentDoctor.getId());
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = drawerActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_drawer, fragment);
        fragmentTransaction.commit();
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
}
