package com.app.chatpanelappmobile.ui.gallery;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.chatpanelappmobile.R;

public class GalleryFragment extends Fragment implements
        View.OnClickListener{

    private GalleryViewModel galleryViewModel;
    AlertDialog dialogoNuevoChat;
    AlertDialog.Builder dialogBuilder;
    Context thiscontext;
String usuarioActualChat;

    TextView[] material;
    LinearLayout layoutChats;
    int numero =globales.usuario.length;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        thiscontext = container.getContext();
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        globales.toolbar.setTitle("Chats");
        layoutChats= (LinearLayout) root.findViewById(R.id.layoutChat);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                rellenarChats();
            }
        });
        return root;

    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < material.length; i++) {
            if ((v.getId() == i)) {

                dialogBuilder = new AlertDialog.Builder(thiscontext);
                View layoutView = getLayoutInflater().inflate(R.layout.nuevochat_layout, null);

                TextView txChatConexion;

                txChatConexion = (TextView) layoutView.findViewById(R.id.txChatConexion);
                txChatConexion.setText(globales.usuario[i].getNombre());
                Button BotonCerrar, BotonChat, BotonPerfil;
                BotonChat = (Button) layoutView.findViewById(R.id.iniciarChat);
                BotonPerfil = (Button) layoutView.findViewById(R.id.verPerfil);
                BotonCerrar = (Button) layoutView.findViewById(R.id.cerrarVentana);
                BotonCerrar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        dialogoNuevoChat.dismiss();

                    }
                });

                BotonChat.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {



                        Toast.makeText(thiscontext, "Próximanente se habilitará la opción de chatear entre usuari@s",
                                Toast.LENGTH_LONG).show();

                    }
                });

                BotonPerfil.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        Toast.makeText(thiscontext, "Oooops.... Perfiles todavía no implementados",
                                Toast.LENGTH_LONG).show();

                    }
                });

                dialogBuilder.setView(layoutView);
                dialogoNuevoChat = dialogBuilder.create();
                dialogoNuevoChat.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationCorreo;
                dialogoNuevoChat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogoNuevoChat.setTitle("Acciones disponibles");

                dialogoNuevoChat.setCancelable(false);
                dialogoNuevoChat.show();


            }
        }
    }


    public void rellenarChats(){
        layoutChats.removeAllViewsInLayout();
        material = new TextView[numero];

        for(int i =0;i<numero;i++){



            material[i] = new TextView(thiscontext);
            material[i].setText(globales.usuario[i].getNombre());
            material[i].setTextColor(Color.parseColor("#000000"));
            material[i].setTextSize(20);
            material[i].setGravity(Gravity.CENTER);
            material[i].setId(i);
            material[i].setOnClickListener(this);
            layoutChats.addView(material[i]);

        }
    }
}





/*

 public class Main3Activity extends AppCompatActivity implements
        View.OnClickListener {
    Button boton;
    ScrollView scrollCheckBoxes;
    ImageButton siguiente, anterior, mensaje, terminar;
    String idHerramientaString, idMaterialesString, cantidadMaterialesString, contenido = "", contenidoSn = "";
    TextView tituloPasoActual, puestoPasoActual, herramientaId, herramientaDesc, materialesID, materialesCantidad, componente, tvSn;
    LinearLayout layoutDchaCuarto, layoutPrinDchaPrimero, layoutHerramientasID, layoutHerramientasDescripcion, layoutIzqAbajo, LayoutImagenesArribaIquierda, LayoutMaterialesID, LayoutMaterialesSku, LayoutMaterialesCantidad, LayoutMaterialesNserie;
    int idHerramienta, sumaImagenes, sumaMateriales, sumabotonesMateriales, idMaterial, cantidadMaterial;
    CheckBox[] checkbox;
    TextView[] material;
    Button[] botones;
    String[] checkboxesDatos, imagenesUrl, materialSku, materialDesc, materialBotonSn;
    int sumaPasos, sumaCheck, sumaBotones;
    ArrayList<String> list, listaImg, listaMaterial, listaMaterialDesc, listaBotonesSn, checklist;
    public static int estado;
    int comodin, extra, extra2, activar, activado;
    EditText Nserie;
    Button BotonAceptarNserie;
    String Contenido;
    Dialog dialog;
    Button BotonAceptarObservaciones;
    EditText textoSn;
    EditText Incidencia;
    Button BotonAceptarIncidencia, BotonCancelarIncidencia;





 public void establecerMateriales() {
        activar = 0;
        activado = 0;
        LayoutMaterialesID.removeAllViewsInLayout();
        LayoutMaterialesSku.removeAllViewsInLayout();
        LayoutMaterialesCantidad.removeAllViewsInLayout();
        LayoutMaterialesNserie.removeAllViewsInLayout();
        sumaMateriales = 0;
        sumaBotones = 0;
        sumabotonesMateriales = 0;
        listaMaterial = new ArrayList<String>();
        listaMaterialDesc = new ArrayList<String>();
        listaBotonesSn = new ArrayList<String>();
//SE CUENTAN LOS BOTONES Y MATERIALES NECESARIOS//////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < globales.Materiales.length; i++) {
            if (globales.Materiales[i].getPaso() == globales.PasosEpc[globales.paginaActual].getId()) {
                sumaMateriales++;
                listaMaterial.add(globales.Materiales[i].getSku());
                listaMaterialDesc.add(globales.Materiales[i].getDescripcion_material());
                listaBotonesSn.add(globales.Materiales[i].getSn());
                if (globales.Materiales[i].getSn().equals("si")) {
                    sumabotonesMateriales++;

                }
                materialesID = new TextView(Main3Activity.this);
                idMaterial = globales.Materiales[i].getId();
                idMaterialesString = Integer.toString(idMaterial);
                materialesID.setText(idMaterialesString);
                materialesID.setTextColor(Color.parseColor("#000000"));
                materialesID.setTextSize(14);
                materialesID.setGravity(Gravity.CENTER);
                LayoutMaterialesID.addView(materialesID);
                materialesCantidad = new TextView(Main3Activity.this);
                cantidadMaterial = globales.Materiales[i].getCantidad();
                cantidadMaterialesString = Integer.toString(cantidadMaterial);
                materialesCantidad.setText(cantidadMaterialesString);
                materialesCantidad.setTextColor(Color.parseColor("#000000"));
                materialesCantidad.setTextSize(14);
                materialesCantidad.setGravity(Gravity.CENTER);
                LayoutMaterialesCantidad.addView(materialesCantidad);

            }
        }


        materialDesc = new String[listaMaterialDesc.size()];
        material = new TextView[listaMaterial.size()];
        materialSku = new String[listaMaterial.size()];
        materialSku = listaMaterial.toArray(materialSku);
        materialDesc = listaMaterialDesc.toArray(materialDesc);

        materialBotonSn = new String[listaBotonesSn.size()];
        botones = new Button[listaBotonesSn.size()];
        materialBotonSn = listaBotonesSn.toArray(materialBotonSn);



        //AQUI//////////////////////////////////////



//SE CREAN LOS BOTONES DE LOS MATERIALES QUE NECESITAN UN NUMERO DE SERIE
        for (int i = 0; i < material.length; i++) {
            comodin = 100 + i;
            botones[i] = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 38);
            layoutParams.setMargins(0, -6, 0, -6);
            botones[i].setText("No asignado");
            botones[i].setPadding(0, 0, 0, 0);
            botones[i].setTextSize(12);
            botones[i].setTextColor(Color.parseColor("#ffffff"));
            botones[i].setId(comodin);
            botones[i].setOnClickListener(this);
            if (!materialBotonSn[i].equals("si")) {
                botones[i].setVisibility(INVISIBLE);

            }

            LayoutMaterialesNserie.addView(botones[i], layoutParams);
//TAMBIEN SE GENERA LOS VIEW CON EL TEXTO DE CADA MATERIAL Y SU CORRESPONDIENTE METODO ONCLICK
            material[i] = new TextView(this);
            material[i].setText(materialSku[i]);
            material[i].setTextColor(Color.parseColor("#000000"));
            material[i].setTextSize(14);
            material[i].setGravity(Gravity.CENTER);
            material[i].setId(i);
            material[i].setOnClickListener(this);
            LayoutMaterialesSku.addView(material[i]);
        }

        for (int i = 0; i < material.length; i++) {
            if (botones[i].getVisibility() == View.VISIBLE) {
                activar++;
            } else {
                // Either gone or invisible
            }
        }

    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < material.length; i++) {
            extra = 100 + i;
            if ((v.getId() == extra)) {
                extra2 = extra;
                dialog = new Dialog(Main3Activity.this);
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                View view = getLayoutInflater().inflate(R.layout.nserie_dialog, null);
                dialog.setTitle("Numero de serie");
                componente = (TextView) view.findViewById(R.id.componente);
                componente.setText(materialSku[i] + " - " + materialDesc[i]);
                BotonAceptarNserie = (Button) view.findViewById(R.id.BotonAceptarNserie);
                Nserie = (EditText) view.findViewById(R.id.Nserie);
                BotonAceptarNserie.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        contenido = Nserie.getText().toString();
                        if (contenido.isEmpty()) {
                            Nserie.setText("Introduce número de serie.");
                            BotonAceptarNserie.setEnabled(false);
                            //Declaro un Handler que hace de unión entre el hilo principal y el secundario
                            Handler handler = new Handler();

                            //Llamo al método postDelayed
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    Nserie.setText("");
                                    BotonAceptarNserie.setEnabled(true);
                                }
                            }, 1000); // 1 segundos de "delay"
                        } else {


                            for (int j = 0; j < material.length; j++) {
                                if (botones[j].getId() == extra2) {
                                    botones[j].setText(contenido);

                                    if (materialDesc[j].contains("High")) {
                                        globales.snHigh = contenido;
                                        cargandoPopUp();
                                        actualizarEquipoHigh();
                                    }


                                    if (materialDesc[j].contains("MOSFET")) {
                                        globales.snMosfet = contenido;

                                        cargandoPopUp();
                                        actualizarEquipoMosfet();

                                    }


                                    if (materialDesc[j].contains("Control")) {
                                        globales.snControl = contenido;

                                        cargandoPopUp();
                                        actualizarEquipoControl();

                                    }


                                    if (materialDesc[j].contains("Decoupling")) {
                                        globales.snDecoupling = contenido;

                                        cargandoPopUp();
                                        actualizarEquipoDecoupling();

                                    }
                                    if (materialDesc[j].contains("Transformador")) {
                                        globales.snTrafo = contenido;

                                        cargandoPopUp();
                                        actualizarEquipoTrafo();

                                    }

                                    botones[j].setEnabled(false);

                                    activado++;
                                }
                            }
                            dialog.cancel();
                        }

                    }
                });
                dialog.setContentView(view);
                dialog.show();
                dialog.getWindow().setLayout(600, 400);
                dialog.setCancelable(true);

                for (int j = 0; j < globales.Materiales.length; j++) {
                    if (globales.Materiales[j].getSku().equals(materialSku[i])) {
                        globales.Materiales[j].setSn("hecho");
                    }
                }


            }
        }
//DIALOG QUE MUESTRA LA INFORMACION ADICIONAL DE CADA MATERIAL EN EL EVENTO ONLICK
        for (int i = 0; i < material.length; i++) {
            if ((v.getId() == i)) {
                dialog = new Dialog(Main3Activity.this);
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                View view = getLayoutInflater().inflate(R.layout.dialog_material, null);
                dialog.setTitle("Información adicional");
                TextView tvSku, tvDesc;
                Button BotonAceptarMaterial;
                LinearLayout layoutMaterialDerecha = (LinearLayout) view.findViewById(R.id.layoutMaterialDerecha);
                LinearLayout layoutMaterialIzquierda = (LinearLayout) view.findViewById(R.id.layoutMaterialIzquierda);
                layoutMaterialDerecha.removeAllViewsInLayout();
                layoutMaterialIzquierda.removeAllViewsInLayout();
                BotonAceptarMaterial = (Button) view.findViewById(R.id.BotonAceptarDialogMaterial);
                BotonAceptarMaterial.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        dialog.cancel();

                    }
                });

                tvSku = new TextView(Main3Activity.this);
                tvSku.setText(materialSku[i]);
                tvSku.setTextColor(Color.parseColor("#000000"));
                tvSku.setGravity(Gravity.CENTER);
                layoutMaterialIzquierda.addView(tvSku);
                tvDesc = new TextView(Main3Activity.this);
                tvDesc.setText(materialDesc[i]);
                tvDesc.setTextColor(Color.parseColor("#000000"));
                tvDesc.setGravity(Gravity.CENTER);
                layoutMaterialDerecha.addView(tvDesc);
                dialog.setContentView(view);
                dialog.show();
                dialog.getWindow().setLayout(600, 300);
                dialog.setCancelable(false);

            }
        }
    }*/