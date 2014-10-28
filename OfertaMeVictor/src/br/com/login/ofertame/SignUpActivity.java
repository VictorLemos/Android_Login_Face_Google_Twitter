package br.com.login.ofertame;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;

public class SignUpActivity extends Activity {

	
	public static final String PRF_USER = "login";
	
	Button btnCriar;
	EditText etEmail, etUserName, etPass;
	
	boolean verificaUsuarioCadastrado = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        
        btnCriar = (Button) findViewById(R.id.btnSingUp);
        
        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        
        //Login que ficara armazenado o Usuário do cara em cache... seu tempo de vida é em quanto sistema estiver instalado no celular...
        SharedPreferences login = getSharedPreferences(PRF_USER, MODE_PRIVATE);
        
        //Captura as informações inseridas pelo usuario e salva dentro do login
        final String userNameSave = login.getString("userName", "");
        final String emailSave = login.getString("email", "");
        final String passSave = login.getString("password", "");
        
        if(userNameSave.equals("")){
        	AlertDialog.Builder alerta = new AlertDialog.Builder(SignUpActivity.this);
        	alerta.setTitle("Alerta");
        	alerta.setMessage("Para utilizar o aplicativo é necessário criar um Usuario..");
        	alerta.setNeutralButton("Ok", null);
			alerta.show();
        }else{
			verificaUsuarioCadastrado = true;
        } 
        
        btnCriar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String user = etUserName.getText().toString();
				String email = etEmail.getText().toString();
				String pass = etPass.getText().toString();
				
				if(verificaUsuarioCadastrado == false){
					SharedPreferences login = getSharedPreferences(PRF_USER, 0);
					SharedPreferences.Editor editor = login.edit();
					editor.putString("userName", user);
					editor.putString("email", email);
					editor.putString("password", pass);
					editor.commit();
					
					Toast.makeText(getApplicationContext(), "Usuário " +user+ " E-mail " +email+ " Criado com sucesso", Toast.LENGTH_LONG).show();
					
					trocaTela();

				}else if(user.equals(userNameSave) & email.equals(emailSave) & pass.equals(passSave)){
					Toast.makeText(getApplicationContext(), "Usuário já cadastrado ", Toast.LENGTH_LONG).show();
					
				}
			}
		});
    } 
    
    public void trocaTela(){
		Intent trocaTela = new Intent(SignUpActivity.this, SignInActivity.class);
		startActivity(trocaTela);
	}
    
    @Override
	protected void onStop() {
		super.onStop();
		//Garente que quando o usuário clicar no botão voltar, 
		//o sistema deve finalizar sem passar pela tela de login
		finish();
	}
}
