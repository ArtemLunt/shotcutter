package com.shotcutter.identity.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder(builderMethodName = "userEntityBuilder")
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
