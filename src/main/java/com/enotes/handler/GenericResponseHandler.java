package com.enotes.handler;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder // no need to create object
public class GenericResponseHandler {

    private HttpStatus responseStatus;
    private String status;
    private Object data;
    private String message;

    public ResponseEntity<?> create(){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("status",status);
        map.put("message",message);
        //if data is available then it will put the data
        if (!ObjectUtils.isEmpty(data)){
            map.put("data", data);
        }
        return new ResponseEntity<>(map,responseStatus);
    }
}
