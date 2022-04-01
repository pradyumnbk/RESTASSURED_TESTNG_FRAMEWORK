package spotify.oauth2.tests;


import Api.ApplicationApi.PlaylistApi;
import Api.StatusCode;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.Error;
import pojo.Playlist;
import spotify.oauth2.tests.utils.DataLoader;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static spotify.oauth2.tests.utils.FakerUtils.generateDescription;
import static spotify.oauth2.tests.utils.FakerUtils.generateName;

@Epic("spotify oauth 2.0")
@Feature("playlist api")
public class PlaylistTest extends BaseTest{
    @Story("create a playlist story")
    @Link("https://example.org")
    @TmsLink("12345")
    @Issue("12568")
    @Description("here we are creating a playlist for the user")
    @Test(description="should be able to create a playlist")
    public void shouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
       assertPlaylistEquals(response.as(Playlist.class),requestPlaylist);
       assertStatuscode(response.statusCode(), StatusCode.CODE_201.getCode());
    }
    @Description("here we are fetching a playlist which was created")
    @Test(description="should be able to get a playlist")
    public void shouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(),false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
       Playlist responsePlaylist=response.as(Playlist.class);

       assertStatuscode(response.statusCode(),StatusCode.CODE_200.getCode());
      // assertPlaylistEquals(response.as(Playlist.class),requestPlaylist);
    }
    @Description("here we are updating the playlist which was previously created")
    @Test(description="should be able to update a playlist")
    public void shouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(),false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);

    }
    @Story("should not create a playlist story")
    @Description("this is the negative scenario where we are not passing the name and checking we are getting successful result or not")
    @Test(description="should not be able to create a playlist without name")
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("", generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatuscode(response.statusCode(),StatusCode.CODE_400.getCode());
        assertError(response.as(Error.class),StatusCode.CODE_400.getCode(),StatusCode.CODE_400.getMsg());
    }
    @Story("should not create a playlist story")
    @Description("this is also a negative scenario where we not passing valid token and checking we are getting successful response or not")
    @Test(description="should not be able to create a playlist with invalid token")
    public void shouldNotBeAbleToCreateAPlaylistWithInvalidToken(){
        String invalid_Token="1234";
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(),false);
               Response response=PlaylistApi.post(invalid_Token,requestPlaylist);
               assertStatuscode(response.statusCode(),StatusCode.CODE_401.getCode());
               assertError(response.as(Error.class),StatusCode.CODE_401.getCode(), StatusCode.CODE_401.getMsg());
    }
    @Step
    public Playlist playlistBuilder(String name,String description,boolean _public){
        /*for non builder pattern in lombok*/
       // Playlist playlist=new Playlist();
      //  playlist.setName(name);
      //  playlist.setDescription(description);
     //   playlist.set_public(_public);
        /*for builder pattern in lombok*/
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
       /* return new Playlist()
                .setName(name)
                .setDescription(description)
                .setPublic(_public);*/
      //  return playlist; /*for non builder return type*/
    }
    @Step
    public void assertPlaylistEquals(Playlist responsePlaylist,Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(),equalTo(requestPlaylist.get_public()));
    }
    @Step
    public void assertStatuscode(int actualStatuscode,int expectedStatuscode){
        assertThat(actualStatuscode,equalTo(expectedStatuscode));
    }
    @Step
    public void assertError(Error responseError,int expectedStatuscode,String expectedMessage){
        assertThat(responseError.getError().getStatus(),equalTo(expectedStatuscode));
        assertThat(responseError.getError().getMessage(),equalTo(expectedMessage));
    }
}
