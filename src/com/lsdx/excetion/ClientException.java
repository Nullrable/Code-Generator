package com.lsdx.excetion;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 20:13
 * @Modified By：
 */
public class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }
}
