package dev.jefferson.email.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MultipartUploadExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;
    @ExceptionHandler
    public String handleFileUploadException(MaxUploadSizeExceededException exception, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "File size limit exceeded. Please make sure the file size is well within " + maxSize;
    }
}
