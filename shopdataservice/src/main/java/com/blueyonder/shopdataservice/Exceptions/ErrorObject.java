package com.blueyonder.shopdataservice.Exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorObject {

    private HttpStatus status;
    private String trace;
    private String message;
    private String debug_message;
    public ErrorObject(HttpStatus status, Exception ex){
        this.status = status;
        this.trace = getTrace(ex);
        this.message = ex.getLocalizedMessage();
    }

    private String getTrace(Exception ex){
        StringWriter traceObj = new StringWriter();
        ex.printStackTrace(new PrintWriter(traceObj));
        return traceObj.toString();
    }
    public String getResponse(){
        return getMessage();
    }

    public String getResponse(String prefix, String suffix){
        return prefix+getMessage()+suffix;
    }
}
