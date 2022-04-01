package Api.ApplicationApi;

import Api.RestResources;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Playlist;
import spotify.oauth2.tests.utils.ConfigLoader;

import static Api.ApplicationApi.TokenManager.getToken;
import static Api.Route.PLAYLISTS;
import static Api.Route.USERS;


public class PlaylistApi {
    @Step
    public static Response post(Playlist requestPlaylist){

        return RestResources.post(USERS+ "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS,getToken(),requestPlaylist);
    }
    public static Response post(String token,Playlist requestPlaylist){
        return RestResources.post(USERS+ "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS,token,requestPlaylist);
    }
    public static Response get(String playlistId){
        return RestResources.get(PLAYLISTS+"/"+playlistId,getToken());

    }
    public static Response update(String playlistId,Playlist requestPlaylist){
        return RestResources.update(PLAYLISTS+"/"+playlistId,getToken(),requestPlaylist);

    }

}
