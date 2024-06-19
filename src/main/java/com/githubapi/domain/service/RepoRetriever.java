package com.githubapi.domain.service;

import com.githubapi.domain.model.Repo;
import com.githubapi.infrastructure.controller.error.RepoNotFoundException;
import com.githubapi.domain.repository.RepoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepoRetriever {

    private final RepoRepository repoRepository;

    public RepoRetriever(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public List<Repo> getAllRepositoriesFromDb(Pageable pageable) {
        return repoRepository.findAll(pageable);
    }

    public Repo getRepoById(Long id) {
        return repoRepository.findById(id).orElseThrow(() -> new RepoNotFoundException("Repository with id " + id + " not found."));
    }
}
