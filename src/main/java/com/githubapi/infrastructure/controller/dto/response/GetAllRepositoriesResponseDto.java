package com.githubapi.infrastructure.controller.dto.response;

import java.util.List;

public record GetAllRepositoriesResponseDto(List<RepoDto> repos) {
}
