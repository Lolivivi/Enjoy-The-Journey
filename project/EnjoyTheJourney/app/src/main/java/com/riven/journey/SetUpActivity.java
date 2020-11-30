package com.riven.journey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
/**
 * 设置中心
 * @author 田春雨
 */
public class SetUpActivity extends AppCompatActivity {
    private ToggleButton btn1;
    private ToggleButton btn2;
    private Button btnOut;
    private ImageView arrow1;//账号设置
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_focus);
        SharedPreferences sp = getSharedPreferences("setting", MODE_PRIVATE);
        phone = sp.getString("tel", "1");
        findViews();
        setListener();

    }

    private void findViews() {
        btn1 = findViewById(R.id.tb_toggle_button1);
        btn2 = findViewById(R.id.tb_toggle_button2);
        btnOut = findViewById(R.id.btn_out);
        arrow1 = findViewById(R.id.iv_arrow1);
    }

    private void setListener() {
        MyListener listener = new MyListener();
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btnOut.setOnClickListener(listener);
        arrow1.setOnClickListener(listener);

    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tb_toggle_button1:
                    if (btn1.isChecked()) {
                        Toast.makeText(getApplicationContext(), "开启成功！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.tb_toggle_button2:
                    if (btn2.isChecked()) {
                        Toast.makeText(getApplicationContext(), "开启成功！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btn_out:
                    //退出登录
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "退出登录成功！", Toast.LENGTH_LONG).show();
                    break;
                case R.id.iv_arrow1:
                    //账号设置
                    Intent intent1 = new Intent(getApplicationContext(), AccountSettingsActivity.class);
                    intent1.putExtra("phone", phone);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
