package com.akshit.services;


import com.akshit.exceptions.ErrorCode;
import com.akshit.exceptions.NewWavesException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GcpAccessTokenService {

    public Tokeninfo validateAccessToken(String accessToken) {
        try {
            // Initialize the transport and JSON factory
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            // Build the OAuth2 service
            Oauth2 oauth2 = new Oauth2.Builder(transport, jsonFactory, null).setApplicationName("New Waves")
                    .build();

            // Set up the tokeninfo request
            Oauth2.Tokeninfo tokenInfoRequest = oauth2.tokeninfo();
            tokenInfoRequest.setAccessToken(accessToken);

            // Execute the request and parse the response
            return tokenInfoRequest.execute();
        } catch (Exception e) {
            throw NewWavesException.of(ErrorCode.OAUTH2_GOOGLE_USER_NOT_FOUND, e.getMessage());
        }
    }
}
