package com.product.app;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.product.exceptions.DataNotFoundException;
import com.product.exceptions.ErrorObject;
import com.product.exceptions.ResourseNotFoundException;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler  {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




@ExceptionHandler(ResourseNotFoundException.class)  

public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourseNotFoundException ex){
	
	ErrorObject eObject=new ErrorObject();
	eObject.setMessage(ex.getMessage());
	eObject.setStatusCode(HttpStatus.NOT_FOUND.value());
	eObject.setTimestemp(System.currentTimeMillis());
	return new ResponseEntity<ErrorObject>(eObject,HttpStatus.NOT_FOUND);
	
}
@ResponseBody
@ExceptionHandler(DataNotFoundException.class)  
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public ResponseEntity<ErrorObject> handleDataNotFoundException(DataNotFoundException ex){
	ErrorObject eObject=new ErrorObject();
	eObject.setMessage(ex.getMessage());
	eObject.setStatusCode(HttpStatus.NO_CONTENT.value());
	eObject.setTimestemp(System.currentTimeMillis());
	return new ResponseEntity<ErrorObject>(eObject,new HttpHeaders(),HttpStatus.OK);
	
}


@Override
protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex, HttpHeaders headers, 
    HttpStatus status, WebRequest request) {
	//ErrorObject eObject=new ErrorObject();
	
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDate.now());
    body.put("status", status.value());

    List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(x -> x.getDefaultMessage())
            .collect(Collectors.toList());

    body.put("message", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
}
}
