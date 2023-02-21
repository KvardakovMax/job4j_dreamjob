package ru.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.dreamjob.model.Vacancy;
import ru.dreamjob.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleVacancyService implements VacancyService {

    private final VacancyRepository repository;

    public SimpleVacancyService(VacancyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return repository.save(vacancy);
    }

    @Override
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return repository.update(vacancy);
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Vacancy> findAll() {
        return repository.findAll();
    }
}
