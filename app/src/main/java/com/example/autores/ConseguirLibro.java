package com.example.autores;

import android.os.AsyncTask;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class ConseguirLibro
        extends AsyncTask<String,Void,String> {

    private WeakReference<TextView> mTextoTitulo;
    private WeakReference<TextView> mTextoAutor;

    ConseguirLibro(TextView tituloTexto, TextView autorTexto){
        this.mTextoTitulo = new WeakReference<>(tituloTexto);
        this.mTextoAutor = new WeakReference<>(autorTexto);
    }

    @Override
    protected String doInBackground(String... strings) {
        return UtilidadesRed.obtenerInformacionLibro(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i= 0;
            String titulo = null;
            String autores = null;
            while(i < itemsArray.length() && (autores == null && titulo == null)){
                JSONObject libro = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = libro.getJSONObject("volumeInfo");
                try{
                    titulo = volumeInfo.getString("title");
                    autores = volumeInfo.getString("authors");
                }catch (Exception e){
                    mTextoTitulo.get().setText("No existen resultados");
                    mTextoAutor.get().setText("");
                    e.printStackTrace();
                }
                i++;
            }

            if(titulo != null && autores != null){
                mTextoTitulo.get().setText(titulo);
                mTextoAutor.get().setText(autores);
            }else{
                mTextoTitulo.get().setText("No existen resultados");
                mTextoAutor.get().setText("");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
