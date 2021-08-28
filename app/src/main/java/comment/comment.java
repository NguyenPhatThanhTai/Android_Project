package comment;

public class comment {
    private String CommentId;
    private String Name;
    private String Comment;
    private String Created;
    private String MovieId;

    public comment(String commentId, String name, String comment, String created, String movieId) {
        CommentId = commentId;
        Name = name;
        Comment = comment;
        Created = created;
        MovieId = movieId;
    }

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String commentId) {
        CommentId = commentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }
}
