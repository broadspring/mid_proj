package com.hg.pj.sns;

public class SiteOption {

	private int snsCountPerpage; // 한 페이지 당 sns 개수
	
	public SiteOption() {
		// TODO Auto-generated constructor stub
	}

	public SiteOption(int snsCountPerpage) {
		super();
		this.snsCountPerpage = snsCountPerpage;
	}

	public int getSnsCountPerpage() {
		return snsCountPerpage;
	}

	public void setSnsCountPerpage(int snsCountPerpage) {
		this.snsCountPerpage = snsCountPerpage;
	}
		
}
