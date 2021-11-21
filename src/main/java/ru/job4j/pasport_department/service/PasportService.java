package ru.job4j.pasport_department.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.pasport_department.domain.Pasport;
import ru.job4j.pasport_department.repository.PasportRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PasportService {

    private static final long DAY = 86_400_000;
    private static final double YEAR = 365.25;
    private static final double FOURTEEN = YEAR * 14;
    private static final double TWENTY = YEAR * 20;
    private static final double TWENTYONE = YEAR * 21;
    private static final double FORTYFIVE = YEAR * 45;
    private final Date nowDate = new Date(System.currentTimeMillis());

    @Autowired
    private PasportRepository pasportRepository;

    public Pasport saveOrUpdate(Pasport pasport) {
        pasportRepository.save(pasport);
        return pasport;
    }

    public Optional<Pasport> findById(int id) {
        return pasportRepository.findById(id);
    }

    public void delete(int id) {
        pasportRepository.deleteById(id);
    }

    public List<Pasport> findAll() {
        return (List<Pasport>) pasportRepository.findAll();
    }

    public List<Pasport> findBySeries(int series) {
        return (List<Pasport>) pasportRepository.findBySeries(series);
    }

    public List<Pasport> unavailable() {
        System.out.println("Start");
        List<Pasport> list = (List<Pasport>) pasportRepository.findAll();
        List<Pasport> listResult = new ArrayList();
        for (Pasport p : list) {
            double dayBirth = (nowDate.getTime() - p.getBirthday().getTime()) / DAY;
            System.out.println(dayBirth);
            double dayIssue = (nowDate.getTime() - p.getDateOfIssue().getTime()) / DAY;
            System.out.println(dayIssue);
            if ((dayBirth > TWENTY && (dayIssue + FOURTEEN) > TWENTY && dayBirth < TWENTYONE)
                    || (dayBirth > FORTYFIVE && (dayIssue + TWENTY) > FORTYFIVE)) {
                listResult.add(p);
            }
        }
        return listResult;
    }

    public List<Pasport> findAllReplaceable() {
        List<Pasport> list = (List<Pasport>) pasportRepository.findAll();
        List<Pasport> listResult = new ArrayList();
        for (Pasport p : list) {
            double dayBirth = (nowDate.getTime() - p.getBirthday().getTime()) / DAY;
            double dayIssue = (nowDate.getTime() - p.getDateOfIssue().getTime()) / DAY;
            if ((dayBirth < TWENTY && (TWENTY - dayIssue - FOURTEEN) < 90)
                    || (dayBirth < FORTYFIVE && (FORTYFIVE - TWENTY - dayIssue) < 90)) {
                listResult.add(p);
            }
        }
        return listResult;
    }

}
