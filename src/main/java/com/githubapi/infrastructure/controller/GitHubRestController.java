package com.githubapi.infrastructure.controller;

import com.githubapi.domain.service.*;
import com.githubapi.infrastructure.controller.dto.GitHubRepoDto;
import com.githubapi.infrastructure.controller.dto.request.CreateRepoRequestDto;
import com.githubapi.infrastructure.controller.dto.request.UpdateRepoRequestDto;
import com.githubapi.domain.model.Repo;
import com.githubapi.infrastructure.controller.dto.response.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.githubapi.infrastructure.controller.RepoMapper.*;

@AllArgsConstructor
@RestController
@RequestMapping("/github")
public class GitHubRestController {

    private final RepoRetriever repoRetriever;
    private final RepoDeleter repoDeleter;
    private final RepoAdder repoAdder;
    private final RepoUpdater repoUpdater;
    private final GitHubService gitHubService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<GitHubAllResult>> getAllRepositoriesWhichAreNotForks(@RequestHeader String accept,
                                                                                    @PathVariable String userName) {

        List<GitHubRepoDto> allRepositoriesNames = gitHubService.fetchListOfAllRepositoriesNames(accept, userName);
        gitHubService.addReposToDatabase(allRepositoriesNames);
        List<GitHubAllResult> response = gitHubService.fetchAllBranches(allRepositoriesNames);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/db")
    public ResponseEntity<GetAllRepositoriesResponseDto> getAllRepositoriesFromDb(@PageableDefault(page = 1, size = 5) Pageable pageable) {
        List<Repo> allRepos = repoRetriever.getAllRepositoriesFromDb(pageable);
        GetAllRepositoriesResponseDto body = mapFromRepoToGetAllRepositoriesResponseDto(allRepos);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/db/{id}")
    public ResponseEntity<GetRepositoryResponseDto> getRepositoryById(@PathVariable Long id) {
        Repo repo = repoRetriever.getRepoById(id);
        GetRepositoryResponseDto body = mapFromRepoToGetRepositoryResponseDto(repo);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/db")
    public ResponseEntity<DeleteRepositoriesResponseDto> deleteAllRepos() {
        repoDeleter.deleteAllRepos();
        DeleteRepositoriesResponseDto body = mapFromRepoToDeleteRepositoriesResponseDto();
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/db/{id}")
    public ResponseEntity<DeleteRepositoryResponseDto> deleteRepoById(@PathVariable Long id) {
        repoDeleter.deleteRepoById(id);
        DeleteRepositoryResponseDto body = mapFromRepoToDeleteRepositoryResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/db")
    public ResponseEntity<CreateRepositoryResponseDto> postRepo(@RequestBody CreateRepoRequestDto request) {
        Repo savedRepo = repoAdder.addRepo(request);
        CreateRepositoryResponseDto body = mapFromRepoToCreateRepositoryResponseDto(savedRepo);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/db/{id}")
    public ResponseEntity<UpdateRepositoryResponseDto> updateRepoById(@PathVariable Long id, @RequestBody UpdateRepoRequestDto request) {
        Repo newRepo = mapFromUpdateRepoRequestDtoToRepo(request);
        repoUpdater.updateRepoById(id, newRepo);
        UpdateRepositoryResponseDto body = mapFromRepoToUpdateRepositoryResponseDto(newRepo);
        return ResponseEntity.ok(body);
    }
}