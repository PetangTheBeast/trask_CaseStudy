package trask.homework.dto;

import java.util.List;

public class CandidateWithTechnologiesDTO {
    private CandidateDTO candidate;
    private List<CandidateTechnologyDTO> technologies;

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    public List<CandidateTechnologyDTO> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<CandidateTechnologyDTO> technologies) {
        this.technologies = technologies;
    }
}
