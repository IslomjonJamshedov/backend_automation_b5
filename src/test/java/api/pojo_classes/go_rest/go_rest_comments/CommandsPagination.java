package api.pojo_classes.go_rest.go_rest_comments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandsPagination {

    private int total;
    private int pages;
    private int page;
    private int limit;
    private Links links;

}
