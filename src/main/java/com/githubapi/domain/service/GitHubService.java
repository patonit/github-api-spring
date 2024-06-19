package com.githubapi.domain.service;

import com.githubapi.domain.proxy.GitHubServerProxy;
import com.githubapi.infrastructure.controller.dto.GitHubBranchWithNameAndShaDto;
import com.githubapi.infrastructure.controller.dto.GitHubBranchWithNameAndCommitDto;
import com.githubapi.infrastructure.controller.dto.GitHubRepoDto;
import com.githubapi.infrastructure.controller.dto.request.CreateRepoRequestDto;
import com.githubapi.infrastructure.controller.dto.response.GitHubAllResult;
import com.githubapi.infrastructure.controller.error.WrongAcceptHeaderException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class GitHubService {

    private final GitHubServerProxy gitHubServerProxy;
    private final RepoAdder repoAdder;

    @Value("${github.api.url}")
    String url;

    public GitHubService(RepoAdder repoAdder, GitHubServerProxy gitHubServerProxy) {
        this.repoAdder = repoAdder;
        this.gitHubServerProxy = gitHubServerProxy;
    }

    public List<GitHubRepoDto> fetchListOfAllRepositoriesNames(String accept, String userName) {

        if (accept.equals("application/xml")) {
            log.info("Wrong header 'accept'.");
            throw new WrongAcceptHeaderException("Xml is not acceptable.");
        }

        List<GitHubRepoDto> allRepos = gitHubServerProxy.makeGetRequest(userName).getBody();
        return fetchReposWhichAreNotForks(allRepos);
    }

    public List<GitHubRepoDto> fetchReposWhichAreNotForks(List<GitHubRepoDto> allRepositories) {
        return allRepositories.stream()
                .filter(arg -> !arg.isFork())
                .toList();
    }

    public void addReposToDatabase(List<GitHubRepoDto> result) {
        for (GitHubRepoDto repo : result) {
            CreateRepoRequestDto createRepoRequestDto = new CreateRepoRequestDto(repo.name(), repo.owner().login());
            repoAdder.addRepo(createRepoRequestDto);
        }
    }

    public List<GitHubAllResult> fetchAllBranches(List<GitHubRepoDto> gitHubRepos) {
        List<GitHubAllResult> gitHubAllResult = new ArrayList<>();

        for (GitHubRepoDto repo : gitHubRepos) {
            List<GitHubBranchWithNameAndCommitDto> gitHubBranchWithNameAndShaAndUrl = gitHubServerProxy.makeGetBranchRequest(repo);
            List<GitHubBranchWithNameAndShaDto> gitHubBranchWithNameAndSha = gitHubBranchWithNameAndShaAndUrl.stream()
                    .map(branch -> new GitHubBranchWithNameAndShaDto(branch.name(), branch.commit().get("sha")))
                    .toList();

            GitHubAllResult branchToAdd = GitHubAllResult.builder()
                    .repoName(repo.name())
                    .ownerLogin(repo.owner().login())
                    .branches(gitHubBranchWithNameAndSha).build();

            gitHubAllResult.add(branchToAdd);
        }
        return gitHubAllResult;
    }
}
