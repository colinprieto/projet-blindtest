package btb.blindtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        Button buttonTest = (Button) findViewById(R.id.buttonTest);
        Button buttonJoin = (Button) findViewById(R.id.buttonJoin);
        Button buttonCreate = (Button) findViewById(R.id.buttonCreate);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooserActivity.this, TestActivity.class));
            }
        });
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooserActivity.this, GameActivity.class));
            }
        });
    }
}
