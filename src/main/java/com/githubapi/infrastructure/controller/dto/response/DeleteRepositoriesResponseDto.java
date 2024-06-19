package com.githubapi.infrastructure.controller.dto.response;

import org.springframework.http.HttpStatus;

public record DeleteRepositoriesResponseDto(String message, HttpStatus status) {
}
