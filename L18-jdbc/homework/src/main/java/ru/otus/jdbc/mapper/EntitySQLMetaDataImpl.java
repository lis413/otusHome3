package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {

        return  "select * from " +  entityClassMetaData.getName();

    }

    @Override
    public String getSelectByIdSql() {
        String sql = String.format("select * from %s where %s = ?", entityClassMetaData.getName(),
                                                                    entityClassMetaData.getIdField().getName());
        return sql;
    }

    @Override
    public String getInsertSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        StringBuilder pole = new StringBuilder();
        StringBuilder znak = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (i+1 != fields.size()){
                znak.append("?, ");
                pole.append(fields.get(i).getName() + ", ");
            } else {
                pole.append(fields.get(i).getName());
                znak.append("?");
            }
        }
        return String.format("insert into %s (%s) values ( %s );", entityClassMetaData.getName(), pole, znak.toString());
    }

    @Override
    public String getUpdateSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        StringBuilder stroka = new StringBuilder();
        for (int i = 1; i <= fields.size(); i++) {
            if (i != fields.size()){
                stroka.append( fields.get(i).getName() + " = ? , ");
            } else {
                stroka.append(fields.get(i).getName() + " = ?");
            }
        }
        String sql = String.format("Update %s Set %s where %s = ?",
                                    entityClassMetaData.getName(), stroka, entityClassMetaData.getIdField());
        return sql;
    }

    public EntityClassMetaData getEntityClassMetaData() {
        return entityClassMetaData;
    }
}
