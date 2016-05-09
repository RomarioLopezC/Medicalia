package com.example.user.medicalia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.medicalia.R;
import com.example.user.medicalia.adapter.DoctorAdapter;
import com.example.user.medicalia.models.Doctor;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DoctorsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.recycler_view_doctors)
    public RecyclerView recyclerViewDoctores;

    public DoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        ButterKnife.bind(this, view);

        DoctorAdapter doctorAdapter = new DoctorAdapter(this.getActivity(), getListDoctors());
        recyclerViewDoctores.setHasFixedSize(true);
        recyclerViewDoctores.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerViewDoctores.setAdapter(doctorAdapter);

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

    public List<Doctor> getListDoctors() {
        List<Doctor> doctors =  new ArrayList<Doctor>();
        doctors.add(new Doctor("Oscar Perez"));
        doctors.add(new Doctor("Kevin Pacheco"));
        doctors.add(new Doctor("Astrid Briceño"));
        doctors.add(new Doctor("Melissa Gonzales"));
        doctors.add(new Doctor("Romario Lopez"));
        doctors.add(new Doctor("Victor Sosa"));
        return doctors;
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
