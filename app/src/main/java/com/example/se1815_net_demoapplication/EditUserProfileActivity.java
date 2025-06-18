package com.example.se1815_net_demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditUserProfileActivity extends AppCompatActivity {
    private EditText edtFirstNameProfile;
    private EditText edtLastNameProfile;
    private EditText edtEmailProfile;
    private Button btSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtFirstNameProfile = findViewById(R.id.edtFirstNameProfile);
        edtLastNameProfile = findViewById(R.id.edtLastNameProfile);
        edtEmailProfile = findViewById(R.id.edtEmailProfile);
        btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("firstName", edtFirstNameProfile.getText().toString());
            intent.putExtra("lastName", edtLastNameProfile.getText().toString());
            intent.putExtra("email", edtEmailProfile.getText().toString());
            intent.putExtra("address", "");
            intent.putExtra("mobileNo", "");
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}