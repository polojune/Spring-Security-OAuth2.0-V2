package com.cos.securityex01.config.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.securityex01.config.oauth.provider.NaverUserInfo;
import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.config.oauth.provider.FaceBookUserInfo;
import com.cos.securityex01.config.oauth.provider.GoogleUserInfo;
import com.cos.securityex01.config.oauth.provider.OAuth2UserInfo;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
      
	@Autowired
	private UserRepository userRepository;
	
    //userRequest 는 code 를 받아서 acessToken 을 응답 받은 객체 	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User =  super.loadUser(userRequest); //google의 회원 프로필 조회
		//oAuth2User 정보를 어디에 담아서 무엇을 리턴? 
		//1번 :PrincipalDetails에 OAuth2User 정보를 집어 넣어 준다. 
		//2번 :PrincipalDetails를 리턴한다.
		System.out.println("oAuth2User" + oAuth2User); //토큰을 통해 응답받은 회원정보
		System.out.println("userRequest" + userRequest.getAccessToken().getTokenValue()); 
		System.out.println("userRequest" + userRequest.getClientRegistration());
		//System.out.println("userRequest" + userRequest.getClass());
	
		return processOAuth2User(userRequest,oAuth2User);
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest,OAuth2User oAuth2User) {
		
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
		   oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes()); 
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			System.out.println("네이버 로그인 요청~~");
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
			
			
		}else {
			System.out.println("우리는 구글과 페이스북만 지원해요");
		}
		
		Optional<User> userOptional = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(),oAuth2UserInfo.getProviderId());
		
		User user; 
		if(userOptional.isPresent()) {
			user = userOptional.get();
			user.setEmail(oAuth2UserInfo.getEmail());
			userRepository.save(user);
		  
		}else {
		 //user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음. 
		 user = User.builder()
			   .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId()) 
			   .email(oAuth2UserInfo.getEmail())
			   .role("ROLE_USER")
			   .provider(oAuth2UserInfo.getProvider())
			   .providerId(oAuth2UserInfo.getProviderId())
			   .build();
		 userRepository.save(user);
		}
		
		
		//일반적으로는 로그인할 때 유저 정보 User 
		//1.OAuth2로 로그인 할때 유저 정보 attributes<- 이거 구성해야함 
		
		//2.db에 user정보 확인 
		
		//있으면 ㅡ>update 해야함 
		//없으면 ㅡ>insert 해야함 
		
		//return PrincipalDetails()
		return new PrincipalDetails(user,oAuth2User.getAttributes());
	}
}
