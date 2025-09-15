public class RegisterRequest {
    private UserData user;
    private String accessToken;
    private String userId;

    public RegisterRequest() {
    }

    public UserData getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }
}