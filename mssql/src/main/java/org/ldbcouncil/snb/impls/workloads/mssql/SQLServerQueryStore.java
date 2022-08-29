package org.ldbcouncil.snb.impls.workloads.mssql;

import java.util.ArrayList;
import java.util.List;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcInsert7AddComment;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.QueryType;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.mssql.converter.SQLServerConverter;

import com.google.common.collect.ImmutableMap;

public class SQLServerQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new SQLServerConverter();
    }

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public SQLServerQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

  /**
     * Get prepared UpdateQuery7 string
     * This is used for system requiring multiple updates.
     * @param operation UpdateQuery7 operation containing parameter values
     * @return List of prepared UpdateQuery7 strings
     */
    @Override
    public List<String> getInsert7Multiple(LdbcInsert7AddComment operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveInsert7AddComment,
                new ImmutableMap.Builder<String, Object>()
                        .put(LdbcInsert7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
                        .put(LdbcInsert7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.getCommentId()))
                        .put(LdbcInsert7AddComment.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                        .put(LdbcInsert7AddComment.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                        .put(LdbcInsert7AddComment.CONTENT, getConverter().convertString(operation.getContent()))
                        .put(LdbcInsert7AddComment.LENGTH, getConverter().convertInteger(operation.getLength()))
                        .put(LdbcInsert7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()))
                        .put(LdbcInsert7AddComment.COUNTRY_ID, getConverter().convertId(operation.getCountryId()))
                        .put(LdbcInsert7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.getReplyToPostId()))
                        .put(LdbcInsert7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.getReplyToCommentId()))
                        .build()
        ));
        for (long tagId : operation.getTagIds()) {
            list.add(prepare(
                    QueryType.InteractiveInsert7AddCommentTags,
                    ImmutableMap.of(
                        LdbcInsert7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()),
                        LdbcInsert7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.getCommentId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }

        // Update edges
        list.add(prepare(
            QueryType.InteractiveInsert7AddCommentEdges,
            ImmutableMap.of(
                LdbcInsert7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.getCommentId()),
                LdbcInsert7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.getReplyToPostId()),
                LdbcInsert7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.getReplyToCommentId())
            )
        ));

        // Update weights
        list.add(prepare(
            QueryType.InteractiveInsert7AddCommentWeights,
            ImmutableMap.of(
                LdbcInsert7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()),
                LdbcInsert7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.getReplyToPostId()),
                LdbcInsert7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.getReplyToCommentId())
            )
        ));
        return list;
    }

}
