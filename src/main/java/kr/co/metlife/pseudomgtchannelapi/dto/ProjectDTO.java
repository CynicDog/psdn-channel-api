package kr.co.metlife.pseudomgtchannelapi.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectDTO implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private String id;
    private String username;
    private String name;
    private Integer sequence;
    private String status;
    private String explanation;
    private LocalDateTime createTimestamp;
    private LocalDateTime approveTimestamp;
    private LocalDateTime startTimestamp;
    private LocalDateTime finishTimestamp;

    private List<ConfigTableDTO> configTables;

    public ProjectDTO(String id, String username, String name, Integer sequence, String status, String explanation, LocalDateTime createTimestamp, LocalDateTime approveTimestamp, LocalDateTime startTimestamp, LocalDateTime finishTimestamp, List<ConfigTableDTO> configTables) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.sequence = sequence;
        this.status = status;
        this.explanation = explanation;
        this.createTimestamp = createTimestamp;
        this.approveTimestamp = approveTimestamp;
        this.startTimestamp = startTimestamp;
        this.finishTimestamp = finishTimestamp;
        this.configTables = configTables;
    }
}