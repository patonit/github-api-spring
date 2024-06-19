package com.githubapi.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepoDto(Owner owner, String name, @JsonProperty("fork") Boolean isFork) {
}
