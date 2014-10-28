package br.com.login.twitter;


import br.com.login.ofertame.R;
import br.com.login.ofertame.R.layout;
import br.com.login.ofertame.R.menu;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class TwitterAndroidLoginMainActivity extends Activity {

	private final String TAG = TwitterAndroidLoginMainActivity.class.toString();
	
	private static final String CONSUMER_KEY = "Hu0FDwgmWbqHYTQlUZBLF2e72";
	private static final String CONSUMER_SECRET = "mlJYH6gIip2UF2Z1ZLlB4PAhHfz6UhabqdRfpvzNVs46bOXbRM";
	
	private static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static final String AUTH_URL = "https://api.twitter.com/oauth/authorize";
	
	private static final CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
			CONSUMER_KEY, CONSUMER_SECRET);
	private static final CommonsHttpOAuthProvider provider = new CommonsHttpOAuthProvider(
			REQUEST_URL, ACCESS_TOKEN_URL, AUTH_URL);
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_android_login_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_twitter_android_login_main, menu);
        return true;
    }
    
    
    public void twitterLogin(View v){
    	new TwitterAndroidAsyncTask(this, consumer, provider)
    		.execute(TwitterAndroidAsyncTask.GET_ACCESS);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == TwitterWebView.WEBVIEW_RETURN_CODE){
    		getIntent().setData(data.getData());
    		new TwitterAndroidAsyncTask(this, consumer, provider)
    			.execute(TwitterAndroidAsyncTask.GRANT_ACCESS);
    	}
    }
}
