public class RegisterRequest {
    private UserData user;
    private String accessToken;

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }
}