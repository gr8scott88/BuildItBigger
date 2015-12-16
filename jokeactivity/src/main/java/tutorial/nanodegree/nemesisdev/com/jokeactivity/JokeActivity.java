package tutorial.nanodegree.nemesisdev.com.jokeactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static String JOKE_KEY = "JOKEKEY";
    private TextView mJokeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mJokeText = (TextView)findViewById(R.id.funny_joke);
        Intent intent = getIntent();
        String passed_joke = intent.getStringExtra(JOKE_KEY);
        if (passed_joke != null){
            mJokeText.setText(passed_joke);
        }else{
            mJokeText.setText("Joke not retrieved");
        }
    }
}
