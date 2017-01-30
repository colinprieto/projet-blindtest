package btb.blindtest.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import btb.blindtest.R;

public class PseudoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pseudo);
        final SharedPreferences sharedPref = PseudoActivity.this.getPreferences(Context.MODE_PRIVATE);
        if(!sharedPref.getString("pseudo", "").equalsIgnoreCase("")){
            startActivity(new Intent(PseudoActivity.this, MenuActivity.class));
        }
        final EditText et = (EditText) findViewById(R.id.editTextPseudo);
        Button buttonOK = (Button) findViewById(R.id.buttonOkPseudo);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("pseudo", et.getText().toString());
                editor.commit();
                startActivity(new Intent(PseudoActivity.this, MenuActivity.class));
            }
        });
    }
}
