package com.ssafy.sweater.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sweater.model.dao.AuthDao;
import com.ssafy.sweater.model.dto.KakaoToken;
import com.ssafy.sweater.model.dto.auth.CheckCertification;
import com.ssafy.sweater.model.dto.auth.EmailCertification;
import com.ssafy.sweater.model.dto.auth.SignUp;
import com.ssafy.sweater.provider.EmailProvider;

@Service
public class AuthServiceImpl implements AuthService {

	private final String CLIENT_ID = "6728c736efecf33334135e277945c52f";
	private final String REDIRECT_URI = "http://localhost:5173/kakao";
	private final String SECRET_KEY = "KXSPZjdbOwldZQ3HOdXcOLqaaK3ixjIz";
	
	private final AuthDao authDao;
	
	private final EmailProvider emailProvider;

	public AuthServiceImpl(AuthDao authDao, EmailProvider emailProvider) {
		this.authDao = authDao;
		this.emailProvider = emailProvider;
	}

	@Override
	public boolean idCheck(String userId) {
		int result = authDao.idCheck(userId);
		
		if (result == 0)
			return true;

		return false;
	}

	@Override
	public boolean emailCertification(EmailCertification emailCertification) {

		String userId = emailCertification.getUserId();
		String email = emailCertification.getEmail();

		try {
			List<CheckCertification> userCheck = authDao.check(userId);
			if(userCheck.size() > 0) authDao.delete(userId);
			
//			System.out.println("이메일 내역 검색 완료");
			
			boolean check = this.idCheck(userId);
			if (!check)
				return false;
			
//			System.out.println("아이디 체크 완료");

			String certificationNumber = this.getCertificationNumber();

			boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
			if (!isSuccessed)
				return false;
			
//			System.out.println("이메일 발송 완료");

			authDao.add(new CheckCertification(userId, email, certificationNumber));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean checkCertification(CheckCertification checkCertification) {
		String userId = checkCertification.getUserId();
		String email = checkCertification.getEmail();
		String certificationNumber = checkCertification.getCertificationNumber();
		
		List<CheckCertification> result = authDao.check(userId);
		if(result == null || result.size() == 0) return false;
		
		boolean check = result.get(0).getEmail().equals(email) && result.get(0).getCertificationNumber().equals(certificationNumber);
		if(!check) return false;
		
		return true;
	}

	@Override
	public int signUp(SignUp signUp) {
		
		String userId = signUp.getUserId();
		boolean idCheck = this.idCheck(userId);
		if(!idCheck) return 0;
		
		String email = signUp.getEmail();
		String certificationNumber = signUp.getCertificationNumber();
		
		List<CheckCertification> result = authDao.check(userId);
		boolean check = result.get(0).getEmail().equals(email) && result.get(0).getCertificationNumber().equals(certificationNumber);
//		System.out.println(result.get(0));
//		System.out.println(check);
		if(!check) return 0;
		
		
		
		return authDao.signUp(signUp);
	}
	
	public int deleteCertification(String userId) {
		return authDao.delete(userId);
	}
	

	public String getCertificationNumber() {
		String certificationNumber = "";

		for (int count = 0; count < 4; count++)
			certificationNumber += (int) (Math.random() * 10);

		return certificationNumber;
	}

	@Override
	public String getKakaoToken(String code) {
		String accessToken = "";
		String refreshToken = "";
		
		String strUrl = "https://kauth.kakao.com/oauth/token";
		KakaoToken kakaoToken = new KakaoToken(); // 요청 받을 객체
		
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성
			
			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true); // outputStream으로 post 데이터 넘김
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			
			// 0번 파라미터 grand_type
			sb.append("grant_type=authorization_code");
			
			// 1번 파라미터 client_id
			sb.append("&client_id=" + CLIENT_ID);
			
			// 2번 파라미터 redirect_uri
			sb.append("&redirect_uri=" + REDIRECT_URI);
			
			// 3번 파라미터 code
			sb.append("&code=" + code);
			
			// 4번 보안 시크릿 키
			sb.append("&client_secret=" + SECRET_KEY);
			
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = conn.getResponseCode();
//			System.out.println(responseCode);
			
			// 요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			
//			System.out.println(result);
			
			ObjectMapper mapper = new ObjectMapper();
			
			// kakaoToken에 result를 KakaoToken.class 형식으로 저장
			kakaoToken = mapper.readValue(result, KakaoToken.class);
//			System.out.println(kakaoToken);
			
			// api 호출용 access token
			accessToken = kakaoToken.getAccessToken();
			
			// access 토큰 만료되면 refreshToken 사용
			refreshToken = kakaoToken.getRefreshToken();
			
//			System.out.println(accessToken);
//			System.out.println(refreshToken);
			
			br.close();
			bw.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return accessToken;
	}

	@Override
	public HashMap<String, Object> getKakaoUser(String kakaoToken) {
		
		// request 보낼 주소
		String strUrl = "https://kapi.kakao.com/v2/user/me";
		HashMap<String, Object> userInfo = new HashMap<>();
		
		userInfo.put("accessToken", kakaoToken);
		
		try {
			// url Http 연결 생성
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true); // outputStream으로 post 데이터 넘김
			
			conn.setRequestProperty("Authorization", "Bearer " + kakaoToken);
			
			int responseCode = conn.getResponseCode();
//			System.out.println(responseCode);
			
			// 요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			
			br.close();
			
			// JSON 파싱
			// Jackson으로 json 파싱
			ObjectMapper mapper = new ObjectMapper();
			
			// 결과 json을 HashMap 형태로 변환하여 resultMap에 담음
			HashMap<String, Object> resultMap = mapper.readValue(result, HashMap.class);
			String id = String.valueOf((Long) resultMap.get("id"));
			
			HashMap<String, Object> properties = (HashMap<String, Object>) resultMap.get("properties");
			String nickname = (String) properties.get("nickname");
			
			userInfo.put("id", id);
			userInfo.put("nickname", nickname);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return userInfo;
	}

	@Override
	public int kakaoSignUp(SignUp signUp) {
		String userId = signUp.getUserId();
		boolean idCheck = this.idCheck(userId);
		if(!idCheck) return 0;
		
		return authDao.signUp(signUp);
	}

}














