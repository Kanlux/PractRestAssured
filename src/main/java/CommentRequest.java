public class CommentRequest {
    private String text;
    private int postId;

    public CommentRequest(String text, int postId) {
        this.text = text;
        this.postId = postId;
    }

    public CommentRequest(int postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public int getPostId() {
        return postId;
    }
}
