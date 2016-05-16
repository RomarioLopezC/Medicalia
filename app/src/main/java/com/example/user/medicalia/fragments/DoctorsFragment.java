package com.example.user.medicalia.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.medicalia.R;
import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.adapter.DoctorAdapter;
import com.example.user.medicalia.models.Doctor;
import com.example.user.medicalia.models.Patient;
import com.example.user.medicalia.remote.DoctorAPI;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DoctorsFragment extends Fragment {

    private static final String TAG = DoctorsFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private Patient currentUser;
    public List<Doctor> doctors;
    private Activity activity;
    private DoctorAdapter doctorAdapter;

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

        String jsonUser = getArguments().getString("user", "");
        currentUser = Utils.toUserAtributtes(jsonUser);
        activity = this.getActivity();

        getListDoctors();

        doctorAdapter = new DoctorAdapter(activity, doctors);
        recyclerViewDoctores.setHasFixedSize(true);
        recyclerViewDoctores.setLayoutManager(new LinearLayoutManager(activity));
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

    public void getListDoctors() {
        String token = "Token token=" + currentUser.getUser().getToken();
        DoctorAPI.Factory.getInstance().getDoctors(token, null, null, null, null)
                .enqueue(new Callback<List<Doctor>>() {
                    @Override
                    public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                        int code = response.code();

                        switch (code){
                            case 200:
                                Log.d(TAG, String.valueOf(code));
                                doctors = response.body();
                                doctorAdapter.swap(doctors);
                                break;
                            default:
                                Log.e(TAG, String.valueOf(code));
                                try {
                                    Snackbar.make(getActivity().getCurrentFocus(), "Error del Servidor" + response.errorBody().string(), Snackbar.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Doctor>> call, Throwable t) {
                        Snackbar.make(getActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
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
