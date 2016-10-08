package com.cabify.getaride.data.entity.response.utils;

import com.cabify.getaride.data.entity.response.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class ErrorConversor {

    public static ErrorResponse parseError(Retrofit retrofit, Response<?> response) {
        ErrorResponse error;

        try {
            Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(Error.class, new Annotation[0]);
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }

        return error;
    }
}
