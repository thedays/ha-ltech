package com.ltech.smarthome.model;

/* loaded from: classes4.dex */
public class Resource<T> {
    private static final int ERROR = 2;
    private static final int LOADING = 3;
    private static final int SUCCESS = 1;
    private T data;
    private String message;
    private int status;

    private Resource(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isLoading() {
        return 3 == this.status;
    }

    public boolean isError() {
        return 2 == this.status;
    }

    public boolean isSuccess() {
        return 1 == this.status;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(1, data, null);
    }

    public static <T> Resource<T> error(String message, T data) {
        return new Resource<>(2, data, message);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<>(3, data, null);
    }
}