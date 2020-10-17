package com.church.simgokchyun.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;
    // private Map<String, Object> properties;
    private Map<String, Object> kakao_account;
    private Map<String, Object> profile;
    
    /*
      oAuth2User.getAttributes
      
      {
        id=1505517363
        connected_at=2020-10-17T02:52:18Z
        properties={
                    nickname=배영철
                    profile_image=http://k.kakaocdn.net/dn/bNiyRf/btqH8dwHIWY/qFQMpZktIK4K0jpdl6moek/img_640x640.jpg
                    thumbnail_image=http://k.kakaocdn.net/dn/bNiyRf/btqH8dwHIWY/qFQMpZktIK4K0jpdl6moek/img_110x110.jpg
                    }
        kakao_account={
                        profile_needs_agreement = false
                        profile = {
                                    nickname=배영철
                                    thumbnail_image_url=http://k.kakaocdn.net/dn/bNiyRf/btqH8dwHIWY/qFQMpZktIK4K0jpdl6moek/img_110x110.jpg
                                    profile_image_url=http://k.kakaocdn.net/dn/bNiyRf/btqH8dwHIWY/qFQMpZktIK4K0jpdl6moek/img_640x640.jpg
                                    }
                        has_email  =  true
                        email_needs_agreement  =  false
                        is_email_valid  =  true
                        is_email_verified  =  true
                        email=0-0nabyc@hanmail.net
                        }
        }
     */

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.kakao_account = (Map<String,Object>)attributes.get("kakao_account");
        this.profile = (Map<String,Object>)kakao_account.get("profile");

        // this.properties = (Map<String,Object>)attributes.get("properties");
    }
	
    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) profile.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) kakao_account.get("email");
    }

	@Override
	public String getProvider() {
		return "kakao";
    }
    
    @Override
    public String getProfileImg() {
        return (String) profile.get("thumbnail_image_url");
    }

}
