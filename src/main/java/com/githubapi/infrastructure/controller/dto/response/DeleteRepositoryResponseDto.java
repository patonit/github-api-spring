package com.githubapi.infrastructure.controller.dto.response;

import org.springframework.http.HttpStatus;

public record DeleteRepositoryResponseDto(String message, HttpStatus status) {
}
