package ru.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.dreamjob.dto.FileDto;
import ru.dreamjob.model.Candidate;
import ru.dreamjob.repository.CandidateRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleCandidateService implements CandidateService {

    private final CandidateRepository candidateRepository;

    private final FileService fileService;

    public SimpleCandidateService(CandidateRepository candidateRepository, FileService fileService) {
        this.candidateRepository = candidateRepository;
        this.fileService = fileService;
    }

    @Override
    public Candidate save(Candidate candidate, FileDto image) {
        saveNewFile(candidate, image);
        return candidateRepository.save(candidate);
    }

    private void saveNewFile(Candidate candidate, FileDto image) {
        var file = fileService.save(image);
        candidate.setFileId(file.getId());
    }

    @Override
    public boolean update(Candidate candidate, FileDto image) {
        boolean isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return candidateRepository.update(candidate);
        }
        var oldFileId = candidate.getFileId();
        saveNewFile(candidate, image);
        fileService.deleteById(oldFileId);
        var isUpdated = candidateRepository.update(candidate);
        return isUpdated;
    }

    @Override
    public boolean deleteById(int id) {
        var candidateOptional = findById(id);
        if (candidateOptional.isEmpty()) {
            return false;
        }
        fileService.deleteById(candidateOptional.get().getFileId());
        return candidateRepository.deleteById(id);
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return candidateRepository.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidateRepository.findAll();
    }
}
