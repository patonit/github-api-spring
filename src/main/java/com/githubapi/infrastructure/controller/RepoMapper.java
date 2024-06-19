package com.githubapi.infrastructure.controller;

import com.githubapi.infrastructure.controller.dto.request.UpdateRepoRequestDto;
import com.githubapi.domain.model.Repo;
import com.githubapi.infrastructure.controller.dto.response.*;
import org.springframework.http.HttpStatus;

import java.util.List;

public class RepoMapper {

    public static DeleteRepositoryResponseDto mapFromRepoToDeleteRepositoryResponseDto(Long id) {
        return new DeleteRepositoryResponseDto("Repository with id: " + id + " deleted.", HttpStatus.OK);
    }

    public static DeleteRepositoriesResponseDto mapFromRepoToDeleteRepositoriesResponseDto() {
        return new DeleteRepositoriesResponseDto("All repositories deleted", HttpStatus.OK);
    }

    public static CreateRepositoryResponseDto mapFromRepoToCreateRepositoryResponseDto(Repo repo) {
        RepoDto repoDto = mapFromRepoToRepoDto(repo);
        return new CreateRepositoryResponseDto(repoDto);
    }

    public static RepoDto mapFromRepoToRepoDto(Repo repo) {
        return new RepoDto(repo.getId(), repo.getOwner(), repo.getName());
    }

    public static Repo mapFromUpdateRepoRequestDtoToRepo(UpdateRepoRequestDto request) {
        return new Repo(request.owner(), request.name());
    }

    public static UpdateRepositoryResponseDto mapFromRepoToUpdateRepositoryResponseDto(Repo repo) {
        return new UpdateRepositoryResponseDto(repo.getOwner(), repo.getName());
    }

    public static GetRepositoryResponseDto mapFromRepoToGetRepositoryResponseDto(Repo repo) {
        RepoDto repoDto = mapFromRepoToRepoDto(repo);
        return new GetRepositoryResponseDto(repoDto);
    }

    public static GetAllRepositoriesResponseDto mapFromRepoToGetAllRepositoriesResponseDto(List<Repo> repos) {
        List<RepoDto> repoDtos = repos.stream().map(RepoMapper::mapFromRepoToRepoDto).toList();
        return new GetAllRepositoriesResponseDto(repoDtos);
    }







}
