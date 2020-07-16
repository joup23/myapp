package com.pro.myapp.sample.spring;

public class UserInfo {

	String uid;
	int idx;
	String unix;
	boolean error;
	
	public UserInfo(boolean error) {
		this.error = error;
	}

	public UserInfo(String uid, int idx, String unix) {
		this.uid = uid;
		this.idx = idx;
		this.unix = unix;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUnix() {
		return unix;
	}

	public void setUnix(String unix) {
		this.unix = unix;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	
}
