package org.endeavourhealth.cim.common.data;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.cim.common.stream.StreamExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepositoryHelper {

    public static Row getSingleRowFromId(
            Session session,
            PreparedStatementCache statementCache,
            String tableName,
            String idColumnName,
            UUID id) {

        if (id == null)
            throw new IllegalArgumentException("id is null");

        PreparedStatement preparedStatement = statementCache.getOrAdd(QueryBuilder.select()
                .all()
                .from(tableName)
                .where(QueryBuilder.eq(idColumnName, QueryBuilder.bindMarker(idColumnName))));

        Row row = session.execute(preparedStatement
                .bind()
                .setUUID(idColumnName, id))
                .all()
                .stream()
                .collect(StreamExtension.singleOrNullCollector());

        return row;
    }

    public static List<Row> getRowsById(
            Session session,
            PreparedStatementCache statementCache,
            String tableName,
            String idColumnName,
            UUID id) {

        if (id == null)
            throw new IllegalArgumentException("id is null");

        PreparedStatement preparedStatement = statementCache.getOrAdd(QueryBuilder.select()
                .all()
                .from(tableName)
                .where(QueryBuilder.eq(idColumnName, QueryBuilder.bindMarker(idColumnName))));

        BoundStatement boundStatement = preparedStatement
                .bind()
                .setUUID(idColumnName, id);

        List<Row> row = session.execute(boundStatement)
                .all()
                .stream()
                .collect(Collectors.toList());

        return row;
    }

    public static List<Row> getRowsByIds(
            Session session,
            PreparedStatementCache statementCache,
            String tableName,
            String idColumnName,
            List<UUID> ids) {

        if (ids == null)
            throw new IllegalArgumentException("ids is null");

        List<Row> results = new ArrayList<>();

        PreparedStatement preparedStatement = statementCache.getOrAdd(QueryBuilder.select()
                .all()
                .from(tableName)
                .where(QueryBuilder.eq(idColumnName, QueryBuilder.bindMarker(idColumnName))));

        List<ResultSetFuture> futures = new ArrayList<>();

        for (UUID id : ids) {
            BoundStatement boundStatement = preparedStatement
                    .bind()
                    .setUUID(idColumnName, id);
            futures.add(session.executeAsync(boundStatement));
        }

        for (ResultSetFuture future : futures) {
            Row row = future.getUninterruptibly()
                    .all()
                    .stream()
                    .collect(StreamExtension.singleOrNullCollector());

            if (row != null)
                results.add(row);
        }

        return results;
    }

    public static List<Row> getAll(
            Session session,
            PreparedStatementCache statementCache,
            String tableName) {

        PreparedStatement preparedStatement = statementCache.getOrAdd(QueryBuilder.select()
                .all()
                .from(tableName));

        List<Row> results = session.execute(preparedStatement.bind())
                .all();

        return results;
    }
}
