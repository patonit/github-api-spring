package com.githubapi.infrastructure.controller.dto;

import java.util.Map;

public record GitHubBranchWithNameAndCommitDto(String name, Map<String, String> commit) {
}
