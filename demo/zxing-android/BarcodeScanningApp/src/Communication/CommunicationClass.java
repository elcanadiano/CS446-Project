package Communication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CommunicationClass{
		//verification before request
	private String tag = "CommClass";
	//most commonly used for setting up and starting a progress dialog
	InputStream inputStream = null;
	String result = ""; //result of JSON
	HttpResponse httpResponse2;
	private boolean done = false;
	public CommunicationClass(){
		String uri = new String("http://buymybookapp.com/api/test/test2");
        new DownloadJSON().execute(uri , null, null);
	}
	
	
	
	public void queryByISBN(String isbn){
		
	}//query
	/*
	 * Input: might use DATAJSON.java
	 * Output:
	 */
	public void queryByObject(Object o){
		
	}
	/*
	 * Input: Any Java Object
	 * Output: A JSON String
	 * Turns java object into JSON
	 */
	public String buildJSONFromObject(Object o){
		//Gson gson = new GsonBuilder().create();
		//Map<String, Object> data = new HashMap<String,Object>();
		Gson gson = new Gson();
		String json = gson.toJson(o);
		return json;
	}
	//not done
	   <T> Object objectFromJson(String json) throws JSONException{
		Object o = new Object();
		JSONObject jsonObject = new JSONObject(json);
		Gson gson = new Gson();
		gson.fromJson(json, (Class<T>) o);
		return o;
	}//objectFromJson
	

	
	public class DownloadJSON extends AsyncTask<String, Void, String> {
		 
		protected String doInBackground(String... urls) {
            HttpClient client = new DefaultHttpClient();
            String json = "";
            try {
                String line = "";
                HttpGet request = new HttpGet(urls[0]);
                HttpResponse response = client.execute(request);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while ((line = rd.readLine()) != null) {
                    json += line + System.getProperty("line.separator");
                }
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return json;
        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(String result) {
        	Log.d(tag, "ONPOSTEXECUTE");
        	Log.d(tag,"jsonON: "+ result);
        }
    }
	
	
}//CommunicationClass
