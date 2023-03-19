package api.pojo_classes.go_rest.go_rest_comments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentData {

    private int id;
    private int post_id;
    private String name;
    private String email;
    private String body;
}
