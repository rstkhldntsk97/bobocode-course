package com.bobocode;

import com.bobocode.entity.Person;
import com.bobocode.orm.CustomSessionFactory;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * @author Rostyslav Kholodnytskyi
 * @date 13/12/2021
 * @project bobocode-course
 */
public class DemoApp {

    public static void main(String[] args) {
        var dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("root");

        var factory = new CustomSessionFactory(dataSource);
        var session = factory.createSession();
        var p1 = session.find(Person.class, 1);
        System.out.println(p1);
        var p2 = session.find(Person.class, 1);
        System.out.println(p1);
        System.out.println(p1 == p2);
        p1.setFirstName("Eazy");
        session.close();
        System.out.println(session.find(Person.class, 1));
    }

}
