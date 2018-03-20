package com.ldbc.snb.janusgraph.importer;

import java.util.*;

/**
 * Created by Tomer Sagi on 21-Sep-14.
 * Interactive Workload schema by LDBC
 * according to snb docs ver. 0.1.3
 */
public class InteractiveWorkloadSchema implements WorkLoadSchema {
    Map<String, Class<?>> pClassMap = new HashMap<>();
    HashMap<String, Integer> vTypes = new HashMap<>();
    HashMap<Integer, String> vTypeReverse = new HashMap<>();
    Set<String> eTypes = new HashSet<>(Arrays.asList(new String[]{"knows", "isLocatedIn", "studyAt", "workAt", "isPartOf", "hasCreator", "likes"
            , "hasTag", "containerOf", "hasMember", "hasModerator", "hasInterest", "hasType", "isSubclassOf", "replyOf"}));
    Map<String, Set<String>> vpMap = new HashMap<>();
    Map<String, Set<String>> ePMap = new HashMap<>();
    Map<String, String> eFileMap = new HashMap<>();
    Map<String, String> vpFileMap = new HashMap<>();
    Set<String> undirectedEdges = new HashSet<String>();

    public InteractiveWorkloadSchema() {
        super();

        vTypes.put("Place", 0);
        vTypes.put("Person", 1);
        vTypes.put("Organisation", 2);
        vTypes.put("Comment", 3);
        vTypes.put("Post", 4);
        vTypes.put("Forum", 5);
        vTypes.put("Tag", 6);
        vTypes.put("TagClass", 7);

        pClassMap.put("Person.id", Long.class);
        pClassMap.put("Place.id", Long.class);
        pClassMap.put("Forum.id", Long.class);
        pClassMap.put("Post.id", Long.class);
        pClassMap.put("Tag.id", Long.class);
        pClassMap.put("TagClass.id", Long.class);
        pClassMap.put("Comment.id", Long.class);
        pClassMap.put("Organisation.id", Long.class);

        pClassMap.put("creationDate", Long.class);
        pClassMap.put("firstName", String.class);
        pClassMap.put("lastName", String.class);
        pClassMap.put("gender", String.class);
        pClassMap.put("email", Arrays.class);
        pClassMap.put("birthday", Long.class);
        pClassMap.put("language", Arrays.class);
        pClassMap.put("browserUsed", String.class);
        pClassMap.put("locationIP", String.class);
        pClassMap.put("name", String.class);
        pClassMap.put("type", String.class);
        pClassMap.put("url", String.class);
        pClassMap.put("title", String.class);
        pClassMap.put("content", String.class);
        pClassMap.put("length", Integer.class);
        pClassMap.put("imageFile", String.class);

        pClassMap.put("classYear", Integer.class);
        pClassMap.put("workFrom", Integer.class);
        pClassMap.put("joinDate", Long.class);

        vpMap.put("Person", new HashSet<>(Arrays.asList(new String[]{"id","creationDate", "firstName", "lastName"
                , "gender", "birthday", "email", "language", "browserUsed", "locationIP"})));

        vpMap.put("Organisation", new HashSet<>(Arrays.asList(new String[]{"id","type", "name", "url"})));
        vpMap.put("Place", new HashSet<>(Arrays.asList(new String[]{"id","name", "url", "type"})));
        vpMap.put("Post", new HashSet<>(Arrays.asList(new String[]{"id","creationDate", "browserUsed", "locationIP", "content", "length", "language", "imageFile"})));
        vpMap.put("Comment", new HashSet<>(Arrays.asList(new String[]{"id","creationDate", "browserUsed", "locationIP", "content", "length"})));
        vpMap.put("Forum", new HashSet<>(Arrays.asList(new String[]{"id","title", "creationDate"})));
        vpMap.put("Tag", new HashSet<>(Arrays.asList(new String[]{"id","name", "url"})));
        vpMap.put("TagClass", new HashSet<>(Arrays.asList(new String[]{"id","name", "url"})));

        ePMap.put("knows", new HashSet<>(Arrays.asList(new String[]{"creationDate"})));
        ePMap.put("studyAt", new HashSet<>(Arrays.asList(new String[]{"classYear"})));
        ePMap.put("workAt", new HashSet<>(Arrays.asList(new String[]{"workFrom"})));
        ePMap.put("likes", new HashSet<>(Arrays.asList(new String[]{"creationDate"})));
        ePMap.put("hasMember", new HashSet<>(Arrays.asList(new String[]{"joinDate"})));

        undirectedEdges.add("knows");

        eFileMap.put("Comment.hasCreator.Person", "comment_hasCreator_person");
        eFileMap.put("Comment.hasTag.Tag", "comment_hasTag_tag");
        eFileMap.put("Comment.isLocatedIn.Place", "comment_isLocatedIn_place");
        eFileMap.put("Comment.replyOf.Comment", "comment_replyOf_comment");
        eFileMap.put("Comment.replyOf.Post", "comment_replyOf_post");
        eFileMap.put("Forum.containerOf.Post", "forum_containerOf_post");
        eFileMap.put("Forum.hasMember.Person", "forum_hasMember_person");
        eFileMap.put("Forum.hasModerator.Person", "forum_hasModerator_person");
        eFileMap.put("Forum.hasTag.Tag", "forum_hasTag_tag");
        eFileMap.put("Organisation.isLocatedIn.Place", "organisation_isLocatedIn_place");
        eFileMap.put("Person.hasInterest.Tag", "person_hasInterest_tag");
        eFileMap.put("Person.isLocatedIn.Place", "person_isLocatedIn_place");
        eFileMap.put("Person.knows.Person", "person_knows_person");
        eFileMap.put("Person.likes.Comment", "person_likes_comment");
        eFileMap.put("Person.likes.Post", "person_likes_post");
        eFileMap.put("Person.studyAt.Organisation", "person_studyAt_organisation");
        eFileMap.put("Person.workAt.Organisation", "person_workAt_organisation");
        eFileMap.put("Place.isPartOf.Place", "place_isPartOf_place");
        eFileMap.put("Post.hasCreator.Person", "post_hasCreator_person");
        eFileMap.put("Post.hasTag.Tag", "post_hasTag_tag");
        eFileMap.put("Post.isLocatedIn.Place", "post_isLocatedIn_place");
        eFileMap.put("TagClass.isSubclassOf.TagClass", "tagclass_isSubclassOf_tagclass");
        eFileMap.put("Tag.hasType.TagClass", "tag_hasType_tagclass");

        vpFileMap.put("Person.email", "person_email_emailaddress");
        vpFileMap.put("Person.language", "person_speaks_language");

    }


    @Override
    public Map<String, Integer> getVertexTypes() {
        return vTypes;
    }

    @Override
    public Set<String> getEdgeTypes() {
        return eTypes;
    }

    @Override
    public Map<String, Set<String>> getVertexProperties() {
        return vpMap;
    }

    @Override
    public Map<String, Set<String>> getEdgeProperties() {
        return ePMap;
    }

    @Override
    public Class<?> getPropertyClass(String propertyName) {
        return pClassMap.get(propertyName);
    }

    @Override
    public Map<String, String> getEFileMap() {
        return eFileMap;
    }


    @Override
    public Map<String, String> getVPFileMap() {
        return vpFileMap;
    }

    @Override
    public Set<String> getUndirectedEdges() {
        return undirectedEdges;
    }
}
