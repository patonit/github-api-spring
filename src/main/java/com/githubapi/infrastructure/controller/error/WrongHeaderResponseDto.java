package com.githubapi.infrastructure.controller.error;

public record WrongHeaderResponseDto(Integer status, String message) {
}
