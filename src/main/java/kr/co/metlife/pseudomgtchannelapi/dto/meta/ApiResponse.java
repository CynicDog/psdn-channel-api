package kr.co.metlife.pseudomgtchannelapi.dto.meta;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T item;
    private Metadata metadata;
}