package com.shotcutter.library.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderMethodName = "userDTOBuilder")
public class UserDTO extends User {
}
