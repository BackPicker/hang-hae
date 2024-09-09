package com.example.test.domain.msg;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMessage {

    private String msg;

    public ResponseMessage(String msg) {
        this.msg = msg;
    }
}
