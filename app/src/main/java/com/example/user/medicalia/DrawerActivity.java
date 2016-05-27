package com.example.user.medicalia;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.fragments.DiagnosticsFragment;
import com.example.user.medicalia.fragments.DoctorsFragment;
import com.example.user.medicalia.fragments.ProfileFragment;
import com.example.user.medicalia.models.Patient;
import com.example.user.medicalia.remote.AppointmentAPI;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView = null;
    public DrawerLayout drawer = null;
    private String jsonCurrentUser;
    private BeaconManager beaconManager;
    private boolean beaconInRange = false;
    private Patient currentUser;
    private String token;

    @Bind(R.id.textView_pacient_name)
    TextView textView_pacient_name;

    @Bind(R.id.textView_pacient_email)
    TextView textView_pacient_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

        jsonCurrentUser = getSharedPreferences(getString(R.string.name_shared_preferences), Context.MODE_APPEND)
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

        token = getString(R.string.token) + currentUser.getUserAttributes().getToken();



//        Beacon
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                if (!beaconInRange) {
//                    PEREZ HELP PEREZ HELP PEREZ
//                    AQUI MANDA EL REQUEST A LA API Y LO QUE TE RESPONDA EN BODY LO MANDAS COMO
//                    PARAMETRO DEL METODO DE SHOWBEACONNOTIFICACION

                    AppointmentAPI.Factory.getInstance().register(token, "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    int code = response.code();


                                    switch (code) {
                                        case 200:
                                            try {
                                                Log.e("Drawer Activity", response.body().string());
                                                showBeaconNotification("Bienvenido al consulturio.",
                                                        response.body().string());
                                                beaconInRange = true;
                                            } catch (IOException e) {
                                                Log.e("Drawer Activity", e.getMessage());
                                            }

                                            break;
                                        default:
                                            Log.e("Drawer Activity", String.valueOf(code));
                                    }


                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("Drawer Activity", t.getMessage());
                                }
                            });



                }
            }

            @Override
            public void onExitedRegion(Region region) {
                showBeaconNotification("Esperamos su mejora.",
                        "Todavía no cuenta con un diagnóstico. Revisar la información más tarde.");
//                AQUI MANDA OTRO REQUEST PARA SABER SI SE CREO UN DIAGNOSTICO O NO y responde igual que el de arriba
                AppointmentAPI.Factory.getInstance().diagnostic_created(token, "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                int code = response.code();

                                switch (code) {
                                    case 200:
                                        try {
                                            showBeaconNotification("Bienvenido al consulturio.",
                                                    response.body().string());
                                            beaconInRange = true;
                                        } catch (IOException e) {
                                            Log.e("Drawer Activity", e.getMessage());
                                        }

                                        break;
                                    default:
                                        Log.e("Drawer Activity", String.valueOf(code));
                                }


                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("Drawer Activity", t.getMessage());
                            }
                        });

                beaconInRange = false;
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "Region a monitorear",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        63463, 21120));
            }
        });





        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView_pacient_name.setText(currentUser.getUserAttributes().getFullName());
        textView_pacient_email.setText(currentUser.getUserAttributes().getEmail());

        //Set the mainFragment
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.user_key), jsonCurrentUser);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_drawer, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.user_key), jsonCurrentUser);

        if (id == R.id.profile) {
            fragment = new ProfileFragment();
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        } else if (id == R.id.diagnostics) {
            fragment = new DiagnosticsFragment();
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        } else if (id == R.id.find_by_name) {
            fragment = new DoctorsFragment();
            bundle.putString(getString(R.string.find_by_key), getString(R.string.by_name));
            fragment.setArguments(bundle);
            fragmentTransaction = true;

//        } else if (id == R.id.find_by_hospital) {
//            fragment = new DoctorsFragment();
//            bundle.putString(getString(R.string.find_by_key), getString(R.string.by_hospital));
//            fragment.setArguments(bundle);
//            fragmentTransaction = true;
//
//        } else if (id == R.id.find_by_specialty) {
//            fragment = new DoctorsFragment();
//            bundle.putString(getString(R.string.find_by_key), getString(R.string.by_specialty));
//            fragment.setArguments(bundle);
//            fragmentTransaction = true;

        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor edit = getSharedPreferences(getString(R.string.name_shared_preferences), Context.MODE_APPEND).edit();
            edit.clear();
            edit.apply();
//            Para dejar de monitorear cuando cierra la sesión
            beaconManager.stopMonitoring(new Region(
                    "Region a monitorear",
                    UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                    63463, 21120));

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_drawer, fragment).addToBackStack("fragment")
                    .commit();

            ///item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showBeaconNotification(String title, String message) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        System.out.println(message);
        mBuilder.setSmallIcon(R.drawable.md);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("Notificaciones de Medicalia:");
//        String mensaje_1 = message.split(".")[0];
        inboxStyle.addLine(message);
//        if (message.split(".").length > 1) {
//            String mensaje_2 = message.split(".")[1];
//            inboxStyle.addLine(mensaje_2);
//        }
        mBuilder.setStyle(inboxStyle);

        Intent notifyIntent = new Intent(this, DrawerActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(1, mBuilder.build());
    }

}

