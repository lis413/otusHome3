package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String query = entitySQLMetaData.getSelectByIdSql();
        System.out.println("SELECT - " + query);
        return (Optional<T>) dbExecutor.executeSelect(connection, query, List.of(id), rs -> {
            try {
                if (rs.next()) {
                    T instance = createEmptyObject();
                    restoreObjectFields(rs, instance);
                    return instance;
//                        return new Client(rs.getLong("id"), rs.getString("name"));
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        });
        // return null;
    }

    @Override
    public List<T> findAll(Connection connection) {
        String query = entitySQLMetaData.getSelectAllSql();
        List<T> list = new ArrayList<>();
        return dbExecutor.executeSelect(connection, query, null, rs -> {
            try {
                while (rs.next()) {
                    T instance = createEmptyObject();
                    restoreObjectFields(rs, instance);
                    list.add(instance);
                }
                return list;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        String query = entitySQLMetaData.getInsertSql();
        List<Field> fields = entitySQLMetaData.getEntityClassMetaData().getFieldsWithoutId();
        List<Object> params = getFieldsValues(client, fields);
        long id = dbExecutor.executeStatement(connection, query, params);
        return id;
    }

    @Override
    public void update(Connection connection, T client) {
        String query = entitySQLMetaData.getUpdateSql();
        List<Field> fields = entitySQLMetaData.getEntityClassMetaData().getFieldsWithoutId();
        fields.add(entitySQLMetaData.getEntityClassMetaData().getIdField());
        List<Object> params = getFieldsValues(client, fields);
        dbExecutor.executeStatement(connection, query, params);
        throw new UnsupportedOperationException();
    }

    public List<Object> getFieldsValues(T objectData, List<Field> fields) {
        List<Object> values = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                values.add(field.get(objectData));
            } catch (IllegalAccessException e) {

            }
        }
        return values;
    }

    public T createEmptyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<T> constructor = entitySQLMetaData.getEntityClassMetaData().getConstructor();
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int idx = 0; idx < params.length; idx++) {
            params[idx] = paramTypes[idx].isPrimitive() ? 0 : null;
        }
        return constructor.newInstance(params);
    }

    private void restoreObjectFields(ResultSet rs, T instance) throws SQLException, IllegalAccessException {
        List<Field> fields = entitySQLMetaData.getEntityClassMetaData().getAllFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            field.setAccessible(true);
            Object value = rs.getObject(fieldName);
            field.set(instance, value);
        }
    }
}
