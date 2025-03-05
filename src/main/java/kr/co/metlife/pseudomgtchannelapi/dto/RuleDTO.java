package kr.co.metlife.pseudomgtchannelapi.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RuleDTO implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private String id;
    private String attributeName;
    private String nameKorean;
    private String nameEnglish;
    private Integer sequence;
    private String explanation;
    private String inputUserId;
    private LocalDateTime inputTimestamp;
    private String updateUserId;
    private LocalDateTime updateTimestamp;
}
