package com.EasyPeasy.buymybook;


/*
 * 
 * This class is used to convert JSON to Java OBject for viewing
 * 
 */

public class SearchListItem {
	private String title;
	private String author;
	private String price;
	private String condition;
	private String url;
	
	public String getTitle(){
		return this.title;
	}
	public String getAuthor(){
		return this.author;
	}
	public String getPrice(){
		return this.price;
	}
	public String getCondition(){
		return this.condition;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return this.url;
	}
	
	public SearchListItem(){
		;//noop
	}
	public SearchListItem(String title, String author, String price, String condition){
		this.title = title;
		this.author = author;
		this.price = price;
		this.condition = condition;
	}
	
	

}
