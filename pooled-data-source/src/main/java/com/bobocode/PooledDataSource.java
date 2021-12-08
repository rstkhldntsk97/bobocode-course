package com.bobocode;

import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Rostyslav Kholodnytskyi
 * @date 08/12/2021
 * @project bobocode-course
 */
public class PooledDataSource extends PGSimpleDataSource {

    private Queue<Connection> pool;

    @SneakyThrows
    public PooledDataSource(String url, String user, String password, int poolSize) {
        super();
        this.setUrl(url);
        this.setUser(user);
        this.setPassword(password);
        this.pool = new ConcurrentLinkedDeque<>();
        initializePool(poolSize);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return pool.poll();
    }

    private void initializePool(int poolSize) throws SQLException {
        for (int i = 0; i < poolSize; i++) {
            var physicalConnection = super.getConnection();
            var connectionProxy = new ProxyConnection(physicalConnection, pool);
            pool.add(connectionProxy);
        }
    }

}
