package com.githubapi.infrastructure.controller.dto.response;

import com.githubapi.infrastructure.controller.dto.GitHubBranchWithNameAndShaDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GitHubAllResult(String repoName, String ownerLogin, List<GitHubBranchWithNameAndShaDto> branches) {
}
