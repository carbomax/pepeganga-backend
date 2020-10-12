package com.startcode.EcommerceServices;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.startcode.Models.UserTestRequest;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration.AuthUrls;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.AccessToken;
import io.swagger.client.model.RefreshToken;
import io.swagger.client.model.UserResponse;
import io.swagger.client.model.UserTestResponse;

@Service
public class MercadoLibreEcommerce {
	
	/*@Autowired
	private DefaultApi meliApi;*/
	
	/*
	public String test() {
		return ""; /*meliApi.getApiClient().getBasePath();
	}*/

	static long ClientId = 6727534428095949L;	
	static String SecretKey = "PP4JV8CnJsh3tZOc7xHrcQfgquV8M6yY";
    static String redirectUri = "https://www.mercadolibre.com.uy/"/*"https://192.168.1.3:8443"*/;
    
    //private final DefaultApi apiML = new DefaultApi();
	 
    public static String getAuthUrl() throws UnsupportedEncodingException, ApiException {
            DefaultApi api = new DefaultApi(new ApiClient(), ClientId, SecretKey);
            String response = api.getAuthUrl(redirectUri, AuthUrls.MLU);
            return response;
     }
     
     public static AccessToken getAccessToken(String code) throws UnsupportedEncodingException, ApiException {
                DefaultApi api = new DefaultApi(new ApiClient(), ClientId, SecretKey);             
                AccessToken response = api.authorize(code, redirectUri);
                return response;
     }
      
     public static RefreshToken refreshToken() throws UnsupportedEncodingException, ApiException {
                     DefaultApi api = new DefaultApi(new ApiClient(), ClientId, SecretKey);
                     String refreshToken = "{your_refresh_token}";
                     RefreshToken response = api.refreshAccessToken(refreshToken);
                     return response;
     }
     
     public UserTestResponse CreateTestUser(UserTestRequest testUser) throws ApiException{
    	 DefaultApi api = new DefaultApi(new ApiClient(), ClientId, SecretKey); 
    	// UserTestResponse response = meliApi.CreateTestUser(testUser.getSite_id(), testUser.getAccess_token());
    	 UserTestResponse response = api.CreateTestUser(testUser.getSite_id(), testUser.getAccess_token());
         return response;
     }
     
     public UserResponse getUserAccountInfo(Map<String, ?> customQuery) throws ApiException{    	    	     		 
    	 DefaultApi api = new DefaultApi();
    	 UserResponse response = api.usersUserIdGet(Integer.valueOf((String) customQuery.get("userId")), customQuery.get("accessToken").toString());
    	 return response;
     }
 		
}
