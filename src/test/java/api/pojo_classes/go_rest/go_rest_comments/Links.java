package api.pojo_classes.go_rest.go_rest_comments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Links {

    private String previous;
    private String current;
    private String next;
}
