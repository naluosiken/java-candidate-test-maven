package com.example.demo.dao;

import com.example.demo.entity.Test;

/**
 * @author luzy
 * @date 20/11/12 17:02
 */
public interface TestDao {

    Test getUserId(String id_one, String id_two);
}
