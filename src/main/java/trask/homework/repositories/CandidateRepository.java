package trask.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import trask.homework.entities.Candidate;

// @Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}