package com.bobocode.orm;

import com.bobocode.annotation.Column;
import com.bobocode.annotation.Table;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rostyslav Kholodnytskyi
 * @date 13/12/2021
 * @project bobocode-course
 */
@RequiredArgsConstructor
public class CustomSession {

    private final DataSource ds;
    Map<EntityKey<?>, Object> cache = new HashMap<>();
    Map<EntityKey<?>,  Object[]> snapshot = new HashMap<>();

    public <T> T find(Class<T> type, Object id) {
        var key = new EntityKey<>(type, id);
        var entity = cache.computeIfAbsent(key, this::loadFromDB);
        return type.cast(entity);
    }

    private <T> T loadFromDB(EntityKey<T> key) {
        var type = key.type();
        var id = key.id();
        try (Connection con = ds.getConnection()) {
            try (PreparedStatement statement = createStatement(type, con, id)) {
                ResultSet resultSet = statement.executeQuery();
                return createEntityFromRS(resultSet, key);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private <T> PreparedStatement createStatement(Class<T> type, Connection connection, Object id) {
        String sql = "SELECT * FROM " + type.getAnnotation(Table.class).value() + " WHERE id=?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return preparedStatement;
    }

    @SneakyThrows
    private <T> T createEntityFromRS(ResultSet rs, EntityKey<T> entityKey) {
        var type = entityKey.type();
        T object = type.getConstructor().newInstance();
        Field[] fields = Arrays.stream(type.getDeclaredFields()).toArray(Field[]::new);
        var snapshotCopies = new Object[fields.length];
        while (rs.next()) {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                var fieldValue = rs.getObject(fields[i].getAnnotation(Column.class).value());
                fields[i].set(object, fieldValue);
                snapshotCopies[i] = fieldValue;
            }
        }
        snapshot.put(entityKey, snapshotCopies);
        return object;
    }

    public void close() {
        cache.entrySet()
                .forEach(this::hasChanged);
        cache.clear();
        snapshot.clear();
    }

    private void updateValues(EntityKey<?> key, Field declaredField, String newValue) {
        String value = declaredField.getAnnotation(Column.class).value();
        String fieldName = key.type().getAnnotation(Table.class).value();
        String sql = "UPDATE " + fieldName + " SET " + value + "= '" + newValue + "' WHERE id =?";
        try (Connection con = ds.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(sql)){
                statement.setObject(1, key.id());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void hasChanged(Map.Entry<EntityKey<?>, Object> entry) {
        EntityKey<?> key = entry.getKey();
        Object[] fieldsValues = snapshot.get(key);
        Object value = entry.getValue();
        Field[] declaredFields = value.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            if (!(fieldsValues[i].equals(declaredFields[i].get(value)))) {
                updateValues(key, declaredFields[i], declaredFields[i].get(value).toString());
            }
        }
    }

}
