package kr.co.metlife.pseudomgtchannelapi.dto.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    private long timestamp;
    private String username;
    private int count;
}
