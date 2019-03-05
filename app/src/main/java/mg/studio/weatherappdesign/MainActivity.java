package mg.studio.weatherappdesign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initial refresh the page
        new DownloadUpdate().execute();
        setCityName("ChongQin");
            //get the system time
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy/MM/dd   HH:mm:ss");
        SimpleDateFormat   formatter1=new SimpleDateFormat("EEEE");
        Date curDate=new Date(System.currentTimeMillis());
        String str=formatter.format(curDate);
        String weekshow=formatter1.format(curDate);
        Log.d("curdate time:",str);
        TextView text_curtime=(TextView)this.findViewById(R.id.tv_date);
        TextView text_titleweekShow=(TextView)this.findViewById(R.id.titleweekShow);
        text_curtime.setText(str);
        text_titleweekShow.setText(weekshow);


    }
   //get the net work_status;
    private boolean isNetworkConnected() {

        ConnectivityManager manager1 = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager1.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }

    public String getWeatherMain(JSONArray jsonArray,int positon){
        try{
            JSONObject someday=jsonArray.getJSONObject(positon);
            String weatherDescribe=someday.optString("weather").toString();

            JSONArray tmp=new JSONArray(weatherDescribe);
            JSONObject weatherDescribeMain=tmp.getJSONObject(0);
            String result=weatherDescribeMain.optString("main");
            return result;
        }catch (JSONException e){
           //return e.toString();
            Log.i("异常:",e.toString());
        }
        return null;
    }

    public void forcastTheWeather(String str,int i){
        ImageView  firstview=(ImageView)this.findViewById(R.id.img_weather_condition);
        ImageView secondview=(ImageView)this.findViewById(R.id.scendday);
        ImageView thirdview=(ImageView)this.findViewById(R.id.thirdday);
        ImageView forthview=(ImageView)this.findViewById(R.id.forthday);
        ImageView fifthview=(ImageView)this.findViewById(R.id.fifthday);
        if(i==0){
            switch (str){
                case"Rain":
                    firstview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case"Clear":
                    firstview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case"Clouds":
                    firstview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.windy_small));
                    break;
                default:
            }
        }
        if(i==2){
            switch (str){
                case"Rain":
                    secondview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case"Clear":
                    secondview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case"Clouds":
                    secondview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.windy_small));
                    break;
                default:

            }
        }
        if(i==4){
            switch (str){
                case"Rain":
                    thirdview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case"Clear":
                    thirdview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case"Clouds":
                    thirdview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.windy_small));
                    break;
                default:

            }
        }
        if(i==6){
            switch (str){
                case"Rain":
                    forthview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case"Clear":
                    forthview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case"Clouds":
                    forthview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.windy_small));
                    break;
                default:

            }
        }
        if(i==8){
            switch (str){
                case"Rain":
                    fifthview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case"Clear":
                    fifthview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case"Clouds":
                    fifthview.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.windy_small));
                    break;
                default:

            }
        }
    }

    //this function does't work
    public String getJSONBuffer(String stringurl){
        String stringUrl=stringurl;
        HttpURLConnection urlConnection = null;
        BufferedReader reader;
        try {
            URL url = new URL(stringUrl);

            // Create the request to get the information from the server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Mainly needed for debugging
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            Log.d("buffer.toString():",buffer.toString());
            return buffer.toString();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void btnClick(View view) {
        //is net work
        //update system time
        //SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("dd/MM/yyyy");
        if(!isNetworkConnected()){
            TextView isnetwork=(TextView)this.findViewById(R.id.isnetwork);
            isnetwork.setText("There is not network!!!");
        }
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy/MM/dd   HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String str=formatter.format(curDate);
        Log.d("curdate time:",str);
        TextView text_curtime=(TextView)this.findViewById(R.id.tv_date);
        text_curtime.setText(str);

        //change icon
        //String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"D:/GitWareHouse/Android-Appliaction_copy/weather-application/app/src/main/res/drawable/sunny_small.png";
//        Bitmap bitmap=BitmapFactory.decodeFile(path);
//        curpicture.setImageBitmap(bitmap);
//        Uri uri=Uri.fromFile(new File(path));
//        curpicture.setImageURI(uri);

        //set next four days
        SimpleDateFormat format=new SimpleDateFormat("EEEE");
        Date curdate=new Date(System.currentTimeMillis());
        String curstr=format.format(curDate);
        int numberInWeek=getNumberInWeek(curstr);
        Log.d("curstr",curstr);
        Log.d("curstr inter",String.valueOf(numberInWeek));
        setSpecificTime(numberInWeek);


    }
    public int getNumberInWeek(String str){
        int number=0;
        switch (str){
            case "Sunday":
                number=7;
                break;
            case"Saturday":
                number=6;
                break;
            case"Friday":
                number=5;
                break;
            case"Thursday":
                number=4;
                break;
            case"Wednesday":
                number=3;
                break;
            case"Tuesday":
                number=2;
                break;
            case"Monday":
                number=1;
                break;
                default:
                    break;
        }
        return number;
    }
    public String getStringInWeek(int t){
        String whatday=null;
        switch (t){
            case 0:
                whatday="SUN";
                break;
            case 1:
                whatday="MON";
                break;
            case 2:
                whatday="TUE";
                break;
            case 3:
                whatday="WED";
                break;
            case 4:
                whatday="THU";
                break;
            case 5:
                whatday="FRI";
                break;
            case 6:
                whatday="SAT";
                break;
                default:
                    break;
        }
        return whatday;
    }
    public void setSpecificTime(int i){
        int time2=i+1;
        time2=time2%7;
        String time2string=getStringInWeek(time2);
        Log.i("time2：",time2string);
        TextView time2time=(TextView)this.findViewById(R.id.time2);
        time2time.setText(time2string);

        int time3=i+2;
        time3=time3%7;
        String time3string=getStringInWeek(time3);
        Log.i("time3：",time3string);
        TextView time3time=(TextView)this.findViewById(R.id.time3);
        time3time.setText(time3string);

       int time4=i+3;
       time4=time4%7;
       String time4string=getStringInWeek(time4);
       Log.i("time4：",time4string);
       TextView time4time=(TextView)this.findViewById(R.id.time4);
       time4time.setText(time4string);

       int time5=i+4;
       time5=time5%7;
       String time5string=getStringInWeek(time5);
       TextView time5time=(TextView)this.findViewById(R.id.time5);
       time5time.setText(time5string);
    }
    public void setCityName(String cityName){
        TextView cityname=(TextView)this.findViewById(R.id.tv_location);
        cityname.setText(cityName);
    }

    private class DownloadUpdate extends AsyncTask<String, Void, String> {

        public JSONArray wholearray;
        @Override
        protected String doInBackground(String... strings) {
//          String stringUrl = "http://mpianatra.com/Courses/info.txt";
            String stringUrl="http://api.openweathermap.org/data/2.5/forecast?q=Chongqing,cn&mode=json&APPID=aa3d744dc145ef9d350be4a80b16ecab";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
               // Log.d("buffer is:",buffer.toString());
                try{
                    JSONObject root=new JSONObject(buffer.toString());
                    String  list=root.optString("list").toString();
//                    String  city=root.optString("city").toString();
//                    JSONObject cityname=new JSONObject(city);
//                    String cityName=cityname.optString("name");

                    //change list to JSONArray in order to get a value in the Array
                    JSONArray arraylist=new JSONArray(list);
                    wholearray=arraylist;
                    //get the value in the Array and the index is 0
                    JSONObject currentday=arraylist.getJSONObject(0);
                    String main=currentday.optString("main").toString();
                    JSONObject temp=new JSONObject(main);

                    //get the temperature
                    String result=temp.optString("temp");
                    //Float data=Float.parseFloat(result);
                    Double data=Double.parseDouble(result);
                    data=data-273.15;
                    int appear=data.intValue();
                    result=String.valueOf(appear);
                    //get the location
                    //setCityName(cityName);
                    return  result;
                }catch (JSONException e){
                    Log.i("异常:",e.toString());
                }

                //return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String temperature) {
            //Update the temperature displayed
            //JSONObject root=new JSONObject(temperature.toString());
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);

            //forcast the folloing four days
            JSONArray arraylist=wholearray;
            String crntday=getWeatherMain(arraylist,0);
            String secondday=getWeatherMain(arraylist,8);
            String thirdday=getWeatherMain(arraylist,16);
            String forthday=getWeatherMain(arraylist,24);
            String fifthday=getWeatherMain(arraylist,32);
            Log.d("currntday",crntday);
            Log.d("secondday:",secondday);
            Log.d("thirdday",thirdday);
            Log.d("forthday",forthday);
            Log.d("fifthday",fifthday);
            forcastTheWeather(crntday,0);
            forcastTheWeather(secondday,2);
            forcastTheWeather(thirdday,4);
            forcastTheWeather(forthday,6);
            forcastTheWeather(fifthday,8);
        }

    }
}
