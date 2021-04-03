package com.shotcutter.movies.TMDB;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpRequest;
import lombok.Builder;

import java.io.IOException;
import java.net.URI;

@Builder
public class TMDBApiInterceptor implements ClientHttpRequestInterceptor {
    private String apiKey;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        URI newUrl = UriComponentsBuilder.fromHttpUrl(request.getURI().toString())
                .queryParam(PathVariable.API_KEY.toString(), apiKey)
                .build()
                .toUri();

        return execution.execute(
                new HttpRequestWrapper(request) {
                    @Override
                    public URI getURI() {
                        return newUrl;
                    }
                },
                body
        );
    }
}
