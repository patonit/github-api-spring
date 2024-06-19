package com.githubapi.infrastructure.controller.dto.response;

import org.springframework.http.HttpStatus;

public record RepoNotFoundResponseDto(String message, HttpStatus status) {
}
