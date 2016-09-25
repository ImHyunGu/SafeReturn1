package org.androidtown.safereturn;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 김 혁 진 on 2016-06-09.
 */
public class Taxi extends Activity {

    public String phonenumber;
    public String taxinumber;
    EditText edit1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taxi);

        Button sendButton = (Button) this.findViewById(R.id.send);
        edit1 = (EditText) this.findViewById(R.id.text1);


        sendButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

                phonenumber = pref.getString("editText", ""); // 저장된 값 불러오기
                if (phonenumber.isEmpty()) {
                    Toast.makeText(getBaseContext(), "전화번호가 등록되어있지않습니다",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    taxinumber = edit1.getText().toString();
                    //Toast.makeTexlt(getApplicationContext(),taxinumber,Toast.LENGTH_SHORT).show();
                    SmsManager smsmanager = SmsManager.getDefault();
                    smsmanager.sendTextMessage(phonenumber, null, taxinumber + "번호의 택시를 탔습니다.", null, null);
                    Toast.makeText(getApplicationContext(), "메세지를 보냈습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


















