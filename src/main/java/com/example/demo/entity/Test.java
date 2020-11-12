package com.example.demo.entity;

import java.io.Serializable;

/**
 * @author luzy
 * @date 20/11/12 16:58
 */
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id_one;
    private String id_two;

    public String getId_one() {
        return id_one;
    }

    public void setId_one(String id_one) {
        this.id_one = id_one;
    }

    public String getId_two() {
        return id_two;
    }

    public void setId_two(String id_two) {
        this.id_two = id_two;
    }
}
