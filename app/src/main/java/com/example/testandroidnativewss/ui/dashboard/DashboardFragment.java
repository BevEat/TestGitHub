package com.example.testandroidnativewss.ui.dashboard;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testandroidnativewss.LatLng;
import com.example.testandroidnativewss.LatLngDao;
import com.example.testandroidnativewss.MainActivity;
import com.example.testandroidnativewss.R;
import com.example.testandroidnativewss.databinding.FragmentDashboardBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    TextView textLat;
    TextView textLong;
    TextView textGet;
    TextView textPost;

    LatLng latlng = null;
    private LatLngDao latlngDao;
    private AddLatLngTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latlngDao = new LatLngDao(getActivity());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //WebView Start
        WebView mWebView = (WebView) root.findViewById(R.id.wv_OneMap);
        mWebView.loadUrl("https://developers.onemap.sg/commonapi/staticmap/getStaticImage?layerchosen=default&lat=1.30980&lng=103.77750&zoom=14&height=512&width=400&points=[1.30980,103.77750,\"175,50,0\",\"A\"]|[1.31141,103.77864,\"255,255,178\",\"B\"]|[1.30628,103.79036,\"175,50,0\",\"C\"]");

            /// Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

            // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        //WebView End

        //Set text Lat Long Start
        textLat = (TextView) root.findViewById(R.id.textLat);
        textLong = (TextView) root.findViewById(R.id.textLong);

        Button btnLatLong = (Button) root.findViewById(R.id.btnLatLong);
        btnLatLong.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                textLat.setText(Double.toString(MainActivity.lat));
                textLong.setText(Double.toString(MainActivity.lng));

                setLatLng();

                task = new AddLatLngTask(getActivity());
                task.execute((Void) null);
            }
        });
        //Set text Lat Long End

        // GET Start
        Button btnGet = (Button) root.findViewById(R.id.btnGet);
        textGet = (TextView) root.findViewById(R.id.textGet);

        btnGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                //Some url endpoint that you may have
                String getUrl = "https://prod.openapipaas.com/api/ecommerce/getLocations?lat=1.30980&lng=103.77750&orgid=a429bcf9-aedf-42b0-8baf-2fa4cb183381&accesskey=kziv4Ri3SPv05a3gFjok78wrwWC3OF5T";
                //String to place our result in
                String resultGet;
                //Instantiate new instance of our class
                HttpGetRequest getRequest = new HttpGetRequest();
                //Perform the doInBackground method, passing in our url
                try {
                    resultGet = getRequest.execute(getUrl).get();
                    textGet.setText(resultGet);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        // GET End

        // POST Start
        Button btnPost = (Button) root.findViewById(R.id.btnPost);
        textPost = (TextView) root.findViewById(R.id.textPost);

        btnPost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                //Some url endpoint that you may have
                String postUrl = "https://prod.openapipaas.com/api/wss/userLogin";
                //String to place our result in
                String resultPost;
                //Instantiate new instance of our class
                Map<String, String> postData = new HashMap<>();
                postData.put("email", "devops1@worldskills.sg");
                postData.put("password", "P@ssw0rd");
                postData.put("accesskey", "kziv4Ri3SPv05a3gFjok78wrwWC3OF5T");
                HttpPostRequest task = new HttpPostRequest(postData);

                try {
                    resultPost = task.execute(postUrl).get();
                    textPost.setText(resultPost);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // POST End

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }

    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        // This is the JSON body of the post
        String result;
        String inputLine;
        JSONObject postData;
        // This is a constructor that allows you to pass in the JSON body
        public HttpPostRequest(Map<String, String> postData) {
            if (postData != null) {
                this.postData = new JSONObject(postData);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //Create a URL object holding our url
            try {
                // This is getting the url from the string we passed in
                URL url = new URL(params[0]);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("POST");

                // Send the post body
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(postData.toString());
                    writer.flush();
                }

                int statusCode = urlConnection.getResponseCode();
                if (statusCode == 200) {

                    InputStreamReader streamReader = new
                            InputStreamReader(urlConnection.getInputStream());
                    //Create a new buffered reader and String Builder
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    //Check if the line we are reading is not null
                    while ((inputLine = reader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }
                    //Close our InputStream and Buffered reader
                    reader.close();
                    streamReader.close();
                    //Set our result equal to our stringBuilder
                    result = stringBuilder.toString();

                    // From here you can convert the string to JSON with whatever JSON parser you like to use
                } else {
                    // Status code is not 200
                    // Do something to handle the error
                    result="postFailed";
                }

            } catch (Exception e) {
                e.printStackTrace();
                result = null;
            }
            return result;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }

    private void setLatLng() {
        latlng = new LatLng();
        latlng.setLat(Double.parseDouble(textLat.getText().toString()));
        latlng.setLng(Double.parseDouble(textLat.getText().toString()));
    }

    public class AddLatLngTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddLatLngTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = latlngDao.save(latlng);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Lat Long Saved",
                            Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}