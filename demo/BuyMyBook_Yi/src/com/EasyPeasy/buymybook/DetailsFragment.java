package com.EasyPeasy.buymybook;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.method.KeyListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * xml file: fragment_results_search.xml
 */
public class DetailsFragment extends Fragment {
	//private OnItemSelectedListener listener;
	private EditText editPhoneNumber;
	private EditText editTextNumber;
	private EditText editEmailAddress;
	
	private KeyListener originalPhoneKeyListener;
	private KeyListener originalTextKeyListener;
	private KeyListener originalEmailKeyListener;
	
	private ImageView callme;
	private ImageView textme;
	private ImageView emailme;
	
	private TextView titleText;
	private TextView authorText;
	private TextView priceText;
	private TextView conditionText;
	
	public DetailsFragment(){
		;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	public String getCondition(int cond){
		String condition;
		 switch(cond){
     	case 0:
     		condition = new String("Decomposed");
     		break;
     	case 1:
     		condition = new String("Poor");
     		break;
     	case 2:
     		condition = new String("Fair");
     		break;
     	case 3:
     		condition = new String("Good");
     		break;
     	case 4:
     		condition = new String("Very Good");
     		break;
     	case 5:
     		condition = new String("As New");
     		break;
     	default:
     		condition = new String("N/A");
     		break;
     		
     }//switch
		 
		 return condition;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_results_details,container,false);
		
		editTextNumber = (EditText)view.findViewById(R.id.message);
		editEmailAddress = (EditText)view.findViewById(R.id.email);
		
	
		emailme = (ImageView)view.findViewById(R.id.email_img);
		textme = (ImageView)view.findViewById(R.id.message_img);
		
	
		originalTextKeyListener = editTextNumber.getKeyListener();
		originalEmailKeyListener = editEmailAddress.getKeyListener();
		
		//Put name, join date, phone, text, and email
	
	
		editTextNumber.setText("5195460284");
		editEmailAddress.setText("cecabusa@uwaterloo.ca");
		titleText = (TextView) view.findViewById(R.id.title);
		authorText = (TextView) view.findViewById(R.id.author);
		priceText = (TextView) view.findViewById(R.id.price);
		conditionText = (TextView) view.findViewById(R.id.condition);
		
		
	
		TextView txt = (TextView) view.findViewById(R.id.search_manual_title);
		//String result = getArguments() != null ? getArguments().getString("json") : null;
		/*if(result != null){
			txt.setText(result);
		}*/
		String title = getArguments().getString("title");
		String author = getArguments().getString("author");
		String price = getArguments().getString("price");
		String condition = getArguments().getString("condition");
		condition = getCondition(Integer.parseInt(condition)).toString();
		titleText.setText(title);
		authorText.setText(author);
		priceText.setText(price);
		conditionText.setText(condition);
		/*String result = new String(title.toString()+author.toString()+price.toString()+condition.toString());
		txt.setText(result);
		*/
		return view;
		
	}
	
	@Override
    public void onAttach(Activity activity) {
      super.onAttach(activity);
      
      
      /*
      if (activity instanceof OnItemSelectedListener) {
        listener = (OnItemSelectedListener) activity;
      } else {
        throw new ClassCastException(activity.toString()
            + " must implemenet MyListFragment.OnItemSelectedListener");
      }
      */
    }
	
	
}
