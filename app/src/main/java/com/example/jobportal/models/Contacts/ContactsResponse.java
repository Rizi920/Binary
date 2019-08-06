package com.example.jobportal.models.Contacts;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ContactsResponse{

	@SerializedName("total_row")
	private int totalRow;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("page")
	private int page;

	@SerializedName("row")
	private int row;

	public void setTotalRow(int totalRow){
		this.totalRow = totalRow;
	}

	public int getTotalRow(){
		return totalRow;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setRow(int row){
		this.row = row;
	}

	public int getRow(){
		return row;
	}

	@Override
 	public String toString(){
		return 
			"ContactsResponse{" + 
			"total_row = '" + totalRow + '\'' + 
			",data = '" + data + '\'' + 
			",page = '" + page + '\'' + 
			",row = '" + row + '\'' + 
			"}";
		}
}