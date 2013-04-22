package com.diphot.siuweb.shared.dtos;

import com.google.gson.Gson;

public class PostResult {
	Result result;
	public enum Result {OK,FALSE,NOSTRATEGY};
	public PostResult(Result result){
		this.result = result;
	}

	public String getToGson(){
		return new Gson().toJson(this);
	}
}