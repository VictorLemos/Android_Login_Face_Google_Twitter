package br.com.login.ofertame;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.SharedPreferences;

public class SignInActivity extends Activity {

	public static final String PRF_USER = "login";
	Button btnEntrar;
	EditText etUserName, etPass;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        
       btnEntrar = (Button) findViewById(R.id.btnSingIn);
       etUserName = (EditText) findViewById(R.id.etUserName);
       etPass = (EditText) findViewById(R.id.etPass);
       
       //Recuperando o Usuário cadastrado..... para entrar
       SharedPreferences login = getSharedPreferences(PRF_USER, MODE_PRIVATE);
       
       //Captura as informações inseridas pelo usuario e salva dentro do login
       final String userNameSave = login.getString("userName", "");
       final String passSave = login.getString("password", "");
       
        btnEntrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String user = etUserName.getText().toString();
				String pass = etPass.getText().toString();
				
				if(user.equals("") || pass.equals("")){
					Toast.makeText(getApplicationContext(), "Nenhum campo deve estar vazio", Toast.LENGTH_LONG).show();	
				
				}else if(user.equals(userNameSave) & pass.equals(passSave)){
					
					Toast.makeText(getApplicationContext(), "Bem vindo de volta " +user, Toast.LENGTH_LONG).show();
					
					//trocaTela();
				
				}else{
					Toast.makeText(getApplicationContext(), "Usuário ou senha errado ", Toast.LENGTH_LONG).show();
			
				}
			}
		}); 
    }
    
//   public void trocaTela(){
//		Intent trocaTela = new Intent(SignInActivity.this, BuscaOfertas.class);
//		startActivity(trocaTela);
//	}
   
}
