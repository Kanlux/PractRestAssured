public class LoginResponse {
    private UserData  user;
    private String accessToken;

    public LoginResponse() {

    }

    public UserData getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }
}