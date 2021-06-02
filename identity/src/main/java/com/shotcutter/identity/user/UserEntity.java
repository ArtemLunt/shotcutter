package com.shotcutter.identity.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(builderMethodName = "userEntityBuilder")
@With
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public final class UserEntity {

    @Id
    private String id;
    private String username;
    @Indexed(unique = true)
    private String email;
    private String avatar;

}
