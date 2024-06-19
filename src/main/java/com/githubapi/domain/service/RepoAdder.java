package com.githubapi.domain.service;

import com.githubapi.infrastructure.controller.dto.request.CreateRepoRequestDto;
import com.githubapi.domain.model.Repo;
import com.githubapi.domain.repository.RepoRepository;
import org.springframework.stereotype.Service;

@Service
public class RepoAdder {
    private final RepoRepository repoRepository;

    public RepoAdder(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public Repo addRepo(CreateRepoRequestDto request) {
        Repo repo = new Repo(null, request.owner(), request.name());
        return repoRepository.save(repo);
    }
}
