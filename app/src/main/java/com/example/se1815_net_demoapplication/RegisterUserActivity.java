package com.example.se1815_net_demoapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se1815_net_demoapplication.bean.UserBean;
import com.example.se1815_net_demoapplication.database.DatabaseHandler;

public class RegisterUserActivity extends AppCompatActivity {
    private EditText edtPassword, edtFirstName, edtLastName, edtEmail;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("User_prefs", MODE_PRIVATE);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtPassword = findViewById(R.id.edtRegisterPassword);
        edtEmail = findViewById(R.id.edtEmail);
        Button btRegister = findViewById(R.id.btSave);
        btRegister.setOnClickListener(v -> {
            if (edtFirstName.getText().toString().isEmpty()) {
                return;
            }
            if (edtPassword.getText().toString().isEmpty()) {
                return;
            }

            UserBean userBean = new UserBean(0, edtEmail.getText().toString(),
                    edtPassword.getText().toString(), edtFirstName.getText().toString(),
                    edtLastName.getText().toString(), edtEmail.getText().toString(),
                    "", "", "Staff");

            dbHandler = new DatabaseHandler(RegisterUserActivity.this);
            dbHandler.insertUser(userBean);
            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("username", edtUsername.getText().toString());
//            editor.putString("password", edtPassword.getText().toString());
            editor.apply();
        });
    }
}