package trask.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import trask.homework.entities.Technology;

// @Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

}