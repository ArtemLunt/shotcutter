package com.shotcutter.movies.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping
@RestController("/**")
public class OptionsController {

    @RequestMapping(method = RequestMethod.OPTIONS)
    HttpStatus index() {
        return HttpStatus.OK;
    }

}
