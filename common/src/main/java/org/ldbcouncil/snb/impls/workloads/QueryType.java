package org.ldbcouncil.snb.impls.workloads;
/**
 * Enum containing all query file names without extension.
 */

public enum QueryType {
        // interactive complex queries
        InteractiveComplexQuery1 ("interactive-complex-1" ),
        InteractiveComplexQuery2 ("interactive-complex-2" ),
        InteractiveComplexQuery3 ("interactive-complex-3" ),
        InteractiveComplexQuery4 ("interactive-complex-4" ),
        InteractiveComplexQuery5 ("interactive-complex-5" ),
        InteractiveComplexQuery6 ("interactive-complex-6" ),
        InteractiveComplexQuery7 ("interactive-complex-7" ),
        InteractiveComplexQuery8 ("interactive-complex-8" ),
        InteractiveComplexQuery9 ("interactive-complex-9" ),
        InteractiveComplexQuery10("interactive-complex-10"),
        InteractiveComplexQuery11("interactive-complex-11"),
        InteractiveComplexQuery12("interactive-complex-12"),
        InteractiveComplexQuery13("interactive-complex-13"),
        InteractiveComplexQuery14("interactive-complex-14"),
        InteractiveComplexQuery3DurationAsFunction ("interactive-complex-3-duration-as-function" ),
        InteractiveComplexQuery4DurationAsFunction ("interactive-complex-4-duration-as-function" ),
        InteractiveComplexQuery7WithSecond("interactive-complex-7-with-second"),

        // interactive short queries
        InteractiveShortQuery1("interactive-short-1"),
        InteractiveShortQuery2("interactive-short-2"),
        InteractiveShortQuery3("interactive-short-3"),
        InteractiveShortQuery4("interactive-short-4"),
        InteractiveShortQuery5("interactive-short-5"),
        InteractiveShortQuery6("interactive-short-6"),
        InteractiveShortQuery7("interactive-short-7"),

        // interactive updates (single queries)
        InteractiveUpdate1("interactive-update-1"),
        InteractiveUpdate2("interactive-update-2"),
        InteractiveUpdate3("interactive-update-3"),
        InteractiveUpdate4("interactive-update-4"),
        InteractiveUpdate5("interactive-update-5"),
        InteractiveUpdate6("interactive-update-6"),
        InteractiveUpdate7("interactive-update-7"),
        InteractiveUpdate8("interactive-update-8"),

        // interactive updates (additional queries for systems that perform them as multiple queries)
        InteractiveUpdate1AddPerson            ("interactive-update-1-add-person"),
        InteractiveUpdate1AddPersonCompanies   ("interactive-update-1-add-person-companies"),
        InteractiveUpdate1AddPersonEmails      ("interactive-update-1-add-person-emails"),
        InteractiveUpdate1AddPersonLanguages   ("interactive-update-1-add-person-languages"),
        InteractiveUpdate1AddPersonTags        ("interactive-update-1-add-person-tags"),
        InteractiveUpdate1AddPersonUniversities("interactive-update-1-add-person-universities"),

        InteractiveUpdate4AddForum             ("interactive-update-4-add-forum"),
        InteractiveUpdate4AddForumTags         ("interactive-update-4-add-forum-tags"),

        InteractiveUpdate6AddPost              ("interactive-update-6-add-post"),
        InteractiveUpdate6AddPostTags          ("interactive-update-6-add-post-tags"),

        InteractiveUpdate7AddComment           ("interactive-update-7-add-comment"),
        InteractiveUpdate7AddCommentTags       ("interactive-update-7-add-comment-tags"),

        // interactive updates (additional queries for system that insert content/imageFile using separate operations)
        InteractiveUpdate6AddPostContent       ("interactive-update-6-add-post-content"),
        InteractiveUpdate6AddPostImageFile     ("interactive-update-6-add-post-imagefile"),

        // Deletes
        InteractiveDelete1("interactive-delete-1"),
        InteractiveDelete2("interactive-delete-2"),
        InteractiveDelete3("interactive-delete-3"),
        InteractiveDelete4("interactive-delete-4"),
        InteractiveDelete5("interactive-delete-5"),
        InteractiveDelete6("interactive-delete-6"),
        InteractiveDelete7("interactive-delete-7"),
        InteractiveDelete8("interactive-delete-8"),
       ;

       private String name;

       QueryType(String name) {
           this.name = name;
       }

       public String getName() {
           return name;
       }
}
