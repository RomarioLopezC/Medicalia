package com.example.user.medicalia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.medicalia.R;
import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.models.UserAttributes;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProfileFragment extends Fragment {


    @Bind(R.id.profile_name)
    public TextView textView_name;
    @Bind(R.id.profile_email)
    public TextView textView_email;
    @Bind(R.id.textView_phone)
    public TextView textView_phone;
    @Bind(R.id.textView_blood_type)
    public TextView textView_blood_type;
    @Bind(R.id.textView_birthday)
    public TextView textView_birthday;
    @Bind(R.id.textView_height)
    public TextView textView_height;
    @Bind(R.id.textView_weight)
    public TextView textView_weight;
    @Bind(R.id.textView_allergies)
    public TextView textView_allergies;
    @Bind(R.id.textView_gender)
    public TextView textView_gender;


    private OnFragmentInteractionListener mListener;
    private UserAttributes currentUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        String jsonUser = getArguments().getString("user", "");
        currentUser = Utils.toUserAtributtes(jsonUser);

        String fullname = currentUser.getName() + " " + currentUser.getLastname();
        textView_name.setText(fullname);
        textView_email.setText(currentUser.getEmail());
        textView_phone.setText(currentUser.getMobile());
        textView_blood_type.setText(currentUser.getPatient().getBloodType());
        textView_birthday.setText(currentUser.getPatient().getBirthday());
        String height = String.valueOf(currentUser.getPatient().getHeight()) + " cm";
        textView_height.setText(height);
        String weight = String.valueOf(currentUser.getPatient().getWeight()) + " kg";
        textView_weight.setText(weight);
        textView_allergies.setText(currentUser.getPatient().getAllergies());
        textView_gender.setText(currentUser.getPatient().getGender());


        // Inflate the layout for this fragment
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
