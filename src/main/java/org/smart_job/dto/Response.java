package org.smart_job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  Response <T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> Response<T> ok(T data) {
        return new Response<>(true, "Success", data);
    }

    public static <T> Response<T> ok(T data, String message) {
        return new Response<>(true, message, data);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(false, message, null);
    }

}