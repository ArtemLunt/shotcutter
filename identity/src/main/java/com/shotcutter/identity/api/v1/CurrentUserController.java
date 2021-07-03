package com.shotcutter.identity.api.v1;

import com.shotcutter.identity.user.UserIdentityService;
import com.shotcutter.identity.user.UserPatchDTO;
import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.library.user.User;
import com.shotcutter.securitystarter.security.JWTPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/user/me")
@RestController
public class CurrentUserController {

    private final UserIdentityService userIdentityService;
    private final ConverterService converterService;

    public CurrentUserController(UserIdentityService userIdentityService,
                                 ConverterService converterService) {
        this.userIdentityService = userIdentityService;
        this.converterService = converterService;
    }

    @GetMapping
    public User getCurrentUser(JWTPrincipal principal) {
        return principal.getPrincipal();
    }

    @PatchMapping
    public User patchUser(JWTPrincipal principal,
                          @RequestBody UserPatchDTO userPatchDTO) {
        return userIdentityService.patch(principal.getPrincipal().getId(), userPatchDTO.getUsername())
                .flatMap(user -> converterService.convertTo(user, User.class))
                .get();
    }

    @PatchMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User updateAvatar(@ModelAttribute MultipartFile avatar,
                             JWTPrincipal principal) throws ResponseStatusException {
        try {
            return userIdentityService.updateAvatar(principal.getPrincipal().getId(), avatar)
                    .flatMap(user -> converterService.convertTo(user, User.class))
                    .get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
