package org.androidtown.safereturn;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 김 혁 진 on 2016-06-09.
 */
public class SafeRegist extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saferegist);

        Button regButton = (Button) findViewById(R.id.register);
        Button check1Button = (Button) findViewById(R.id.CheckBox1);
        Button check2Button = (Button) findViewById(R.id.CheckBox2);

        /*데이터를 저장할떄 보통 데이터베이스를 사용하는데, 이는 복잡하기 떄문에 간단한 데이터를
        저장할 경우에는 sharedpreferences를 사용한다. sharedpreferences는 어플을 지우면 같이지워진다.*/
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        EditText edit1 = (EditText) this.findViewById(R.id.text1);
        CheckBox check1 = (CheckBox)findViewById(R.id.CheckBox1);
        CheckBox check2 = (CheckBox)findViewById(R.id.CheckBox2);

        String text = pref.getString("editText", ""); // 저장된 값 불러오기
        Boolean chk1 = pref.getBoolean("check1", false);
        Boolean chk2 = pref.getBoolean("check2", true);
        edit1.setText(text);
        check1.setChecked(chk1);
        check2.setChecked(chk2);


        regButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                EditText edit1 = (EditText) findViewById(R.id.text1);
                CheckBox check1 = (CheckBox)findViewById(R.id.CheckBox1);
                CheckBox check2 = (CheckBox)findViewById(R.id.CheckBox2);
                editor.putString("editText", edit1.getText().toString());
                editor.putBoolean("check1", check1.isChecked());
                editor.putBoolean("check2", check2.isChecked());
                editor.commit();
                Toast.makeText(getBaseContext(), "등록되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //체크박스 체크.
        check1Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                CheckBox check1 = (CheckBox)findViewById(R.id.CheckBox1);
                CheckBox check2 = (CheckBox)findViewById(R.id.CheckBox2);
                if(check1.isChecked())
                    check2.setChecked(false);
                else
                    check1.setChecked(true);


            }
        });
        check2Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                CheckBox check1 = (CheckBox)findViewById(R.id.CheckBox1);
                CheckBox check2 = (CheckBox)findViewById(R.id.CheckBox2);
                if(check2.isChecked())
                    check1.setChecked(false);
                else
                    check2.setChecked(true);
                //stopService(new Intent("android.term"));
            }
        });

    }

    public void onStop() { // 어플리케이션이 화면에서 사라질때
        super.onStop();
        SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE); // UI 상태를 저장합니다.
        SharedPreferences.Editor editor = pref.edit(); // Editor를 불러옵니다.
        EditText edit1 = (EditText) findViewById(R.id.text1);
        CheckBox check1 = (CheckBox)findViewById(R.id.CheckBox1);
        CheckBox check2 = (CheckBox)findViewById(R.id.CheckBox2);
        editor.putString("editText", edit1.getText().toString()); // edit text에
        editor.putBoolean("check1", check1.isChecked());
        editor.putBoolean("check2", check2.isChecked());
        // 값저장
        editor.commit(); // 저장합니다.
    }
}
