package btb.blindtest.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import btb.blindtest.Manager.CommunicationManager;
import btb.blindtest.R;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        MainTask mt = new MainTask();
        mt.execute();
    }

    private class MainTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPref = CreateActivity.this.getPreferences(Context.MODE_PRIVATE);
            CommunicationManager c = CommunicationManager.getInstance(getApplicationContext());
            c.write("create");
            String[] response = c.read()[0].split("/");
            if(response[1].equalsIgnoreCase("ok")) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("partyID", response[0]);
                editor.commit();
                startActivity(new Intent(CreateActivity.this, GameActivity.class));
            }else {
                new Toast(getApplicationContext()).makeText(getApplicationContext(),
                        "Error while creating game", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }
}
