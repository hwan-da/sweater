package com.ssafy.sweater.model.dto;

public class Mountain {
	private String name;
	private String region;
	private int fid;
	private String mntnCode;
	private double pmntnLt;
	private String pmntnDffl;
	private int pmntnUppl;
	private int pmntnGodn;
	private String paths;
	
	public Mountain() {}

	public Mountain(String name, String region, int fid, String mntnCode, double pmntnLt, String pmntnDffl,
			int pmntnUppl, int pmntnGodn, String paths) {
		this.name = name;
		this.region = region;
		this.fid = fid;
		this.mntnCode = mntnCode;
		this.pmntnLt = pmntnLt;
		this.pmntnDffl = pmntnDffl;
		this.pmntnUppl = pmntnUppl;
		this.pmntnGodn = pmntnGodn;
		this.paths = paths;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getMntnCode() {
		return mntnCode;
	}

	public void setMntnCode(String mntnCode) {
		this.mntnCode = mntnCode;
	}

	public double getPmntnLt() {
		return pmntnLt;
	}

	public void setPmntnLt(double pmntnLt) {
		this.pmntnLt = pmntnLt;
	}

	public String getPmntnDffl() {
		return pmntnDffl;
	}

	public void setPmntnDffl(String pmntnDffl) {
		this.pmntnDffl = pmntnDffl;
	}

	public int getPmntnUppl() {
		return pmntnUppl;
	}

	public void setPmntnUppl(int pmntnUppl) {
		this.pmntnUppl = pmntnUppl;
	}

	public int getPmntnGodn() {
		return pmntnGodn;
	}

	public void setPmntnGodn(int pmntnGodn) {
		this.pmntnGodn = pmntnGodn;
	}

	public String getPaths() {
		return paths;
	}

	public void setPaths(String paths) {
		this.paths = paths;
	}

	@Override
	public String toString() {
		return "Mountain [name=" + name + ", region=" + region + ", fid=" + fid + ", mntnCode=" + mntnCode
				+ ", pmntnLt=" + pmntnLt + ", pmntnDffl=" + pmntnDffl + ", pmntnUppl=" + pmntnUppl + ", pmntnGodn="
				+ pmntnGodn + ", paths=" + paths + "]";
	}

	
	
	
	
}
