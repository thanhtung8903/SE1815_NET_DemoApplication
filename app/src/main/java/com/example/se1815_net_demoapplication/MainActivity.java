package com.example.se1815_net_demoapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se1815_net_demoapplication.bean.UserBean;
import com.example.se1815_net_demoapplication.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";
    private EditText edtUsername;
    private EditText edtPassword;
    private Spinner spinnerCampus;
    private RadioButton radioButtonStaff;
    private RadioButton radioButtonManager;
    private CheckBox checkBoxRememberMe;
    private Button btLogin;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHandler = new DatabaseHandler(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        spinnerCampus = findViewById(R.id.spinnerCampus);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.campus,
                android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampus.setAdapter(arrayAdapter);
        spinnerCampus.setOnItemSelectedListener(this); // Set the onItemSelectedListener
        radioButtonStaff = findViewById(R.id.radioButtonStaff);
        radioButtonManager = findViewById(R.id.radioButtonManager);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String campus = spinnerCampus.getSelectedItem().toString();
        String role = radioButtonStaff.isChecked() ? "Staff" : "Manager";
        boolean rememberMe = checkBoxRememberMe.isChecked();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        UserBean loggedInUser = databaseHandler.login(username, password);

        if (loggedInUser != null) {
            Intent intent = new Intent(MainActivity.this, ViewUserProfileActivity.class);
            intent.putExtra("username", loggedInUser.getUsername());
            intent.putExtra("password", loggedInUser.getPassword());
            intent.putExtra("campus", loggedInUser.getCampus());
            intent.putExtra("role", loggedInUser.getRole());
            intent.putExtra("firstName", loggedInUser.getFirstName());
            intent.putExtra("lastName", loggedInUser.getLastName());
            intent.putExtra("email", loggedInUser.getEmail());
            intent.putExtra("phone", loggedInUser.getPhone());
            intent.putExtra("rememberMe", rememberMe);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng",
                    Toast.LENGTH_SHORT).show();
        }

//        UserBean user = dbHandler.
//        SharedPreferences sharedPreferences = getSharedPreferences("User_prefs", MODE_PRIVATE);
//        String prefUsername = sharedPreferences.getString("username", "");
//        String prefPassword = sharedPreferences.getString("password", "");
//        if (!username.equals(prefUsername) || !password.equals(prefPassword)) {
//            Toast.makeText(MainActivity.this, "Invalid username or password",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }

//        Intent intent = new Intent(MainActivity.this, ViewUserProfileActivity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("password", password);
//        intent.putExtra("campus", campus);
//        intent.putExtra("role", role);
//        intent.putExtra("rememberMe", rememberMe);
//        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCampus = parent.getItemAtPosition(position).toString();
        Toast.makeText(MainActivity.this, "Selected Campus: "
                + selectedCampus, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Selected Campus: " + selectedCampus);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openRegisterActivity(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
        startActivity(intent);
    }
}