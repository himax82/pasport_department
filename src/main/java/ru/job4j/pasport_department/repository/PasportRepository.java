package ru.job4j.pasport_department.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.pasport_department.domain.Pasport;

public interface PasportRepository extends CrudRepository<Pasport, Integer> {

    Iterable<Pasport> findBySeries(Integer series);

}
