package com.demo.restapi.config.springdoc;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class GlobalOperationCustomizer implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {

        Parameter parameter = new Parameter()
                .in(ParameterIn.HEADER.toString())
                .name("X-AUTH-TOKEN")
                .description("로그인 성공 Access Token")
                .schema(new StringSchema())
                .required(false);

        operation.addParametersItem(parameter);

        return operation;
    }
}
