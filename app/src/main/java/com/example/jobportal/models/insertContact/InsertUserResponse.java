package com.example.jobportal.models.insertContact;

import com.google.gson.annotations.SerializedName;

public class InsertUserResponse {

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"InsertUserResponse{" +
			"data = '" + data + '\'' + 
			"}";
		}
}