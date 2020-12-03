package com.example.radosaw.test1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import android.util.Log;

import android.app.ProgressDialog;




public class MainActivity extends AppCompatActivity {

    private Button openMic;
    private final int REQ_CODE_SPEECH_OUTPUT =143;

    TextToSpeech t1;

    String degrees;
    String pytanie;
    String zadana_temp;

   // String ip="192.168.8.82";

    String ip="10.150.4.82";
    String adres_temp = "http://"+ip+"/S1.htm";
    String adres_zadana = "http://"+ip+"/K1.htm";
    String adres_komfort = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=21.0)";
    String adres_czuwanie = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=17.0)";
    String adres_17 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=17.0)";
    String adres_18 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=18.0)";
    String adres_19 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=19.0)";
    String adres_20 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=20.0)";
    String adres_21 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=21.0)";
    String adres_22 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=22.0)";
    String adres_23 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=23.0)";
    String adres_24 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=24.0)";
    String adres_25 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=25.0)";
    String adres_26 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=26.0)";
    String adres_27 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=27.0)";
    String adres_28 = "http://"+ip+"/ws/tsite.xml?Type=Write&Request=K1(V=28.0)";
    Document document = null;

    ProgressDialog mProgressDialog;

    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("myTag", "This is my message");
        setContentView(R.layout.activity_main);

        openMic = (Button) findViewById(R.id.button);


        //rozpoznawanie
        openMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);
                rozpoznawanie();

            }
        });

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);



        //synt
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR)
                {
                    t1.setLanguage(Locale.getDefault());
                }

            }
        });

    }




    //pobieranie i mowienie temp.

    private class pobieranie extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Sprawdzanie temperatury");
            mProgressDialog.setMessage("Pobieranie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(adres_temp).get();
                Elements description = document.select( "input[id=S1-56]");
                degrees = description.attr( "value");


            } catch (IOException e) {
                e.printStackTrace();

        }
            return null;
        }

       @Override
       protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String temp1 = "Jest";
            String temp2 = "stopni";
            String tempall = temp1+ " "+ degrees+ " "+ temp2;
            String toSpeak;
            toSpeak=tempall;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }


//koniec pobierania i mowienia temp.


//sprawdzanie zadanej temperatury
private class zadana extends AsyncTask<Void, Void, Void> {
    String title;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle("Sprawdzanie zadanej temperatury");
        mProgressDialog.setMessage("Pobieranie...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document document = Jsoup.connect(adres_zadana).get();
            Elements description = document.select( "input[id=K1-44]");
            zadana_temp = description.attr( "value");


        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        mProgressDialog.dismiss();
        String temp1 = "Wartość zadana to";
        String temp2 = "stopni";
        String temp_zadana_all = temp1+ " "+ zadana_temp+ " "+ temp2;
        String toSpeak;
        toSpeak=temp_zadana_all;
        Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
        t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
    }
}


    //zwiększanie temperatury
    private class zwieksz extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Zwiększanie temperatury");
            mProgressDialog.setMessage("Ładowanie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(adres_zadana).get();
                Elements description = document.select( "input[id=K1-44]");
                zadana_temp = description.attr( "value");


            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            if (zadana_temp.equals("17.00")){
                new osiemnascie().execute();
            }
            else if (zadana_temp.equals("18.00")){
                new dziewietnascie().execute();
            }
            else if (zadana_temp.equals("19.00")){
                new dwadziescia().execute();
            }
            else if (zadana_temp.equals("20.00")){
                new dwadziesciajeden().execute();
            }
            else if (zadana_temp.equals("21.00")){
                new dwadzeisciadwa().execute();
            }
            else if (zadana_temp.equals("22.00")){
                new dwadzeisciatrzy().execute();
            }
            else if (zadana_temp.equals("23.00")){
                new dwadzeisciacztery().execute();
            }
            else if (zadana_temp.equals("24.00")){
                new dwadzeisciapiec().execute();
            }
            else if (zadana_temp.equals("25.00")){
                new dwadzeisciaszesc().execute();
            }
            else if (zadana_temp.equals("26.00")){
                new dwadzeisciasiedem().execute();
            }
            else if (zadana_temp.equals("27.00")){
                new dwadzeisciaosiem().execute();
            }
            else{
                String error2="Wystąpił błąd podczas zwiększania temperatury. Nie odnaleziono zadanej temperatury lub jest ona spoza zakresu.";
                t1.speak(error2,TextToSpeech.QUEUE_FLUSH,null);
            }
        }
    }

    //zwiększanie temperatury
    private class zmniejsz extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Zmniejszanie temperatury");
            mProgressDialog.setMessage("Ładowanie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(adres_zadana).get();
                Elements description = document.select( "input[id=K1-44]");
                zadana_temp = description.attr( "value");


            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            if (adres_zadana.equals("18.00")){
                new siedemnascie().execute();
            }
            else if (zadana_temp.equals("19.00")){
                new osiemnascie().execute();
            }
            else if (zadana_temp.equals("20.00")){
                new dziewietnascie().execute();
            }
            else if (zadana_temp.equals("21.00")){
                new dwadziescia().execute();
            }
            else if (zadana_temp.equals("22.00")){
                new dwadziesciajeden().execute();
            }
            else if (zadana_temp.equals("23.00")){
                new dwadzeisciadwa().execute();
            }
            else if (zadana_temp.equals("24.00")){
                new dwadzeisciatrzy().execute();
            }
            else if (zadana_temp.equals("25.00")){
                new dwadzeisciacztery().execute();
            }
            else if (zadana_temp.equals("26.00")){
                new dwadzeisciapiec().execute();
            }
            else if (zadana_temp.equals("27.00")){
                new dwadzeisciaszesc().execute();
            }
            else if (zadana_temp.equals("28.00")){
                new dwadzeisciasiedem().execute();
            }
            else{
                String error2="Wystąpił błąd podczas zmniejszania temperatury. Nie odnaleziono zadanej temperatury lub jest ona spoza zakresu.";
                t1.speak(error2,TextToSpeech.QUEUE_FLUSH,null);
            }
        }
    }


// wysylanie komfort
    private class komfort extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Tryb komfort");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String y;
                Document document = Jsoup.connect(adres_komfort).get();
                y = document.title();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String komfort = "Włączono tryb komfort- dwadzieściajeden stopni";
            String toSpeak;
            toSpeak=komfort;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    // wysylanie czuwanie
    private class czuwanie extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Tryb czuwania");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_czuwanie).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String czuwanie = "Włączono tryb czuwania- siedemnaście stopni";
            String toSpeak;
            toSpeak=czuwanie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }



    // wysylanie 17
    private class siedemnascie extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("17 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_17).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono siedemnaście stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class osiemnascie extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("18 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_18).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono osiemnaście stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dziewietnascie extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("19 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_19).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dziewiętnaście stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }


    private class dwadziescia extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("20 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_20).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieścia stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadziesciajeden extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("21 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_21).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściajeden stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadzeisciadwa extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("22 stopnie");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_22).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściadwa stopnie";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadzeisciatrzy extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("23 stopnie");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_23).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściatrzy stopnie";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadzeisciacztery extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("24 stopnie");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_24).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściacztery stopnie";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadzeisciapiec extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("25 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_25).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściapięć stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadzeisciaszesc extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("26 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_26).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściasześć stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadzeisciasiedem extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("27 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_27).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściasiedem stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private class dwadzeisciaosiem extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("28 stopni");
            mProgressDialog.setMessage("Ustawianie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String x;
                Document document = Jsoup.connect(adres_28).get();
                x = document.title();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            String stopnie = "Ustawiono dwadzieściaosiem stopni";
            String toSpeak;
            toSpeak=stopnie;
            Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }
    }



//rozpoznawanie
    private void rozpoznawanie() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mów teraz");

        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        }
        catch(ActivityNotFoundException tim){
            String error2="Błąd rozpoznawania mowy";
            t1.speak(error2,TextToSpeech.QUEUE_FLUSH,null);

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQ_CODE_SPEECH_OUTPUT:{
                if(resultCode == RESULT_OK && data != null ){
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    pytanie = voiceInText.get(0);

                    if (voiceInText.get(0).equals("Ile jest stopni")){
                        new pobieranie().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw tryb komfort")){
                        new komfort().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw tryb czuwanie")){
                        new czuwanie().execute();
                    }
                    else if (voiceInText.get(0).equals("jaka jest ustawiona temperatura")){
                        new zadana().execute();
                    }
                    else if (voiceInText.get(0).equals("zwiększ temperaturę")){
                        new zwieksz().execute();
                    }
                    else if (voiceInText.get(0).equals("zmniejsz temperaturę")){
                        new zmniejsz().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 17 stopni")){
                        new siedemnascie().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 18 stopni")){
                        new osiemnascie().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 19 stopni")){
                        new dziewietnascie().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 20 stopni")){
                        new dwadziescia().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 21 stopni")){
                        new dwadziesciajeden().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 22 stopnie")){
                        new dwadzeisciadwa().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 23 stopnie")){
                        new dwadzeisciatrzy().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 24 stopnie")){
                        new dwadzeisciacztery().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 25 stopni")){
                        new dwadzeisciapiec().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 26 stopni")){
                        new dwadzeisciaszesc().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 27 stopni")){
                        new dwadzeisciasiedem().execute();
                    }
                    else if (voiceInText.get(0).equals("Ustaw 28 stopni")){
                        new dwadzeisciaosiem().execute();
                    }

                    else{
                        String error="Podano nieprawidłową komendę";
                        t1.speak(error,TextToSpeech.QUEUE_FLUSH,null);
                    }

                }
                break;
            }
        }

    }

}


