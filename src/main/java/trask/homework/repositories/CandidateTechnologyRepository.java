package trask.homework.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import trask.homework.entities.CandidateTechnology;

// @Repository
public interface CandidateTechnologyRepository extends JpaRepository<CandidateTechnology, Long> {

    List<CandidateTechnology> findByCandidateId(Long candidateId);

    List<CandidateTechnology> findByTechnologyId(Long technologyId);

    Optional<CandidateTechnology> findByCandidateIdAndTechnologyId(Long candidateId, Long technologyId);

    boolean existsByCandidateIdAndTechnologyId(Long candidateId, Long technologyId);
}