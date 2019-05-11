package com.kener.dell.telefonrehberi2;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

EditText adGiris,soyadGiris,telGiris,ismeGoreArat;
Button kaydet,kisiAra,kisiSil,kisiGuncelle;
SQliteHelper v1;

//*************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1=new SQliteHelper(this);

        adGiris=(EditText) findViewById(R.id.editTextAd);
        soyadGiris=(EditText) findViewById(R.id.editTextSoyad);
        telGiris=(EditText) findViewById(R.id.editTextNumara);
        ismeGoreArat=(EditText) findViewById(R.id.editTextIsimara);
        kisiSil=(Button) findViewById(R.id.butonkisisil);
        kisiGuncelle=(Button) findViewById(R.id.butonguncelle);
        kaydet=(Button) findViewById(R.id.butonkaydet);
        kisiAra=(Button) findViewById(R.id.buttonKisi);


        kaydet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                kaydet(adGiris.getText().toString(),soyadGiris.getText().toString(),telGiris.getText().toString());

            }
        });


        kisiAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bilgilericek(ismeGoreArat.getText().toString());
            }
        });

        kisiSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           KisiSil(adGiris.getText().toString(),soyadGiris.getText().toString(),telGiris.getText().toString());

            }
        });

        kisiGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               KayitGuncelle(adGiris.getText().toString(),soyadGiris.getText().toString(),telGiris.getText().toString());

            }
        });



    }
    //***************************************
    public void KisiSil(String ad,String soyad ,String number) {

        try {


            SQLiteDatabase db = v1.getReadableDatabase();
            db.delete("rehber", "ad" + "=?", new String[]{ad});
            Toast.makeText(getApplicationContext(), "Kayıt silindi", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Kayıt silinemedi", Toast.LENGTH_SHORT).show();
        }
    }
    //********************************************
    public void KayitGuncelle(String ad,String soyad, String number) {

        try {


            SQLiteDatabase db = v1.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("ad", ad);
            cv.put("soyad",soyad);
            cv.put("number",number);
            db.update("rehber", cv, "ad" + "=?", new String[]{ad});
            db.close();
            Toast.makeText(getApplicationContext(), "Kayıt Güncellendi", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Güncellenemedi", Toast.LENGTH_SHORT).show();
        }
    }
//***********************************************************************************
    public void kaydet( String ad, String soyad , String number){

        try{
            SQLiteDatabase db1=v1.getWritableDatabase();
            ContentValues cv1=new ContentValues();
            cv1.put("ad",ad);
            cv1.put("soyad",soyad);
            cv1.put("number",number);

            db1.insertOrThrow("rehber",null,cv1);
            db1.close();
            Toast.makeText(getApplicationContext(),"Kayıt işlemi tamamlandı",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Kayıt işleminde hata oluştu",Toast.LENGTH_SHORT).show();

        }


    }
//****************************************************************************
    public void  bilgilericek(String kisiAd){
        try{
            ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<String >(MainActivity.this,android.R.layout.select_dialog_singlechoice);

            String[] sutunlar={"ad","soyad","number"};
            SQLiteDatabase db=v1.getReadableDatabase();
            Cursor okunanlar=db.query("rehber",sutunlar,"ad=?",new String[]{kisiAd},null,null,null);

            String ad="";
            String soyad="";
            String number="";

            while (okunanlar.moveToNext()){

                ad=okunanlar.getString(okunanlar.getColumnIndex("ad"));
                soyad=okunanlar.getString(okunanlar.getColumnIndex("soyad"));
                number=okunanlar.getString(okunanlar.getColumnIndex("number"));

                arrayAdapter2.add(ad+ "\n"+soyad + "\n" +number); //okunan veriler adaptöre alt alta eklendi.
            }
           cekilenleriGoster(arrayAdapter2);
        }
        catch (Exception e){
           Toast.makeText(getApplicationContext(),"Bilgileri getirirken hata oluştu.",Toast.LENGTH_SHORT).show();
        }
    }
//*************************************************************************
    //bilgisini istediğimi alette çıkartmak
  private  void cekilenleriGoster(final  ArrayAdapter arrayAdapter2){
      AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
      builderSingle.setIcon(R.drawable.ic_launcher_background);
      builderSingle.setTitle("Getirilen Kayıtlar");

      builderSingle.setNegativeButton("Çıkış", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int which ) {
              dialogInterface.dismiss();
          }
      });

      builderSingle.setAdapter(arrayAdapter2, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which ) {
              String strName=(String) arrayAdapter2.getItem(which);
              AlertDialog.Builder builderInner=new AlertDialog.Builder(MainActivity.this);
              builderInner.setCancelable(false);
              builderInner.setMessage(strName);

              builderInner.setTitle("Kayıtlar");
              builderInner.setPositiveButton("Değiştir", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {

                  }
              });
              builderInner.setNegativeButton("Sil", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {

                  }
              });
             builderInner.show() ;
          }
      });

      builderSingle.show();
  }


}
