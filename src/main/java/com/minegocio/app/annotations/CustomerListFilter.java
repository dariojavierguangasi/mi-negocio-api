package com.minegocio.app.annotations;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiImplicitParams(
        value = {
                @ApiImplicitParam(
                        name = "identificationNumber",
                        dataType = "string",
                        paramType = "query"
                ),
                @ApiImplicitParam(
                        name = "identificationType",
                        dataType = "string",
                        paramType = "query"
                ),
                @ApiImplicitParam(
                        name = "names",
                        dataType = "string",
                        paramType = "query"
                )

        }
)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerListFilter {
}
