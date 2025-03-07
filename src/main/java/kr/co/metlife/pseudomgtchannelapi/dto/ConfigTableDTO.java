package kr.co.metlife.pseudomgtchannelapi.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ConfigTableDTO implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private String id;
    private String projectId;
    private String tableId;
    private String name;            // 원본 테이블명
    private String logicalName;     // 프로젝트 내 테이블 명
    private String explanation;
    private Integer iteration;
    private Integer sequence;
}
