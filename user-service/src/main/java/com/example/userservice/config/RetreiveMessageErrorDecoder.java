package com.example.userservice.config;

import com.example.userservice.exception.DepartmentNotFoundException;
import com.example.userservice.exception.ExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;

import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    public int staticCode;
    private final ErrorDecoder errorDecoder = new Default();

    Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;
        try (InputStream body = response.body().asInputStream()) {
            assert HttpStatus.resolve(response.status()) != null;
            message = new ExceptionMessage((String) response.headers().get("date").toArray()[0],
                    response.status(),
                    HttpStatus.resolve(response.status()).getReasonPhrase(),
                    IOUtils.toString(body, StandardCharsets.UTF_8),
                    response.request().url());
            staticCode=message.getStatus();

            logger.info(String.valueOf(message.getStatus()));

        } catch (IOException exception) {
            return new Exception(exception.getMessage());
        }
//        switch (response.status()) {
//            case 404:
//                logger.info("[department-service]{} ", message);
//                 throw  new DepartmentNotFoundException(message);
//            case 503:
//                System.out.println("503");
//            default:
//                return errorDecoder.decode(methodKey, response);
//        }
        return errorDecoder.decode(methodKey, response);
    }
}
