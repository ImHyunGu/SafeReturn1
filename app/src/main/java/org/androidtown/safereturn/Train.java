package org.androidtown.safereturn;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.androidtown.safereturn.resource.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by GIGABYTE on 2016-08-18.
 */
public class Train extends Activity {
    EditText T_name, T_line;
    TextView train_up1;
    TextView train_down1;
    TextView train_up2;
    TextView train_down2;
    TextView train_up3;
    TextView train_down3;
    RestClient restClient = new RestClient(this);
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        context = this;
        T_name = (EditText) findViewById(R.id.T_name);
        T_line = (EditText) findViewById(R.id.T_line);
        train_up1 = (TextView) findViewById(R.id.train_up1);
        train_down1 = (TextView) findViewById(R.id.train_down1);
        train_up2 = (TextView) findViewById(R.id.train_up2);
        train_down2 = (TextView) findViewById(R.id.train_down2);
        train_up3 = (TextView) findViewById(R.id.train_up3);
        train_down3 = (TextView) findViewById(R.id.train_down3);
    }

    public void onClick_T_search(View v) {
        Calendar calendar = Calendar.getInstance();
        String today = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK));
        if(today=="1")
            today="3";
        else if(today=="7")
            today="2";
        else
            today="1";



        String set1 = "1/5/" + T_name.getText().toString() + "/" + today + "/1/";//상행
        String set2 = "1/5/" + T_name.getText().toString() + "/" + today + "/2/";//하행
        restClient.get(set1, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("failure", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                try {
                    JSONObject first = object.getJSONObject("SearchLastTrainTimeByFRCodeService");
                    JSONArray data = first.getJSONArray("row");
                    // data.length();
                    JSONObject test = data.getJSONObject(0);
                    train_up1.setText(test.getString("LEFTTIME") + " / " + test.getString("SUBWAYENAME") + "행");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject first = object.getJSONObject("SearchLastTrainTimeByFRCodeService");
                    JSONArray data = first.getJSONArray("row");
                    // data.length();
                    JSONObject test = data.getJSONObject(1);
                    train_up2.setText(test.getString("LEFTTIME") + " / " + test.getString("SUBWAYENAME") + "행");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject first = object.getJSONObject("SearchLastTrainTimeByFRCodeService");
                    JSONArray data = first.getJSONArray("row");
                    // data.length();
                    JSONObject test = data.getJSONObject(2);
                    train_up3.setText(test.getString("LEFTTIME") + " / " + test.getString("SUBWAYENAME") + "행");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        restClient.get(set2, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("failure", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                try {
                    JSONObject first = object.getJSONObject("SearchLastTrainTimeByFRCodeService");
                    JSONArray data = first.getJSONArray("row");
                    // data.length();
                    JSONObject test = data.getJSONObject(0);
                    train_down1.setText(test.getString("LEFTTIME") + " / " + test.getString("SUBWAYENAME") + "행");;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject first = object.getJSONObject("SearchLastTrainTimeByFRCodeService");
                    JSONArray data = first.getJSONArray("row");
                    // data.length();
                    JSONObject test = data.getJSONObject(1);
                    train_down2.setText(test.getString("LEFTTIME") + " / " + test.getString("SUBWAYENAME") + "행");;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject first = object.getJSONObject("SearchLastTrainTimeByFRCodeService");
                    JSONArray data = first.getJSONArray("row");
                    // data.length();
                    JSONObject test = data.getJSONObject(2);
                    train_down3.setText(test.getString("LEFTTIME") + " / " + test.getString("SUBWAYENAME") + "행");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

    }

}
