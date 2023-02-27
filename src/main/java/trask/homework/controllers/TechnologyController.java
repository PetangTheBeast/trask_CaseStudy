package trask.homework.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trask.homework.entities.CandidateTechnology;
import trask.homework.entities.Technology;
import trask.homework.exception.AllErrors;
import trask.homework.repositories.CandidateTechnologyRepository;
import trask.homework.repositories.TechnologyRepository;

@RestController
@RequestMapping("/technology")
public class TechnologyController {

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private CandidateTechnologyRepository candidateTechnologyRepository;

    @GetMapping("")
    public List<Technology> getAll() {
        return technologyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Technology getById(@PathVariable Long id) {
        return technologyRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Technology with id " + id + " not found."));
    }

    @PostMapping("")
    public Technology create(@RequestBody Technology technology) { // Json example: {"name": "Java"}
        return technologyRepository.save(technology);
    }

    @PutMapping("/{id}")
    public Technology update(@PathVariable Long id, @RequestBody Technology technologyData) {
        Technology technology = technologyRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Technology with id " + id + " not found."));

        technology.setName(technologyData.getName());

        return technologyRepository.save(technology);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Technology technology = technologyRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Technology with id " + id + " not found."));

        List<CandidateTechnology> candidateTechnology = candidateTechnologyRepository.findByTechnologyId(id);
        if (candidateTechnology.size() > 0) {
            throw new AllErrors("Technology with id " + id + " is still connected to a candidate");
        }

        technologyRepository.delete(technology);
    }
}