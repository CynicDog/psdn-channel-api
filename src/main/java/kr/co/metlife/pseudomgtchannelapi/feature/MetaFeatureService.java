package kr.co.metlife.pseudomgtchannelapi.feature;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ParameterDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.RuleDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.TableDTO;

import java.util.List;

public interface MetaFeatureService {

    List<RuleDTO> getRules() throws JsonProcessingException;

    List<ParameterDTO> getParameters() throws JsonProcessingException;

    List<TableDTO> getTables() throws JsonProcessingException;
}
