package Communication;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CommunicationClass {
		//verification before request
	public CommunicationClass(){
		
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
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while((read = reader.read(chars)) != -1){
				buffer.append(chars,0,read);
			}//while
			return buffer.toString();
		} finally {
			if(reader != null){
				reader.close();
			}
		}//finally
	}//readUrl
}//CommunicationClass
