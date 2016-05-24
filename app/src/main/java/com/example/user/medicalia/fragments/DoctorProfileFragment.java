package com.example.user.medicalia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.medicalia.R;
import com.example.user.medicalia.models.Doctor;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DoctorProfileFragment extends Fragment {

    private static final String TAG = "DoctorProfile";

    @Bind(R.id.user_profile_name)
    TextView _doctorName;
    @Bind(R.id.user_profile_short_bio) TextView _doctorBio;
    @Bind(R.id.Hospital) TextView _hostpital;
    @Bind(R.id.address) TextView _address;
    @Bind(R.id.office) TextView _office;

    private OnFragmentInteractionListener mListener;

    public DoctorProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_doctor_profile, container, false);
        ButterKnife.bind(this,view);

        Doctor currentDoctor = (Doctor) getArguments().getSerializable("Doctor");

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
