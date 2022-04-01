package Api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


import java.util.HashMap;

import static Api.Route.API;
import static Api.Route.TOKEN;
import static Api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResources {

    public static Response postAccount(HashMap<String,String> formParams){
        return given(getAccountRequestSpec())
                .formParams(formParams)
                .when()
                .post(API+TOKEN)
                .then().spec(getResponseSpec())
                .log().all()
                .extract()
                .response();
    }

    public static Response post(String path,String token,Object requestPlaylist){
        return given(getRequestSpec())
                .auth().oauth2(token)
                .body(requestPlaylist)
                .when()
                .post(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response get(String path, String token){
        return given(getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(path)
                .then().spec(getResponseSpec())
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
    }
    public static Response update(String path,String token,Object requestPlaylist){
       return given(getRequestSpec())
               .auth().oauth2(token)
                .body(requestPlaylist)
                .when()
                .put(path)
                .then().spec(getResponseSpec())
               .extract()
               .response();
    }

}
