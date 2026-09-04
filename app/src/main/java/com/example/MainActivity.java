package com.example;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.ComponentActivity;

/**
 * MainActivity em Java para o aplicativo Horários de Ônibus.
 * Responsável por renderizar a interface Clean Minimalism e abrir os links dos horários.
 */
public class MainActivity extends ComponentActivity {

    public static final String URL_JOTUR = "https://www.jotur.com.br/horarios/";
    public static final String URL_FENIX = "https://www.consorciofenix.com.br/horarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnJotur = findViewById(R.id.btn_jotur);
        if (btnJotur != null) {
            btnJotur.setOnClickListener(v -> openWebPage(this, URL_JOTUR));
        }

        View btnFenix = findViewById(R.id.btn_fenix);
        if (btnFenix != null) {
            btnFenix.setOnClickListener(v -> openWebPage(this, URL_FENIX));
        }
    }

    public static void openWebPage(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Não foi possível abrir o navegador", Toast.LENGTH_SHORT).show();
        }
    }
}
