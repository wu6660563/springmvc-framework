package com.geeku.vo;

public enum AuthrRespId{
	
	UN_LOGIN(401,"抱歉，您还未登陆!"),
	UN_AUTHRIZATION(302,"抱歉，您无权限!");
	
	private int id;
	private String msg;
	
	private AuthrRespId(int id,String msg){
		this.id = id;
		this.msg = msg;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getMsg(){
		return this.msg;
	}
} 
