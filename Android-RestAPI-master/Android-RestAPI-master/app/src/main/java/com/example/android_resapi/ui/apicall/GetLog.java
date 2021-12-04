package com.example.android_resapi.ui.apicall;

import static java.lang.Float.parseFloat;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.example.android_resapi.R;
import com.example.android_resapi.httpconnection.GetRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class GetLog extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    public GetLog(Activity activity, String urlStr) {
        super(activity);
        this.urlStr = urlStr;
    }

    @Override
    protected void onPreExecute() {
        try {

            TextView textView_Date1 = activity.findViewById(R.id.textView_date1);
            TextView textView_Time1 = activity.findViewById(R.id.textView_time1);
            TextView textView_Date2 = activity.findViewById(R.id.textView_date2);
            TextView textView_Time2 = activity.findViewById(R.id.textView_time2);

            String params = String.format("?from=%s:00&to=%s:00",textView_Date1.getText().toString()+textView_Time1.getText().toString(),
                    textView_Date2.getText().toString()+textView_Time2.getText().toString());

            Log.i(TAG,"urlStr="+urlStr+params);
            url = new URL(urlStr+params);

        } catch (MalformedURLException e) {
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        TextView message = activity.findViewById(R.id.message2);
        message.setText("조회중...");
    }


    @Override
    protected void onPostExecute(String jsonString) {
        TextView message = activity.findViewById(R.id.message2);
        if (jsonString == null) {
            message.setText("로그 없음");
            return;
        }
        message.setText("");
        ArrayList<Tag> arrayList = getArrayListFromJSONString(jsonString);

        LineChart lineChart = (LineChart) activity.findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();

        ArrayList<String> labels = new ArrayList<>();
        float ff;

        for(int i = 0; i < arrayList.size(); i++){
            labels.add(arrayList.get(i).timestamp.substring(12, 17));
            ff = (Float.parseFloat(arrayList.get(i).timestamp.substring(12, 14))*100+Float.parseFloat(arrayList.get(i).timestamp.substring(15, 17)))/100;
            Log.e(TAG, "i: "+ff+"\n");
            //String str = arrayList.get(i).timestamp.substring(12, 17);

            entries.add(new Entry(ff, Float.parseFloat(arrayList.get(i).distance)));
        }

        LineDataSet set1;
        set1 = new LineDataSet(entries, "DataSet 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        //XAxis xAxis = lineChart.getXAxis();
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));


        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        lineChart.invalidate();
        // set data
        lineChart.setData(data);

//        final ArrayAdapter adapter = new ArrayAdapter(activity,
//                android.R.layout.simple_list_item_1,
//                arrayList.toArray());

//        ListView txtList = activity.findViewById(R.id.logList);
//        txtList.setAdapter(adapter);
//        txtList.setDividerHeight(10);
    }

    protected ArrayList<Tag> getArrayListFromJSONString(String jsonString) {
        ArrayList<Tag> output = new ArrayList();

        try {
            // 처음 double-quote와 마지막 double-quote 제거
            jsonString = jsonString.substring(1,jsonString.length()-1);
            // \\\" 를 \"로 치환
            jsonString = jsonString.replace("\\\"","\"");

            Log.i(TAG, "jsonString="+jsonString);

            JSONObject root = new JSONObject(jsonString);
            JSONArray jsonArray = root.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                Tag thing = new Tag(jsonObject.getString("distance"),
                        jsonObject.getString("LED"),
                        jsonObject.getString("timestamp"));

                output.add(thing);
            }

        } catch (JSONException e) {
            //Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }

    class Tag {
        String distance;
        String LED;
        String timestamp;

        public Tag(String dist, String led, String time) {
            distance = dist;
            LED = led;
            timestamp = time;
        }

        public String toString() {
            return String.format("[%s] Distance: %s, LED: %s", timestamp, distance, LED);
        }
    }
}