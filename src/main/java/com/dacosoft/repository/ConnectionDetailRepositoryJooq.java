package com.dacosoft.repository;

import org.jooq.DSLContext;
import org.jooq.example.db.h2.Tables;
import org.jooq.example.db.h2.tables.pojos.ConnectionDetail;
import org.jooq.example.db.h2.tables.records.ConnectionDetailRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.jooq.example.db.h2.tables.ConnectionDetail.CONNECTION_DETAIL;

@Component
public class ConnectionDetailRepositoryJooq {

    final
    DSLContext dsl;

    @Autowired
    public ConnectionDetailRepositoryJooq(DSLContext dsl) {
        this.dsl = dsl;
    }


    public List<ConnectionDetailRecord> findAll() {
        return dsl
                .selectFrom(Tables.CONNECTION_DETAIL)
                .fetch();
    }

    public ConnectionDetailRecord findById(int connectionDetailId) {
        return dsl
                .selectFrom(Tables.CONNECTION_DETAIL)
                .where(CONNECTION_DETAIL.ID.eq(connectionDetailId))
                .fetchSingle();
    }

    public void save(ConnectionDetail cd) {
        dsl.insertInto(Tables.CONNECTION_DETAIL,
                CONNECTION_DETAIL.DATABASENAME, CONNECTION_DETAIL.DESCRIPTION, CONNECTION_DETAIL.HOSTNAME, CONNECTION_DETAIL.NAME, CONNECTION_DETAIL.PASSWORD, CONNECTION_DETAIL.PORT, CONNECTION_DETAIL.USERNAME)
                .values(cd.getDatabasename(), cd.getDescription(), cd.getHostname(), cd.getName(), cd.getPassword(), cd.getPort(), cd.getUsername())
                .execute();
    }

    public void update(ConnectionDetail cd) {
        dsl.update(Tables.CONNECTION_DETAIL)
                .set(CONNECTION_DETAIL.DATABASENAME, cd.getDatabasename())
                .set(CONNECTION_DETAIL.DESCRIPTION, cd.getDescription())
                .set(CONNECTION_DETAIL.HOSTNAME, cd.getHostname())
                .set(CONNECTION_DETAIL.NAME, cd.getName())
                .set(CONNECTION_DETAIL.PASSWORD, cd.getPassword())
                .set(CONNECTION_DETAIL.PORT, cd.getPort())
                .set(CONNECTION_DETAIL.USERNAME, cd.getUsername())
                .where(CONNECTION_DETAIL.ID.eq(cd.getId()));
    }

    public void deleteById(int connectionDetailId) {
        dsl.delete(CONNECTION_DETAIL)
                .where(CONNECTION_DETAIL.ID.eq(connectionDetailId))
                .execute();
    }
}
