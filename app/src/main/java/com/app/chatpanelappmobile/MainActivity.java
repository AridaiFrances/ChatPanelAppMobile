package com.app.chatpanelappmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

import static android.widget.LinearLayout.HORIZONTAL;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    AlertDialog dialogoCargando, alertDialog;
    AlertDialog.Builder dialogBuilder;
    ArrayList<String> usuarioNombre = new ArrayList<>();
    ArrayList<String> usuarioPass = new ArrayList<>();

    String[] nombresArr;
    String[] passArr;
    String nombre, pass;
    int id_usuario;
    String estadoLogin="";
    MaterialEditText NombreUsuarioEdt;
    MaterialEditText PassEdt;
    Button LoginBtn;
    Button RegistroBtn;
    private String PREFS_KEY = "mispreferencias";
    public String usuarioDef, passwordDef;
    public Toolbar toolbar;
    public Usuario[] usuario;

    TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//ESTABLEZCO NOMBRE DE USUARIO AL HEADER
        profileName = navigationView.getHeaderView(0).findViewById(R.id.prueba);
//Asigna texto a TextView.

////////////////////////////////////////////////////////////////
        getValuePreference(MainActivity.this);

        new Async().execute();
        profileName.setText(estadoLogin);
        //PENDIENTE DE MODIFICAR
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//////////////////////////////////////////////////////////////////

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //MENU DESPLEGABLE PENDIENTE DE MODIFICAR/////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                saveValuePreference(MainActivity.this,"");
                usuarioDef="";
                estadoLogin="";
                profileName.setText(estadoLogin);
                pantallaLogin();
                return true;
           /* case R.id.action_settings:
                startSettings();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//PENDIENTE DE MODIFICAR///////////////////////////////////////////////////////
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//METODO PARA GUARDAR EN PREFERENCIAS EL NOMBRE DE USUARIO//////////////////////////////////
    public void saveValuePreference(Context context, String text) {
        try{
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString("email", text);
        editor.commit();}
        catch(Exception e){
            Toast.makeText(MainActivity.this, "Ha ocurrido un error en la máquina, pero puede continuar",
                    Toast.LENGTH_LONG).show();
        }
    }


//CONSIGO EL NOMBRE DE USUARIO PARA EL AUTOLOGIN
    public void getValuePreference(Context context) {
        try{
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
            usuarioDef=  preferences.getString("email", "");
            estadoLogin=  preferences.getString("email", "");}
        catch(Exception e){
            Toast.makeText(MainActivity.this, "Ha ocurrido un error en la máquina, pero puede continuar",
                    Toast.LENGTH_LONG).show();
        }
    }


//GIF LOADER///////////////////////////////////////////////////
    public void cargandoPopUp() {
        LinearLayout popUp = new LinearLayout(this);
        popUp.setOrientation(HORIZONTAL);
        popUp.setPadding(30, 30, 30, 30);
        popUp.setGravity(Gravity.CENTER);
        popUp.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams popUpParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popUpParam.gravity = Gravity.CENTER;
        popUp.setLayoutParams(popUpParam);

        GifImageView imagen = new GifImageView(this);

        imagen.setImageResource(R.drawable.cargandodef);
        imagen.setPadding(0, 0, 30, 0);
        imagen.setLayoutParams(popUpParam);

        popUpParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popUpParam.gravity = Gravity.CENTER;

        popUp.addView(imagen);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(popUp);

        dialogoCargando = builder.create();
        dialogoCargando.show();
        Window window = dialogoCargando.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(250, 250);

    }

//PANTALLA DE LOGIN O REGISTER/////////////////////////////////////////////////////////////
    public void pantallaLogin() {
if(estadoLogin==""){
    dialogBuilder = new AlertDialog.Builder(MainActivity.this);
    View layoutView = getLayoutInflater().inflate(R.layout.login_layout, null);
    NombreUsuarioEdt = layoutView.findViewById(R.id.user);
    PassEdt = layoutView.findViewById(R.id.pass);
    LoginBtn = layoutView.findViewById(R.id.login);
    RegistroBtn = layoutView.findViewById(R.id.registro);
    id_usuario = 0;

    RegistroBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this, "Temporalmente fuera de servicio",
                    Toast.LENGTH_LONG).show();

        }
    });

    LoginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nombre = NombreUsuarioEdt.getText().toString();
            pass = PassEdt.getText().toString();


            for (int i = 0; i < usuario.length; i++) {
                if (usuario[i].getNombre().equals(nombre)) {
                    usuarioDef = usuario[i].getNombre();
                    passwordDef = usuario[i].getPass();
                }
            }
            if (nombre.equals(usuarioDef) && pass.equals(passwordDef)) {
                Toast.makeText(MainActivity.this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
                saveValuePreference(MainActivity.this, usuarioDef);
                profileName.setText(usuarioDef);
                alertDialog.dismiss();

            } else if (nombre.equals(usuarioDef)) {
                Toast.makeText(MainActivity.this, "Contraseña incorrecta!", Toast.LENGTH_SHORT).show();
            } else if (pass.equals(passwordDef)) {
                Toast.makeText(MainActivity.this, "Usuario incorrecto!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Datos de acceso incorrectos!", Toast.LENGTH_SHORT).show();
            }


        }
    });


    dialogBuilder.setView(layoutView);
    alertDialog = dialogBuilder.create();
    alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    alertDialog.setCancelable(false);
    alertDialog.show();

}
else{
    Toast.makeText(MainActivity.this, "Bienvenid@ "+usuarioDef,
            Toast.LENGTH_LONG).show();
}

    }

//CLASE PARA OBTENER LECTURAS DE LA DB EXTERNA//////////////////////////////////////////////////
    class Async extends AsyncTask<Void, Void, Void> {


        String records = "", error = "";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            cargandoPopUp();
        }

        @Override

        protected Void doInBackground(Void... voids) {

            try {

                Class.forName("com.mysql.jdbc.Driver");

                Connection connection = DriverManager.getConnection("jdbc:mysql://SERVERDB:3306/NOMBREDB", "USER", "PASS");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("QUERY MYSQL");

                while (resultSet.next()) {

                    // records += resultSet.getString(1) + " " + resultSet.getString(2) + "\n";
                    usuarioNombre.add(resultSet.getString(1));
                    usuarioPass.add(resultSet.getString(2));
                }
                nombresArr = usuarioNombre.toArray(new String[usuarioNombre.size()]);
                passArr = usuarioPass.toArray(new String[usuarioPass.size()]);
                usuario = new Usuario[passArr.length];
            } catch (Exception e) {

                error = e.toString();

            }

            return null;

        }


        @Override

        protected void onPostExecute(Void aVoid) {



            for (int i = 0; i < usuario.length; i++) {
                usuario[i] = new Usuario(nombresArr[i], passArr[i]);
            }
            dialogoCargando.dismiss();
            Toast.makeText(MainActivity.this, "Carga completada",
                    Toast.LENGTH_LONG).show();

            if (error != "") {
                Toast.makeText(MainActivity.this, error,
                        Toast.LENGTH_SHORT).show();
            }
if(estadoLogin==""){
    pantallaLogin();
}
else{
    // find the CoordinatorLayout id
    View contextView = findViewById(android.R.id.content);
    // Make and display Snackbar
    Snackbar.make(contextView, "Hola de nuevo "+estadoLogin+"!", Snackbar.LENGTH_SHORT)
            .show();
}


            super.onPostExecute(aVoid);

        }


    }
}