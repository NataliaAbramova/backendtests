package ru.geekbrans.img.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class ErrorResponse extends CommonResponse<ErrorResponse.ErrorData>{

    @Data
    public static class ErrorData{

        @JsonProperty("error")
        private String error;
        @JsonProperty("request")
        private String request;
        @JsonProperty("method")
        private String method;

    }

}
