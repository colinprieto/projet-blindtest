package btb.blindtest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PseudoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pseudo);
        Button buttonOK = (Button) findViewById(R.id.buttonOkPseudo);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send pseudo to server
                //TODO save pseudo lacally
                startActivity(new Intent(PseudoActivity.this, ChooserActivity.class));
            }
        });
    }
}
