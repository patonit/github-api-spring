package com.githubapi.infrastructure.controller.error;

import com.githubapi.infrastructure.controller.GitHubRestController;
import com.githubapi.infrastructure.controller.dto.response.RepoNotFoundResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice(assignableTypes = GitHubRestController.class)
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseDto handleUserNotFoundException() {
        log.info("User not found exception.");
        return new ErrorResponseDto(404, "User not found");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(WrongAcceptHeaderException.class)
    public ResponseEntity<WrongHeaderResponseDto> handleWrongHeaderException() {
        log.info("Wrong 'accept' header.");
        return ResponseEntity.status(406)
                .contentType(MediaType.APPLICATION_JSON) // Necessary to force response type.
                .body(new WrongHeaderResponseDto(406, "Wrong header 'accept'. JSON is not acceptable."));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RepoNotFoundException.class)
    public ResponseEntity<RepoNotFoundResponseDto> handleRepoNotFoundException(RepoNotFoundException exception) {
        log.warn("RepoNotFoundException while accessing database.");
        RepoNotFoundResponseDto errorRepoResponseDto = new RepoNotFoundResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorRepoResponseDto);
    }
}