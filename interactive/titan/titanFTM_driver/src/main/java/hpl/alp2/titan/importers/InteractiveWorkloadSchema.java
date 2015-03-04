package hpl.alp2.titan.importers;

import java.util.*;

/**
 * Created by Tomer Sagi on 21-Sep-14.
 * Interactive Workload schema by LDBC
 * according to snb docs ver. 0.1.3
 */
public class InteractiveWorkloadSchema implements WorkLoadSchema {
    Map<String, Class<?>> vPClassMap = new HashMap<>();
    Map<String, Class<?>> ePClassMap = new HashMap<>();
    HashMap<String, Integer> vTypes = new HashMap<>();
    HashMap<Integer, String> vTypeReverse = new HashMap<>();
    Set<String> eTypes = new HashSet<>(Arrays.asList(new String[]{"knows", "isLocatedIn", "studyAt", "workAt", "isPartOf", "hasCreator", "likes"
            , "hasTag", "containerOf", "hasMember", "hasModerator", "hasInterest", "hasType", "isSubclassOf", "replyOf"}));
    Map<String, Set<String>> vpMap = new HashMap<>();
    Map<String, Set<String>> ePMap = new HashMap<>();
    Map<String, String> eFileMap = new HashMap<>();
    Map<String, String> vpFileMap = new HashMap<>();

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

        vTypeReverse.put(0, "Place");
        vTypeReverse.put(1, "Person");
        vTypeReverse.put(2, "Organisation");
        vTypeReverse.put(3, "Comment");
        vTypeReverse.put(4, "Post");
        vTypeReverse.put(5, "Forum");
        vTypeReverse.put(6, "Tag");
        vTypeReverse.put(7, "TagClass");

        vPClassMap.put("Person.creationDate", Date.class);
        vPClassMap.put("Person.firstName", String.class);
        vPClassMap.put("Person.lastName", String.class);
        vPClassMap.put("Person.gender", String.class);
        vPClassMap.put("Person.email", Arrays.class);
        vPClassMap.put("Person.birthday", Date.class);
        vPClassMap.put("Person.language", Arrays.class);
        vPClassMap.put("Person.browserUsed", String.class);
        vPClassMap.put("Person.locationIP", String.class);
        vPClassMap.put("Place.name", String.class);
        vPClassMap.put("Place.type", String.class);
        vPClassMap.put("Place.url", String.class);
        vPClassMap.put("Organisation.name", String.class);
        vPClassMap.put("Organisation.type", String.class);
        vPClassMap.put("Organisation.url", String.class);
        vPClassMap.put("Forum.title", String.class);
        vPClassMap.put("Forum.creationDate", Date.class);
        vPClassMap.put("Post.creationDate", Date.class);
        vPClassMap.put("Post.browserUsed", String.class);
        vPClassMap.put("Post.locationIP", String.class);
        vPClassMap.put("Post.content", String.class);
        vPClassMap.put("Post.length", Integer.class);
        /*
        This is a hack due to the fact that Titan has
        global property keys and language has single
        cardinality here and list cardinality in Person.language
         */
        vPClassMap.put("Post.lang", String.class);
        vPClassMap.put("Post.imageFile", String.class);
        vPClassMap.put("Comment.creationDate", Date.class);
        vPClassMap.put("Comment.browserUsed", String.class);
        vPClassMap.put("Comment.locationIP", String.class);
        vPClassMap.put("Comment.content", String.class);
        vPClassMap.put("Comment.length", Integer.class);
        vPClassMap.put("Tag.name", String.class);
        vPClassMap.put("Tag.url", String.class);
        vPClassMap.put("TagClass.name", String.class);
        vPClassMap.put("TagClass.url", String.class);

        ePClassMap.put("knows.creationDate", Date.class);
        ePClassMap.put("studyAt.classYear", Integer.class);
        ePClassMap.put("workAt.workFrom", Integer.class);
        ePClassMap.put("likes.creationDate", Date.class);
        ePClassMap.put("hasMember.joinDate", Date.class);

        vpMap.put("Person", new HashSet<>(Arrays.asList(new String[]{"creationDate", "firstName", "lastName"
                , "gender", "birthday", "email", "language", "browserUsed", "locationIP"})));
        vpMap.put("Organisation", new HashSet<>(Arrays.asList(new String[]{"type", "name", "url"})));
        vpMap.put("Place", new HashSet<>(Arrays.asList(new String[]{"name", "url", "type"})));
        vpMap.put("Post", new HashSet<>(Arrays.asList(new String[]{"creationDate", "browserUsed", "locationIP", "content", "length", "lang", "imageFile"})));
        vpMap.put("Comment", new HashSet<>(Arrays.asList(new String[]{"creationDate", "browserUsed", "locationIP", "content", "length"})));
        vpMap.put("Forum", new HashSet<>(Arrays.asList(new String[]{"title", "creationDate"})));
        vpMap.put("Tag", new HashSet<>(Arrays.asList(new String[]{"name", "url"})));
        vpMap.put("TagClass", new HashSet<>(Arrays.asList(new String[]{"name", "url"})));

        ePMap.put("knows", new HashSet<>(Arrays.asList(new String[]{"creationDate"})));
        ePMap.put("studyAt", new HashSet<>(Arrays.asList(new String[]{"classYear"})));
        ePMap.put("workAt", new HashSet<>(Arrays.asList(new String[]{"workFrom"})));
        ePMap.put("likes", new HashSet<>(Arrays.asList(new String[]{"creationDate"})));
        ePMap.put("hasMember", new HashSet<>(Arrays.asList(new String[]{"joinDate"})));

        eFileMap.put("Comment.hasCreator.Person", "comment_hasCreator_person_0.csv");
        eFileMap.put("Comment.hasTag.Tag", "comment_hasTag_tag_0.csv");
        eFileMap.put("Comment.isLocatedIn.Place", "comment_isLocatedIn_place_0.csv");
        eFileMap.put("Comment.replyOf.Comment", "comment_replyOf_comment_0.csv");
        eFileMap.put("Comment.replyOf.Post", "comment_replyOf_post_0.csv");
        eFileMap.put("Forum.containerOf.Post", "forum_containerOf_post_0.csv");
        eFileMap.put("Forum.hasMember.Person", "forum_hasMember_person_0.csv");
        eFileMap.put("Forum.hasModerator.Person", "forum_hasModerator_person_0.csv");
        eFileMap.put("Forum.hasTag.Tag", "forum_hasTag_tag_0.csv");
        eFileMap.put("Organisation.isLocatedIn.Place", "organisation_isLocatedIn_place_0.csv");
        eFileMap.put("Person.hasInterest.Tag", "person_hasInterest_tag_0.csv");
        eFileMap.put("Person.isLocatedIn.Place", "person_isLocatedIn_place_0.csv");
        eFileMap.put("Person.knows.Person", "person_knows_person_0.csv");
        eFileMap.put("Person.likes.Comment", "person_likes_comment_0.csv");
        eFileMap.put("Person.likes.Post", "person_likes_post_0.csv");
        eFileMap.put("Person.studyAt.Organisation", "person_studyAt_organisation_0.csv");
        eFileMap.put("Person.workAt.Organisation", "person_workAt_organisation_0.csv");
        eFileMap.put("Place.isPartOf.Place", "place_isPartOf_place_0.csv");
        eFileMap.put("Post.hasCreator.Person", "post_hasCreator_person_0.csv");
        eFileMap.put("Post.hasTag.Tag", "post_hasTag_tag_0.csv");
        eFileMap.put("Post.isLocatedIn.Place", "post_isLocatedIn_place_0.csv");
        eFileMap.put("TagClass.isSubclassOf.TagClass", "tagclass_isSubclassOf_tagclass_0.csv");
        eFileMap.put("Tag.hasType.TagClass", "tag_hasType_tagclass_0.csv");

        vpFileMap.put("Person.email", "person_email_emailaddress_0.csv");
        vpFileMap.put("Person.language", "person_speaks_language_0.csv");

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
    public Class<?> getVPropertyClass(String vertexType, String propertyName) {
        return vPClassMap.get(vertexType + "." + propertyName);
    }

    @Override
    public Class<?> getEPropertyClass(String edgeType, String propertyName) {
        return ePClassMap.get(edgeType + "." + propertyName);
    }

    @Override
    public Map<String, String> getEFileMap() {
        return eFileMap;
    }

//    @Override
//    public Map<String, String> getVFileMap() {
//        return vFileMap;
//    }

    @Override
    public Map<String, String> getVPFileMap() {
        return vpFileMap;
    }

    public Map<Integer, String> getVertexTypesReverse() {
        return this.vTypeReverse;
    }
}
