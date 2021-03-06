package edu.msu.cse.monopoly.scribbles;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class DrawActivity extends Activity {
    private static final String COLOR = "color";
    private static final String PLAYER1 = "player1";
    private static final String PLAYER2 = "player2";
    private static final String PLAYER1SCORE = "player1Score";
    private static final String PLAYER2SCORE = "player2Score";
    private static final String HINT = "hint";
    private static final String TOPIC = "topic";
    
    /**
     * Request code when selecting a color
     */
    private static final int SELECT_COLOR = 1;

    /**
     * The thickness choice spinner
     */
    private Spinner spinner;
    
    private ArrayList<String> possibleTopics = new ArrayList<String>();
    private int player1Score;
    private int player2Score;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw);
		
		possibleTopics.add(getString(R.string.animal));
		possibleTopics.add(getString(R.string.building));
		possibleTopics.add(getString(R.string.object));
		possibleTopics.add(getString(R.string.action));
		possibleTopics.add(getString(R.string.msu));
		
        Bundle bundle = getIntent().getExtras();

        // Displays player names and their scores
        // bundle.getInt returns 0 by default, so coming from the welcome screen is not a problem
        
        player1Score = bundle.getInt(PLAYER1SCORE);
        player2Score = bundle.getInt(PLAYER2SCORE);
        if(bundle.getString(PLAYER1)!= null)
        {
            TextView player1ScoreText = (TextView) findViewById(R.id.player1ScoreText);
            
            if (bundle.getString(PLAYER1).equals("")){ // User enters nothing
            	player1ScoreText.setText(getString(R.string.player1) + ": " + Integer.toString(player1Score));
            }
            else{
            player1ScoreText.setText(bundle.getString(PLAYER1)+ ": " + Integer.toString(player2Score));
            }
        }
        
        if(bundle.getString(PLAYER2)!= null){
        	
            TextView player2ScoreText = (TextView) findViewById(R.id.player2ScoreText);
            
            if (bundle.getString(PLAYER2).equals("")){ // User enters nothing
            	player2ScoreText.setText(getString(R.string.player2) + ": " + Integer.toString(bundle.getInt(PLAYER2SCORE)));
            }
            else{
            player2ScoreText.setText(bundle.getString(PLAYER2)+ ": " + Integer.toString(bundle.getInt(PLAYER2SCORE)));
            }
        }

        // Pick a random topic
        Random rand = new Random();
        TextView topicText = (TextView) findViewById(R.id.topicText);
        topicText.setText(getString(R.string.topic) + " " + possibleTopics.get(rand.nextInt(possibleTopics.size())));
        
        spinner = (Spinner) findViewById(R.id.spinnerThickness);
        
        /* NOTE: spinner currently filled with some random default values.
         */
        
        /*
         * Set up the spinner
         */

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.thickness_spinner, android.R.layout.simple_spinner_item);
        
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_draw, menu);
		return true;
	}

    /**
     * Handle a Done button press. Should start the guessing activity.
     * @param view
     */
    public void onDone(View view) {
    	// Start the guessing activity
    	// Change DrawActivity.class to the guess activity class when it is created.
    	
		Intent intent = new Intent(this, GuessActivity.class);
		
		// Put the artist's hint in the bundle
		EditText hintBox = (EditText) findViewById(R.id.hintBox);
		String hint = hintBox.getText().toString();
		intent.putExtra(HINT, hint);
		
		// Put the player's scores in the bundle
		intent.putExtra(PLAYER1SCORE, player1Score);
		intent.putExtra(PLAYER2SCORE, player2Score);
		
		// Put the topic string into the bundle
		TextView topic = (TextView) findViewById(R.id.topicText);
		intent.putExtra(TOPIC, topic.getText().toString());
		
		/* NEED TO PUT THE ARRAY OF LINES IN DRAWINGVIEW INTO THE BUNDLE! */
		
		// Start the guessing activity
		startActivity(intent);
    	
    }
    
    /**
     * Handle a Color button press
     * @param view
     */
    public void onColor(View view) {
    	// Start the color select activity
		Intent intent = new Intent(this, ColorSelectActivity.class);
		startActivityForResult(intent, SELECT_COLOR); 
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SELECT_COLOR && resultCode == Activity.RESULT_OK) {
            @SuppressWarnings("unused")// IMPORTANT!! Take this out when the below code is uncommented.
            
			int color = data.getIntExtra(COLOR, 0);
            
            // set the member variable in the draw view to whatever color we get from above.
            //drawView.setColor(color);
        }
	}
}
