package com.example.user.medicalia.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.medicalia.DrawerActivity;
import com.example.user.medicalia.R;
import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.adapter.DiagnosticAdapter;
import com.example.user.medicalia.models.Diagnostic;
import com.example.user.medicalia.models.Patient;
import com.example.user.medicalia.remote.PatientAPI;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kev' on 23/05/2016.
 */
public class DiagnosticsFragment extends Fragment {
    public static final String TAG = DiagnosticsFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private Patient currentUser;
    public List<Diagnostic> diagnosticList;
    private DiagnosticAdapter adapter;
    private ProgressDialog progressDialog;
    private String token;
    public DrawerActivity drawerActivity = null;
    private Activity activity;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsLoading = false;
    public Toolbar toolbar = null;

    @Bind(R.id.recycler_view_diagnostics)
    public RecyclerView recyclerViewDiagnostics;

    public DiagnosticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diagnostics, container, false);
        ButterKnife.bind(this,view);

        drawerActivity = (DrawerActivity) getActivity();

        setToolbar(view);

        getUserData();

        getDiagnostics();

        adapter = new DiagnosticAdapter(activity, diagnosticList);
        recyclerViewDiagnostics.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerViewDiagnostics.setLayoutManager(mLayoutManager);
        recyclerViewDiagnostics.setAdapter(adapter);

        return view;
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawerActivity.setSupportActionBar(toolbar);

        drawerActivity.getSupportActionBar().setTitle(getString(R.string.diagnostics));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                drawerActivity, drawerActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerActivity.drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void getUserData() {
        String jsonUser = getArguments().getString(getString(R.string.user_key), "");
        currentUser = Utils.toUserAtributtes(jsonUser);
        activity = this.getActivity();
        token = getString(R.string.token) + currentUser.getUserAttributes().getToken();
    }

    private void getDiagnostics(){
        showLoadingDialog();
        fetchDiagnostics(mCallbackFirsPage);
    }

    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        mIsLoading = true;
    }

    public void fetchDiagnostics(Callback<List<Diagnostic>> callback){
        PatientAPI.Factory.getInstance().diagnostics(token, currentUser.getId()).enqueue(callback);
    }

    public Callback<List<Diagnostic>> mCallbackFirsPage = new Callback<List<Diagnostic>>() {
        @Override
        public void onResponse(Call<List<Diagnostic>> call, Response<List<Diagnostic>> response) {
            int code = response.code();
            mIsLoading = false;

            switch (code){
                case 200:
                    Log.d(TAG, String.valueOf(code));
                    diagnosticList = response.body();
                    adapter.swap(diagnosticList);

                    break;
                default:
                    Log.e(TAG, String.valueOf(code));
                    try {
                        Snackbar.make(getActivity().getCurrentFocus(), "Error del Servidor" + response.errorBody().string(), Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
            }

            progressDialog.dismiss();
        }

        @Override
        public void onFailure(Call<List<Diagnostic>> call, Throwable t) {
            progressDialog.dismiss();
            Snackbar.make(getActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    };

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
