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


public class CommunicationClass extends AsyncTask<String, String, Void>{
		//verification before request
	private String tag = "CommClass";
	//most commonly used for setting up and starting a progress dialog
	InputStream inputStream = null;
	String result = ""; //result of JSON
	HttpResponse httpResponse2;
	private boolean done = false;
	public CommunicationClass(){
		String tag = "CommClass";
		Log.v(tag,"CommClass Created");
		System.out.println("CommClass Created");
		
		String uri = new String("http://buymybookapp.com/api/test/test2");
        new DownloadFilesTask().execute(uri , null, null);
	}
	void sendJSON(Object o,int options){
		//options = post 0, search 1
		String json = buildJSON(o);
		switch(options){
			case 0: //post
				
				break;
			case 1: //search
				
				break;
			default:
				
				break;
		}//switch
	}//post
	/*
	 * Input: Any Java Object
	 * Output: A JSON String
	 * Turns java object into JSON
	 */
	private String buildJSON(Object o){
		//Gson gson = new GsonBuilder().create();
		//Map<String, Object> data = new HashMap<String,Object>();
		Gson gson = new Gson();
		String json = gson.toJson(o);
		return json;
	}
	//not done
	Object objectFromJson(String json) throws JSONException{
		Object o = new Object();
		JSONObject jsonObject = new JSONObject(json);
		
		return o;
	}//objectFromJson
	
	public JSONObject getJSONString() throws Exception{
		String json = null;
		try {
			Log.d(tag,"reading url");
			json = readUrl("http://buymybookapp.com/api/test/test2");
			Log.d(tag,"reading url returned");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//catch
		if(json == null){
			Log.d(tag,"json string null");
		}
		else{
			Log.d(tag,"json string not null");
		}
		System.out.println(json);
		return new JSONObject(json);
	}
	public Object getJSON(){
	
		Object value = null;
		try {
			String json = readUrl("http://buymybookapp.com/api/test/test2");
			Gson gson =  new Gson();
			//DataJSON value = new DataJSON();
			value = new Object();
			gson.fromJson(json, (Type) value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//catch
		return value;
	}//get JSON
	
	private String readUrl(String urlString) throws Exception{
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			int read;
			char[] chars = new char[1024];
			while((read = reader.read(chars)) != -1){
				buffer.append(chars,0,read);
			}//while
			Log.d(tag,"readURL, buffer contains JSON");
			return buffer.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if(reader != null){
				Log.d(tag,"reading closed");
				reader.close();
			}//if
		}//finally
		Log.d(tag,"readURL, buffer empty");
		return buffer.toString();
	}//readUrl
	
	
	/////////
	
	public JSONArray get2(){
		String url = "http://buymybookapp.com/api/test/test2";
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONArray jarray = null;
		StringBuilder builder = new StringBuilder();
		try{
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode == 200){
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while((line = reader.readLine()) != null){
					builder.append(line);
				}//while
			}//if
			else{
				Log.e(tag,"FAILED TO DOWNLOAD");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			jarray = new JSONArray(builder.toString());
		} catch(JSONException e){
			Log.e(tag,"ERROR PARSING JSON: "+e.toString());
		}
		return jarray;
		//parse
	}
	@Override
	protected Void doInBackground(String... params) {
		String url = "http://buymybookapp.com/api/test/test2";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpClient httpPost = (HttpClient) new HttpPost(url);
			((HttpResponse) httpPost).setEntity(new UrlEncodedFormEntity(param));
			((HttpResponse) httpPost).setEntity(new UrlEncodedFormEntity(param));
			HttpResponse httpResponse = httpClient.execute((HttpUriRequest) httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
			httpResponse2 = httpResponse;
			
		} catch(Exception e){
			Log.d(tag,"Exception in doInBackground: "+e.toString());
		}//catch
		
		//convert response to string using String Builder
	
		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
			StringBuilder sBuilder = new StringBuilder();
			String line = null;
			while((line = bReader.readLine()) != null){
				sBuilder.append(line + "\n");
			}
			inputStream.close();
			result = sBuilder.toString();
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		done = true;
		return null;
	}
	@Override
	protected void onPostExecute(Void v){
		
	}
	public class DownloadFilesTask extends AsyncTask<String, Void, String> {
		 
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
