package br.com.login.ofertame;


import br.com.login.facebook.FacebookLoginMainActivity;
import br.com.login.google.GoogleLoginMainActivity;
import br.com.login.twitter.TwitterAndroidLoginMainActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button btnSignIn;
	Button btnSignUp;
	Button btnGoogle;
	Button btnFb;
	Button btnTwitter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSignIn = (Button) findViewById(R.id.btnSingIn);
		btnSignUp = (Button) findViewById(R.id.btnSignUp);
		btnGoogle = (Button) findViewById(R.id.btnGplus);
		btnFb = (Button) findViewById(R.id.btnFb);
		btnTwitter = (Button) findViewById(R.id.btnTwitter);
		
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
		btnGoogle.setOnClickListener(this);
		btnFb.setOnClickListener(this);
		btnTwitter.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch(v.getId()){
			case R.id.btnSingIn:
				i = new Intent(this,SignInActivity.class);
				break;
			case R.id.btnSignUp:
				i = new Intent(this,SignUpActivity.class);
				break;
			case R.id.btnGplus:
				i = new Intent(this,GoogleLoginMainActivity.class);
				break;
			case R.id.btnFb:
				i = new Intent(this,FacebookLoginMainActivity.class);
				break;	
			case R.id.btnTwitter:
				i = new Intent(this,TwitterAndroidLoginMainActivity.class);
				break;	
		}
		startActivity(i);
	}

}
