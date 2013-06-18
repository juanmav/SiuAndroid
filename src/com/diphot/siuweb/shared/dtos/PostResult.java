package com.diphot.siuweb.shared.dtos;

import com.google.gson.Gson;

public class PostResult {
	Result result;
	Object extra;
	public enum Result {OK,FALSE,NOSTRATEGY};
	public PostResult(Result result, Object extra){
		this.result = result;
		this.extra = extra;
	}

	public String getToGson(){
		return new Gson().toJson(this);
	}
	
	public Result getResult(){
		return this.result;
	}
	
	
}