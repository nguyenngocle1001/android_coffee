package com.myclass.shopcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    CheckBox cbShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<span style='color: #ffffff'>Thêm tài khoản<span>", Html.FROM_HTML_MODE_COMPACT));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        cbShowPassword = (CheckBox) findViewById(R.id.cbShowPassword);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void add(View view){
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(username.equals(""))
            Toast.makeText(this, "Bạn chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
        else if (password.equals(""))
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("User_Name", username);
            contentValues.put("User_Pass", password);

            long result = MainActivity._DATABASE.insert(Database.getInstance().TBUSERS, null, contentValues);
            if(result == -1){
                Toast.makeText(this, "Thêm thất bại, tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
        }

    }
    public void clear(View view){
        edtUsername.setText("");
        edtPassword.setText("");
    }

    public void showPassword(View view) {
        if (cbShowPassword.isChecked()) {
            edtPassword.setTransformationMethod(null);
            edtPassword.setSelection(edtPassword.getText().length());
        } else {
            edtPassword.setTransformationMethod(new PasswordTransformationMethod());
            edtPassword.setSelection(edtPassword.getText().length());
        }
    }
}