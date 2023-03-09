package api.pojo_classes.go_rest;

import lombok.Builder;
import lombok.Data;

/**
 * With the help @Data which coming from Lomlok,
 * we can eliminate the getters and setters
 */
@Data

/**
 * With @Builder, we are able to assign the value to the attributes
 */
@Builder

public class UpdateGoRestUserWithLombok {

    /**
     * {
     * "gender": "",
     * "email": "Ophelia.Mraz@hotmail.com",
     * "status": ""
     * }
     */

    private String gender;
    private String email;
    private String status;


}
