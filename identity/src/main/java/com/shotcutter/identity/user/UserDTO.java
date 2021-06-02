package com.shotcutter.identity.user;

import com.shotcutter.library.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "userDTOBuilder")
public class UserDTO extends User {
}
