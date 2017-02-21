package com.pennapps.arpitsabherwal.journal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by arpitsabherwal on 23/01/16.
 */
public class RegistrationActivity extends Activity {
    Button btSubmit;
    EditText etName;
    EditText etEmail;
    EditText etPhone;
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_registration);

        etName=(EditText)findViewById(R.id.etName);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPhone=(EditText)findViewById(R.id.etPhone);
        btSubmit=(Button)findViewById(R.id.btSubmit);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegistrationActivity.this,AutoBioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

            }
        });

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/LibreBaskerville-Regular.otf");
        tvTitle.setTypeface(type);

    }
}
