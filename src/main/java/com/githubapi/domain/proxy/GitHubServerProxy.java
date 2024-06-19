package com.githubapi.domain.proxy;

import com.githubapi.infrastructure.controller.dto.GitHubBranchWithNameAndCommitDto;
import com.githubapi.infrastructure.controller.dto.GitHubRepoDto;
import com.githubapi.infrastructure.controller.error.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Log4j2
@Component
public class GitHubServerProxy {

    private final RestTemplate restTemplate;

    @Value("${github.api.url}")
    String url;

    public GitHubServerProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<GitHubRepoDto>> makeGetRequest(String userName) {

        String uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(url)
                .path("users/" + userName + "/repos")
                .build()
                .toString();

        try {
            return restTemplate.exchange(uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });
        } catch (HttpClientErrorException.NotFound e) {
            log.info("User not found : " + e.getMessage());
            throw new UserNotFoundException("User not found");
        }
    }

    public List<GitHubBranchWithNameAndCommitDto> makeGetBranchRequest(GitHubRepoDto repo) {

        String uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(url)
                .path("repos/" + repo.owner().login() + "/" + repo.name() + "/branches")
                .build()
                .toString();

        ResponseEntity<List<GitHubBranchWithNameAndCommitDto>> branches = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return branches.getBody();
    }
}
