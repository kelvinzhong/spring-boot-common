package com.hippo.common.util.security;

import com.hippo.common.bean.JwtState;
import com.hippo.common.bean.JwtToken;
import com.hippo.common.util.general.StringUtils;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtUtils {

	private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

	private static final String REFRESH_TIME = "refreshTime";

	public static String generateSignedJwt(JwtToken jwtToken) {

		if (jwtToken == null) {
			log.warn("user token not found!");
			return null;
		}

		try {
			JWSSigner signer = new MACSigner(jwtToken.getSecret());

			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().issuer(jwtToken.getUserId())
					.subject(jwtToken.getSubject()).expirationTime(jwtToken.getExpirationDate())
					.claim(REFRESH_TIME, jwtToken.getRefreshDate()).claim("clientType", jwtToken.getClientType())
					.build();

			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

			signedJWT.sign(signer);

			return signedJWT.serialize();

		} catch (Exception e) {
			log.error("generate token fail! {}", e.toString());
			return null;
		}
	}

	public static int validateToken(String userId, String token, String secret, String imei) {

		if (StringUtils.isEmpty(secret))
			return JwtState.EMPTY.code;

		try {
			SignedJWT signedJWT = SignedJWT.parse(token);

			JWSVerifier verifier = new MACVerifier(secret);

			if (signedJWT.verify(verifier))
				if (!signedJWT.getJWTClaimsSet().getSubject().equals(imei))
					return JwtState.CONFLICT.code;
				else if (new Date().after(signedJWT.getJWTClaimsSet().getExpirationTime()))
					return JwtState.EXPIRED.code;
				else if (new Date().after(signedJWT.getJWTClaimsSet().getDateClaim(REFRESH_TIME)))
					// token有效时间少于REFRESH_REMAIN_TIME
					return JwtState.REFRESH.code;
				else
					return JwtState.VALID.code;
			else
				return JwtState.INVALID.code;

		} catch (Exception e) {
			log.error("token anylase error {}", e);
			return JwtState.INVALID.code;
		}
	}

}
