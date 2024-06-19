package com.githubapi.domain.service;

import com.githubapi.domain.model.Repo;
import com.githubapi.domain.repository.RepoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class RepoUpdater {

    private final RepoRepository repoRepository;

    public RepoUpdater(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public void updateRepoById(Long id, Repo repo) {
        repoRepository.updateRepoById(id, repo);

    }
}
