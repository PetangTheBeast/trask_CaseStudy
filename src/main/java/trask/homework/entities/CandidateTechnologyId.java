package trask.homework.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class CandidateTechnologyId implements Serializable {

    private Long candidateId;

    private Long technologyId;

    // getters and setters

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }
}