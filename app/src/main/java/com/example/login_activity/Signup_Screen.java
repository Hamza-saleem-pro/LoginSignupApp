package com.example.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        TextView loginlink = findViewById(R.id.login_link);
        EditText U_name, password, email, confirm_pswrd;
        Button signup_btn;

        U_name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        confirm_pswrd = findViewById(R.id.confirm_password);
        signup_btn = findViewById(R.id.signup_btn);

        My_Db_helper db = new My_Db_helper(this);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = U_name.getText().toString();
                String pass = password.getText().toString();
                String email_id = email.getText().toString();
                String confirm_pass = confirm_pswrd.getText().toString();

                if (name.isEmpty() || pass.isEmpty() || email_id.isEmpty() || confirm_pass.isEmpty()) {
                    Toast.makeText(Signup_Screen.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(confirm_pass)) {
                    Toast.makeText(Signup_Screen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if (
                        pass.length() < 6 ||
                                !pass.matches(".*[a-zA-Z].*") ||
                                !pass.matches(".*[0-9].*") ||
                                !pass.matches(".*[!@#$%^&*()_+=<>?].*")
                )
                {
                    Toast.makeText(Signup_Screen.this, "Password must be 6+ chars & include: letter, number & symbol", Toast.LENGTH_LONG).show();

                }


                else {
                    if (db.check_user(name, pass)) {
                        Toast.makeText(Signup_Screen.this, "User already exists", Toast.LENGTH_SHORT).show();
                        U_name.setText("");
                        password.setText("");
                        email.setText("");
                        confirm_pswrd.setText("");
                        return;
                    }

                    Boolean check_user = db.add_user(name, email_id, pass);
                    if (check_user) {
                        Toast.makeText(Signup_Screen.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Signup_Screen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Signup_Screen.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        U_name.setText("");
                        password.setText("");
                        email.setText("");
                        confirm_pswrd.setText("");
                    }
                }
            }
        });

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup_Screen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
