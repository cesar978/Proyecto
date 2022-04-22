package edu.ieu.proyectocanciones;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import edu.ieu.proyectocanciones.modelo.AdminSqliteOpenHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final AdminSqliteOpenHelper adminDB = new AdminSqliteOpenHelper(this);
    private static final String TABLE_NAME="canciones";

    Button btnfecha;
    EditText etfecha;


    private EditText etId;
    private EditText etTitulo;
    private EditText etArtista;
    private EditText etAlbum;
    private EditText etCompositor;
    private EditText etGenero;
    private EditText etFecha;

    private  Button btnAlta;
    private Button btnBaja;
    private Button btnBuscarId;
    private Button btnActualizar;


    private int dia,mes,anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button info = findViewById(R.id.btnactivity1);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity2.class);
                startActivityForResult(intent, 0);

                Button info = findViewById(R.id.btnactivity1);
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainActivity2.class);
                        startActivityForResult(intent, 0);
                    }
                });
                Button info2 = findViewById(R.id.btnactivity2);
                info2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainActivity3.class);
                        startActivityForResult(intent, 0);
                    }
                });
                Button info3 = findViewById(R.id.btnactivity3);
                info3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainActivity4.class);
                        startActivityForResult(intent, 0);
                    }
                });




            }

        }
        );

        btnfecha=(Button)findViewById(R.id.btn_fecha);
        etfecha=(EditText) findViewById(R.id.et_fecha);

        btnfecha.setOnClickListener(this);

        etId=findViewById(R.id.et_id);
        etTitulo=findViewById(R.id.et_titulo);
        etArtista=findViewById(R.id.et_artista);
        etAlbum=findViewById(R.id.et_album);
        etCompositor=findViewById(R.id.et_compositor);
        etGenero=findViewById(R.id.et_genero);
        etFecha=findViewById(R.id.et_fecha);
        btnAlta=findViewById(R.id.btn_alta);
        btnBaja=findViewById(R.id.btn_baja);
        btnBuscarId=findViewById(R.id.btn_buscar_id);
        btnActualizar=findViewById(R.id.btn_actualizar);

        btnAlta.setOnClickListener(view -> {
            alta();
        });
        btnBuscarId.setOnClickListener(view -> {
            buscarPorId();
        });
        btnBaja.setOnClickListener(view -> {
            eliminar();

        });
        btnActualizar.setOnClickListener(view -> {
            actualizar();    
        });



    }

    private void actualizar() {
        String id =etId.getText().toString();
        String titulo =etTitulo.getText().toString();
        String artista =etArtista.getText().toString();
        String album =etAlbum.getText().toString();
        String compositor =etCompositor.getText().toString();
        String genero =etGenero.getText().toString();
        String fecha= etFecha.getText().toString();

        SQLiteDatabase db=adminDB.getWritableDatabase();
        if (id.length()>0){
            ContentValues content = new ContentValues();

            content.put("titulo", titulo);
            content.put("artista", artista);
            content.put("album", album);
            content.put("compositor", compositor);
            content.put("genero", genero);
            content.put("fecha", fecha);

            String where="id=?";
            String[] whereparams={id};

            int actualizados=db.update(TABLE_NAME,content, where,whereparams);
            if (actualizados>0){
                Toast.makeText(this,"Se actualizo el id "+
                        id + "correctamente",Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(this,"El id "+id+" no se encontro en la base de datos", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this,"El campo id debe estar vacio", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminar() {
            String id=etId.getText().toString();
            if (id.length()>0){
                SQLiteDatabase db= adminDB.getWritableDatabase();
                String where="id=?";//WHERE
                String[] stringArray= {id};//VALORES DEL WHERE
                int registros_borrados =db.delete(
                        TABLE_NAME,
                        where,
                        stringArray


                );
                if (registros_borrados>0){
                    Toast.makeText(this, "El registro "+id+" se elimino correctamente"
                            ,Toast.LENGTH_LONG).show();
                    etId.setText("");
                }
                else{
                    Toast.makeText(this," El id "+ id+" no se encontro en la base de datos",Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(this,"El campo id no debe estar vacio",Toast.LENGTH_LONG).show();
            }


    }

    private void buscarPorId() {
        String id =etId.getText().toString();
        if (id.length()>0){
            SQLiteDatabase db=adminDB.getWritableDatabase();
            String where="id= ?";

            String[] objectArray={id};
            Cursor cursor=db.query(TABLE_NAME,null,where,objectArray,null,null,null
                    );
            if (cursor.moveToNext()){
                @SuppressLint("Range") String titulo=cursor.getString(cursor.getColumnIndex("titulo"));
                @SuppressLint("Range") String artista=cursor.getString(cursor.getColumnIndex("artista"));
                @SuppressLint("Range") String album=cursor.getString(cursor.getColumnIndex("album"));
                @SuppressLint("Range") String compositor=cursor.getString(cursor.getColumnIndex("compositor"));
                @SuppressLint("Range") String genero=cursor.getString(cursor.getColumnIndex("genero"));
                @SuppressLint("Range") String fecha=cursor.getString(cursor.getColumnIndex("fecha"));
                etTitulo.setText(titulo.toString());
                etArtista.setText(artista.toString());
                etAlbum.setText(album.toString());
                etCompositor.setText(compositor.toString());
                etGenero.setText(genero.toString());
                etfecha.setText(fecha.toString());
            }
            else{
                Toast.makeText(this,"id "+id+" no se encontro en la base de datos",Toast.LENGTH_LONG).show();
            }

        }else
        {
            Toast.makeText(this,"El campo del id no debe estar vacio",Toast.LENGTH_LONG).show();
        }
    }

    private void alta() {
        String id=etId.getText().toString();
        String titulo=etTitulo.getText().toString();
        String artista=etArtista.getText().toString();
        String album=etAlbum.getText().toString();
        String compositor=etCompositor.getText().toString();
        String genero=etGenero.getText().toString();
        String fecha=etFecha.getText().toString();

        SQLiteDatabase db=adminDB.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("id", id);
        content.put("titulo",titulo);
        content.put("artista",artista);
        content.put("album",album);
        content.put("compositor",compositor);
        content.put("genero",genero);
        content.put("fecha",fecha);

        db.insert(TABLE_NAME, null,content);
        Toast.makeText(this,"Se guardo el elemento "+ id+" correctamente",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        if (v==btnfecha){
            final Calendar calendar=Calendar.getInstance();
            dia=calendar.get(Calendar.DAY_OF_MONTH);
            mes=calendar.get(Calendar.MONTH);
            anio=calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this
                    , new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                    etfecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            }
                    ,dia,mes,anio);
            datePickerDialog.show();

        }
    }

}
