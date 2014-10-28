package br.com.login.google;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.login.ofertame.R;
import br.com.login.ofertame.R.id;
import br.com.login.ofertame.R.layout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.Plus.PlusOptions;
import com.google.android.gms.plus.model.people.Person;

public class GoogleLoginMainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
	
	private static final int SIGN_IN_CODE = 56465;
	private GoogleApiClient googleApiClient;
	private ConnectionResult connectionResult;
	
	private boolean isConsentScreenOpened;
	private boolean isSignInButtonClicked;
	
    // VIEWS
    	private LinearLayout llContainerAll;
    	private ProgressBar pbContainer;
    	
    	private LinearLayout llLoginForm;
    	private SignInButton btSignInDefault;
    	
    	private LinearLayout llConnected;
    	private ImageView ivProfile;
    	private ProgressBar pbProfile;
    	private TextView tvLanguage;
    	private TextView tvName;
    	private TextView tvUrlProfile;
    	private TextView tvEmail;
    	private Button btSignOut;
    	private Button btRevokeAccess;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_principal_main);
		
		accessViews();
		
		googleApiClient = new GoogleApiClient.Builder(GoogleLoginMainActivity.this)
			.addConnectionCallbacks(GoogleLoginMainActivity.this)
			.addOnConnectionFailedListener(GoogleLoginMainActivity.this)
			.addApi(Plus.API, PlusOptions.builder().build())
			.addScope(Plus.SCOPE_PLUS_LOGIN)
			.build();
	}

	
	@Override
	public void onStart(){
		super.onStart();
		
		if(googleApiClient != null){
			googleApiClient.connect();
		}
	}
	
	
	@Override
	public void onStop(){
		super.onStop();
		
		if(googleApiClient != null && googleApiClient.isConnected()){
			googleApiClient.disconnect();
		}
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == SIGN_IN_CODE){
			isConsentScreenOpened = false;
			
			if(resultCode != RESULT_OK){
				isSignInButtonClicked = false;
			}
			
			if(!googleApiClient.isConnecting()){
				googleApiClient.connect();
			}
		}
	}
	

	// UTIL
		public void accessViews(){
			llContainerAll = (LinearLayout) findViewById(R.id.llContainerAll);
			pbContainer = (ProgressBar) findViewById(R.id.pbContainer);
			
			// NOT CONNECTED
				llLoginForm = (LinearLayout) findViewById(R.id.llLoginForm);
		    	btSignInDefault = (SignInButton) findViewById(R.id.btSignInDefault);
	    	
	    	// CONNECTED
		    	llConnected = (LinearLayout) findViewById(R.id.llConnected);
		    	ivProfile = (ImageView) findViewById(R.id.ivProfile);
		    	pbProfile = (ProgressBar) findViewById(R.id.pbProfile);
		    	tvLanguage = (TextView) findViewById(R.id.tvLanguage);
		    	tvName = (TextView) findViewById(R.id.tvName);
		    	tvUrlProfile = (TextView) findViewById(R.id.tvUrlProfile);
		    	tvEmail = (TextView) findViewById(R.id.tvEmail);
		    	btSignOut = (Button) findViewById(R.id.btSignOut);
		    	btRevokeAccess = (Button) findViewById(R.id.btRevokeAccess);
		    	
		    // LISTENER
		    	btSignInDefault.setOnClickListener(GoogleLoginMainActivity.this);
		    	btSignOut.setOnClickListener(GoogleLoginMainActivity.this);
		    	btRevokeAccess.setOnClickListener(GoogleLoginMainActivity.this);
		}
		
		public void showUi(boolean status, boolean statusProgressBar){
			if(!statusProgressBar){
				llContainerAll.setVisibility(View.VISIBLE);
				pbContainer.setVisibility(View.GONE);
				
				llLoginForm.setVisibility(status ? View.GONE : View.VISIBLE);
				llConnected.setVisibility(!status ? View.GONE : View.VISIBLE);
			}
			else{
				llContainerAll.setVisibility(View.GONE);
				pbContainer.setVisibility(View.VISIBLE);
			}
		}
		
		public void loadImage(final ImageView ivImg, final ProgressBar pbImg, final String urlImg){
			RequestQueue rq = Volley.newRequestQueue(GoogleLoginMainActivity.this);
			ImageLoader il = new ImageLoader(rq, new ImageLoader.ImageCache() {
				@Override
				public void putBitmap(String url, Bitmap bitmap) {
					pbImg.setVisibility(View.GONE);
				}
				@Override
				public Bitmap getBitmap(String url) {
					return null;
				}
			});
			pbImg.setVisibility(View.VISIBLE);
			il.get(urlImg, il.getImageListener(ivImg, pbImg.getId(), pbImg.getId()));
		}
		
		public void resolveSignIn(){
			if(connectionResult != null && connectionResult.hasResolution()){
				try {
					isConsentScreenOpened = true;
					connectionResult.startResolutionForResult(GoogleLoginMainActivity.this, SIGN_IN_CODE);
				}
				catch(SendIntentException e) {
					isConsentScreenOpened = false;
					googleApiClient.connect();
				}
			}
		}
		
		public void getDataProfile(){
			Person p = Plus.PeopleApi.getCurrentPerson(googleApiClient);
			
			if(p != null){
				//String id = p.getId();
				String name = p.getDisplayName();
				String language = p.getLanguage();
				String profileUrl = p.getUrl();
				String imageUrl = p.getImage().getUrl();
				String email = Plus.AccountApi.getAccountName(googleApiClient);
				
				//tvId.setText(id);
		    	tvLanguage.setText(language);
		    	tvName.setText(name);
		    	tvEmail.setText(email);
		    	
		    	tvUrlProfile.setText(profileUrl);
		    	Linkify.addLinks(tvUrlProfile, Linkify.WEB_URLS);
		    	
		    	Log.i("Script", "IMG before: "+imageUrl);
		    	imageUrl = imageUrl.substring(0, imageUrl.length() - 2)+"200";
		    	Log.i("Script", "IMG after: "+imageUrl);
		    	loadImage(ivProfile, pbProfile, imageUrl);
			}
			else{
				Toast.makeText(GoogleLoginMainActivity.this, "Dados não liberados", Toast.LENGTH_SHORT).show();
			}
		}

	// LISTENERS
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.btSignInDefault ){
				if(!googleApiClient.isConnecting()){
					isSignInButtonClicked = true;
					showUi(false, true);
					resolveSignIn();
				}
			}
			else if(v.getId() == R.id.btSignOut){
				if(googleApiClient.isConnected()){
					Plus.AccountApi.clearDefaultAccount(googleApiClient);
					googleApiClient.disconnect();
					googleApiClient.connect();
					showUi(false, false);
				}
			}
			else if(v.getId() == R.id.btRevokeAccess){
				if(googleApiClient.isConnected()){
					Plus.AccountApi.clearDefaultAccount(googleApiClient);
					Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient).setResultCallback(new ResultCallback<Status>(){
						@Override
						public void onResult(Status result) {
							finish();
						}
					});
				}
			}
		}


		@Override
		public void onConnected(Bundle connectionHint) {
			isSignInButtonClicked = false;
			showUi(true, false);
			getDataProfile();
		}


		@Override
		public void onConnectionSuspended(int cause) {
			googleApiClient.connect();
			showUi(false, false);
		}


		@Override
		public void onConnectionFailed(ConnectionResult result) {
			if(!result.hasResolution()){
				GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), GoogleLoginMainActivity.this, 0).show();
				return;
			}
			
			if(!isConsentScreenOpened){
				connectionResult = result;
				
				if(isSignInButtonClicked){
					resolveSignIn();
				}
			}
		}
}
