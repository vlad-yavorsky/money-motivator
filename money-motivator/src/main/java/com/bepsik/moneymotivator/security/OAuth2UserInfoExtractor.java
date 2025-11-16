package com.bepsik.moneymotivator.security;

import com.bepsik.moneymotivator.dto.OAuth2UserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OAuth2UserInfoExtractor {

    public OAuth2UserInfo extractUserInfo(OAuth2User oAuth2User) {
        return OAuth2UserInfo.builder()
                .id(oAuth2User.getAttribute("sub"))
                .email(oAuth2User.getAttribute("email"))
                .firstName(oAuth2User.getAttribute("given_name"))
                .lastName(oAuth2User.getAttribute("family_name"))
                .build();
    }
}