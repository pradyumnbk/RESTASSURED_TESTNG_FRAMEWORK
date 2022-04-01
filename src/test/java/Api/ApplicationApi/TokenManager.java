package Api.ApplicationApi;

import Api.RestResources;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import spotify.oauth2.tests.utils.ConfigLoader;

import java.time.Instant;
import java.util.HashMap;

import static Api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class TokenManager {

   private static String access_token;
   private static Instant expiry_time;

    public synchronized static String getToken(){
        try {
            if (access_token==null || Instant.now().isAfter(expiry_time)){
                System.out.println("renewing token....");
                Response response = renewToken();
                access_token=response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time =Instant.now().plusSeconds(expiryDurationInSeconds-300);
            } else {
                System.out.println("token is good to use");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
           throw new RuntimeException("Abort !!! failed to generate token");
        }
        return access_token;
    }

    private static Response renewToken(){
        HashMap<String,String> formParams=new HashMap<String,String>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token",ConfigLoader.getInstance().getRefreshToken());
        formParams.put("client_id",ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret",ConfigLoader.getInstance().getClientSecret());

        Response response = RestResources.postAccount(formParams);
        if((response.statusCode())!=200){
            throw new RuntimeException("ABORT!!! Renew token failed");
        }
        else return response;
    }
}
