ContentProviderTemplate
=======================

Modeled after [Spring](http://spring.io/) JdbcTemplate, ContentProviderTemplate aims to streamline the use of Android [ContentProviders](http://developer.android.com/guide/topics/providers/content-providers.html).  Like JdbcTemplate encapsulates the details of querying, ContentProviderTemplate encapsulates the details of not only querying but also inserting and updating, greatly reducing the amount of boilerplate code needed in a project.

The design of ContentProviderTemplate also encourages developers to separate object-building and ContentValues-building logic from other responsibilities that sometimes get intertwined with other data access code.  Developers are encouraged to write self-contained RowMapper/RowCallbackHandler implemenations for querying, and ContentValuesMapper implementations for inserting/updating.  Such mappers and handlers serve a single purpose and can potentially be reused throughout a project (or projects.)

For convenience, the library provides simple RowMapper implementations for use when querying the value of a single column.  There are even annotation-driven mapper implementations (AnnotationRowMapper and AnnotationContentValuesMapper) that allow you to map to and from your model classes by simply annotating fields that you want the mapper to handle.

Note on Build Dependencies
--------------------------

As you might expect, the build for ContentProviderTemplate has dependencies on Android artifacts, including a transitive dependency on com.google.android:support-v4, which is quite out of date on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.google.android%22).  In order to build the project locally, it's recommended that you use the Android SDK Manager to install the "Android Support Repository" package and update your system's ~/.m2/settings.xml file to include the local repository.  For example:

    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

        <profiles>
            <profile>
                <id>android</id>
                <repositories>
                    <repository>
                        <id>android-support-repo</id>
                        <name>Adnroid Support Repository</name>
                        <url>file:///path-to-android-sdk/extras/android/m2repository</url>
                    </repository>
                </repositories>
            </profile>
        </profiles>

        <activeProfiles>
            <activeProfile activeByDefault="true">android</activeProfile>
        </activeProfiles>
    </settings>
