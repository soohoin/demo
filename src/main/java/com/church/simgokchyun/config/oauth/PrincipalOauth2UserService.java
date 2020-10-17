package com.church.simgokchyun.config.oauth;


import java.util.Optional;

import com.church.simgokchyun.common.common.CommonMapper;
import com.church.simgokchyun.common.common.CommonService;
import com.church.simgokchyun.common.vo.User;
import com.church.simgokchyun.config.auth.PrincipalDetails;
import com.church.simgokchyun.config.oauth.provider.KakaoUserInfo;
import com.church.simgokchyun.config.oauth.provider.OAuth2UserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CommonService comService;

	@Autowired
	CommonMapper  mapper;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // kakao의 회원 프로필 조회
		// return super.loadUser(userRequest);
		return processOAuth2User(userRequest, oAuth2User);
	}

	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			logger.info("oauth2 for kakao");
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
		}else if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			logger.info("oauth2 for google");
			// oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			logger.info("oauth2 for facebook");
			// oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			logger.info("oauth2 for naver");
			// oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		}

		User user = new User();
		try {
			Optional<User> userOptional = mapper.findByUserInfo(oAuth2UserInfo.getEmail());
			
			if (userOptional.isPresent()) {
				user = userOptional.get();
				// user가 존재하면 update 해주기
				user.setEmail_addr(oAuth2UserInfo.getEmail());
				// comService.save(user);
			} else {
				// user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
				user.setEmail_addr(oAuth2UserInfo.getEmail());
				user.setUser_nm(oAuth2UserInfo.getName());
				user.setUser_nic_nm(oAuth2UserInfo.getName());
				user.setThumbnail_image_url(oAuth2UserInfo.getProfileImg());
				// 비밀번호를 임의로 만들어서 넣어준다.  - 추후 비번으로 로그인시 이메일 인증으로 임시 페스워드 부여하기
				user.setPassword(comService.getRamdomPassword(10));
				user.setAuth_yn("Y");
				comService.joinUser(user);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		// new PrincipalDetails(user, oAuth2User.getAttributes());
		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}
