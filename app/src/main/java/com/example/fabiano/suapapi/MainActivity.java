package com.example.fabiano.suapapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONObject;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String token = null;
    private String URL_SUAP = "https://suap.ifrn.edu.br/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Suap api exemplo
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put("username","matricula_do_usuário");
                postDataParams.put("password","senha");
                try {
                    String resposta = HttpConections.performPostCall(URL_SUAP
                            +"api/v2/autenticacao/token/",postDataParams); //pega token de acesso (retorna um json file)

                    JSONObject jsonObject = new JSONObject(resposta);
                    token = jsonObject.getString("token");

                    HashMap<String, String> headerOptions = new HashMap<>();
                    headerOptions.put("Authorization","JWT "+token);//passa as credenciais no header da solicitação http

                    String meusDados = HttpConections.getJson(URL_SUAP+"api/v2/minhas-informacoes/meus-dados/",headerOptions);
                    Log.i("resultado",meusDados);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
