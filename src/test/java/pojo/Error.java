package pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Generated;
@Getter @Setter
@Jacksonized
@Builder  /*when we are using builder annotation then we have to use jacksonized annotation because builder annotation will not identify jackson annotation */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class Error {
    @JsonProperty("error")
    private InnerError error;
}
