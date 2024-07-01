package com.ssafy.sweater.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoToken {
	
	@JsonProperty("token_type")
    private String tokenType;
	@JsonProperty("access_token")
    private String accessToken;
	@JsonProperty("expires_in")
    private Integer expiresIn;
	@JsonProperty("refresh_token")
    private String refreshToken;
	@JsonProperty("refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;
	@JsonProperty("scope")
    private String scope;
    
	public KakaoToken() {}

	public KakaoToken(String tokenType, String accessToken, Integer expiresIn, String refreshToken,
			Integer refreshTokenExpiresIn, String scope) {
		this.tokenType = tokenType;
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getRefreshTokenExpiresIn() {
		return refreshTokenExpiresIn;
	}

	public void setRefreshTokenExpiresIn(Integer refreshTokenExpiresIn) {
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "KakaoToken [tokenType=" + tokenType + ", accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", refreshToken=" + refreshToken + ", refreshTokenExpiresIn="
				+ refreshTokenExpiresIn + ", scope=" + scope + "]";
	}
	
}
