package com.dacosoft.entity;

import java.sql.SQLType;
import java.util.Objects;

/**
 * A structure that represents a database column.
 * For this demo it contains only 3 elements - can be extended for much more information.
 */
public final class DBColumn {
    private String name;
    private SQLType dataType;
    private String nullable;

    public DBColumn(String name, SQLType dataType, String nullable) {
        this.name = name;
        this.dataType = dataType;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public SQLType getDataType() {
        return dataType;
    }

    public String getNullable() {
        return nullable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBColumn dbColumn = (DBColumn) o;
        return name.equals(dbColumn.name) &&
                dataType.equals(dbColumn.dataType) &&
                nullable.equals(dbColumn.nullable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataType, nullable);
    }
}
