package ru.job4j.pasport_department.controlle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.pasport_department.domain.Pasport;
import ru.job4j.pasport_department.service.PasportService;

import java.util.List;

@RestController
public class PasportController {

    @Autowired
    private final PasportService pasportService;

    public PasportController(PasportService pasportService) {
        this.pasportService = pasportService;
    }

    @GetMapping({ "/find"})
    public List<Pasport> findAll() {
        return pasportService.findAll();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Pasport pasport) {
        pasportService.findById(pasport.getId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person with id " + pasport.getId() + " is not found."
        ));
        pasportService.saveOrUpdate(pasport);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Pasport> save(@RequestBody Pasport pasport) {
        return new ResponseEntity<>(pasportService.saveOrUpdate(pasport), HttpStatus.CREATED);
    }

    @GetMapping({ "/findseries/{series}"})
    public List<Pasport> findBySeries(@PathVariable("series") int series) {
        return pasportService.findBySeries(series);
    }

    @GetMapping({ "/unavailable"})
    public List<Pasport> findAllUnavailable() {
        return pasportService.unavailable();
    }

    @GetMapping({ "/find-replaceable"})
    public List<Pasport> findAllReplaceable() {
        return pasportService.findAllReplaceable();
    }

    @DeleteMapping("/delete?id={id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        pasportService.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person with id " + id + " is not found."
        ));
        pasportService.delete(id);
        return ResponseEntity.ok().build();
    }

}
