package spotify.oauth2.tests.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    public static String generateName(){
        Faker faker=new Faker();
        return "playlist"+faker.regexify("[A-Za-z0-9]{10}");
    }
    public static String generateDescription(){
        Faker faker=new Faker();
        return "Description"+faker.regexify("[A-Za-z0-9]{50}");
    }
}
