package trask.homework.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trask.homework.dto.CandidateDTO;
import trask.homework.dto.CandidateTechnologyDTO;
import trask.homework.dto.CandidateWithTechnologiesDTO;
import trask.homework.entities.Candidate;
import trask.homework.entities.CandidateTechnology;
import trask.homework.entities.Technology;
import trask.homework.exception.AllErrors;
import trask.homework.repositories.CandidateRepository;
import trask.homework.repositories.CandidateTechnologyRepository;
import trask.homework.repositories.TechnologyRepository;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private CandidateTechnologyRepository candidateTechnologyRepository;

    @GetMapping("")
    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    @GetMapping("/technology")
    public List<CandidateWithTechnologiesDTO> getAllWithTechnology() {
        List<CandidateWithTechnologiesDTO> candidateWithTechnologiesDTO = new ArrayList<>();

        List<Candidate> candidates = candidateRepository.findAll();

        candidates.forEach(c -> {
            CandidateWithTechnologiesDTO cwtDTO = new CandidateWithTechnologiesDTO();
            cwtDTO.setCandidate(new CandidateDTO(c.getName(), c.getSurname(), c.getEmail()));

            List<CandidateTechnologyDTO> ctDTO = new ArrayList<>();
            ;
            List<CandidateTechnology> candidateTechnologies = candidateTechnologyRepository
                    .findByCandidateId(c.getId());
            candidateTechnologies.forEach(ct -> {
                ctDTO.add(new CandidateTechnologyDTO(ct.getTechnology().getId(), ct.getLevel(), ct.getNote()));
            });
            cwtDTO.setTechnologies(ctDTO);

            candidateWithTechnologiesDTO.add(cwtDTO);
        });

        return candidateWithTechnologiesDTO;
    }

    @GetMapping("/{id}")
    public Candidate getById(@PathVariable Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Candidate with id " + id + " not found."));
    }

    @GetMapping("/{id}/technology")
    public CandidateWithTechnologiesDTO getByIdWithTechnologies(@PathVariable Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Candidate with id " + id + " not found."));

        CandidateWithTechnologiesDTO cwtDTO = new CandidateWithTechnologiesDTO();
        cwtDTO.setCandidate(new CandidateDTO(candidate.getName(), candidate.getSurname(), candidate.getEmail()));

        List<CandidateTechnologyDTO> ctDTO = new ArrayList<>();
        ;
        List<CandidateTechnology> candidateTechnologies = candidateTechnologyRepository
                .findByCandidateId(candidate.getId());
        candidateTechnologies.forEach(ct -> {
            ctDTO.add(new CandidateTechnologyDTO(ct.getTechnology().getId(), ct.getLevel(), ct.getNote()));
        });
        cwtDTO.setTechnologies(ctDTO);

        return cwtDTO;
    }

    /**
     * Create candidate with candidate technologies
     * 
     * @param candidateWithTechnologiesDTO
     * @return
     */
    @PostMapping("")
    public Candidate create(@RequestBody CandidateWithTechnologiesDTO candidateWithTechnologiesDTO) {
        Candidate candidate = new Candidate();
        candidate.setName(candidateWithTechnologiesDTO.getCandidate().getName());
        candidate.setSurname(candidateWithTechnologiesDTO.getCandidate().getSurname());
        candidate.setEmail(candidateWithTechnologiesDTO.getCandidate().getEmail());
        candidateRepository.save(candidate);

        candidateWithTechnologiesDTO.getTechnologies().forEach(ct -> {
            CandidateTechnology candidateTechnology = new CandidateTechnology();
            Long tech_id = ct.getTechnology_id();
            Technology technology = technologyRepository.findById(tech_id)
                    .orElseThrow(() -> new AllErrors("Technology with id " + ct.getTechnology_id() + " not found."));

            candidateTechnology.setCandidate(candidate);
            candidateTechnology.setTechnology(technology);
            candidateTechnology.setLevel(ct.getLevel());
            candidateTechnology.setNote(ct.getNote());

            candidateTechnologyRepository.save(candidateTechnology);
        });
        return candidate;
    }

    /**
     * Add technology to candidate
     * 
     * @param id
     * @param candidateTechnologyDTO
     * @return
     */
    @PostMapping("/{id}/technology")
    public CandidateTechnology addTechnology(@PathVariable Long id,
            @RequestBody CandidateTechnologyDTO candidateTechnologyDTO) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Candidate with id " + id + " not found."));
        Technology technology = technologyRepository.findById(candidateTechnologyDTO.getTechnology_id())
                .orElseThrow(() -> new AllErrors(
                        "Technology with id " + candidateTechnologyDTO.getTechnology_id() + " not found."));
        if (candidateTechnologyRepository.existsByCandidateIdAndTechnologyId(id,
                candidateTechnologyDTO.getTechnology_id())) {
            throw new AllErrors("Candidate with id " + id + " does already have a relation with technology id: "
                    + candidateTechnologyDTO.getTechnology_id());
        }

        CandidateTechnology candidateTechnology = new CandidateTechnology();
        candidateTechnology.setCandidate(candidate);
        candidateTechnology.setTechnology(technology);
        candidateTechnology.setLevel(candidateTechnologyDTO.getLevel());
        candidateTechnology.setNote(candidateTechnologyDTO.getNote());

        return candidateTechnologyRepository.save(candidateTechnology);
    }

    /**
     * Update only candidate
     * 
     * @param id
     * @param candidateDTO
     * @return
     */
    @PutMapping("/{id}")
    public Candidate updateCandidate(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Candidate with id " + id + " not found."));

        candidate.setName(candidateDTO.getName());
        candidate.setSurname(candidateDTO.getSurname());
        candidate.setEmail(candidateDTO.getEmail());

        return candidateRepository.save(candidate);
    }

    /**
     * Update candidate and candidates technologies
     * 
     * @param id
     * @param candidateWithTechnologiesDTO
     * @return
     */
    @PutMapping("/{id}/technology")
    public Candidate updateWithTechnology(@PathVariable Long id,
            @RequestBody CandidateWithTechnologiesDTO candidateWithTechnologiesDTO) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Candidate with id " + id + " not found."));

        deleteCandidateTechnologiesByCandidateId(id);

        candidate.setName(candidateWithTechnologiesDTO.getCandidate().getName());
        candidate.setSurname(candidateWithTechnologiesDTO.getCandidate().getSurname());
        candidate.setEmail(candidateWithTechnologiesDTO.getCandidate().getEmail());
        candidateRepository.save(candidate);

        candidateWithTechnologiesDTO.getTechnologies().forEach(ct -> {
            // CandidateTechnology candidateTechnology = new CandidateTechnology();
            Technology technology = technologyRepository.findById(ct.getTechnology_id())
                    .orElseThrow(() -> new AllErrors("Technology with id " + ct.getTechnology_id() + " not found."));

            Optional<CandidateTechnology> optionalCandidateTechnology = candidateTechnologyRepository
                    .findByCandidateIdAndTechnologyId(id, ct.getTechnology_id());
            CandidateTechnology candidateTechnology;
            if (optionalCandidateTechnology.isEmpty()) {
                candidateTechnology = new CandidateTechnology();
            } else {
                candidateTechnology = optionalCandidateTechnology.get();
            }

            candidateTechnology.setCandidate(candidate);
            candidateTechnology.setTechnology(technology);
            candidateTechnology.setLevel(ct.getLevel());
            candidateTechnology.setNote(ct.getNote());

            candidateTechnologyRepository.save(candidateTechnology);
        });
        return candidate;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new AllErrors("Candidate with id " + id + " not found."));

        deleteCandidateTechnologiesByCandidateId(id);

        candidateRepository.delete(candidate);
    }

    /**
     * Delete candidates technology
     * 
     * @param candidateId
     * @param technologyId
     */
    @DeleteMapping("/{candidateId}/technology/{technologyId}")
    public void deleteTechnology(@PathVariable Long candidateId, @PathVariable Long technologyId) {
        candidateRepository.findById(candidateId)
                .orElseThrow(() -> new AllErrors("Candidate with id " + candidateId + " not found."));
        CandidateTechnology candidateTechnologies = candidateTechnologyRepository
                .findByCandidateIdAndTechnologyId(candidateId, technologyId)
                .orElseThrow(() -> new AllErrors("Candidate with id: " + candidateId +
                        " does not have relation with technology id: " + technologyId));

        candidateTechnologyRepository.delete(candidateTechnologies);
    }

    public void deleteCandidateTechnologiesByCandidateId(Long id) {
        List<CandidateTechnology> candidateTechnologies = candidateTechnologyRepository.findByCandidateId(id);
        candidateTechnologies.forEach(ct -> {
            candidateTechnologyRepository.delete(ct);
        });
    }
}