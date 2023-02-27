package trask.homework.dto;

public class CandidateTechnologyDTO {
    private Long technology_id;
    private int level;
    private String note;

    // constructors, getters, and setters

    public CandidateTechnologyDTO(Long technology_id, int level, String note) {
        this.technology_id = technology_id;
        this.level = level;
        this.note = note;
    }

    public Long getTechnology_id() {
        return technology_id;
    }

    public void setTechnology_id(Long technology_id) {
        this.technology_id = technology_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}