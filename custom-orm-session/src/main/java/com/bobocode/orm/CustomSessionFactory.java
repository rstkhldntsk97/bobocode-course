package com.bobocode.orm;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

/**
 * @author Rostyslav Kholodnytskyi
 * @date 13/12/2021
 * @project bobocode-course
 */
@RequiredArgsConstructor
public class CustomSessionFactory {

    private final DataSource ds;

    public CustomSession createSession() {

        return new CustomSession(ds);

    }

}
