package com.ibm.watson.sample.weatherclassifier;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button control_askBtn = null;
    TextView control_resultTextView = null;
    EditText control_questionText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        control_askBtn = (Button)findViewById(R.id.askButton);
        control_resultTextView = (TextView)findViewById(R.id.result);
        control_questionText = (EditText)findViewById(R.id.question);
        //ping MFP server
        //WLClient.createInstance(this);
        //WLClient.getInstance().connect(new WLResponseListener() {
        //    @Override
        //    public void onSuccess(WLResponse wlResponse) {

        //    }

        //    @Override
        //    public void onFailure(WLFailResponse wlFailResponse) {

        //    }
        //});

        control_askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = control_questionText.getText().toString();
                HttpStuff httpStuff = new HttpStuff(MainActivity.this);
                AsyncTask doasync= httpStuff.execute(question);
            }
        });
    }


    public void updateResult(final String result)
    {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                control_resultTextView.setText(result);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
