ContentProviderClientTemplate
=============================

Modeled after Spring JdbcTemplate, ContentProviderClientTemplate aims to streamline the use of Android ContentProviders.  Like JdbcTemplate encapsulates the details of querying, ContentProviderClientTemplate encapsulates the details of not only querying but also inserting and updating, greatly reducing the amount of boilerplate code needed in a project.

The design of ContentProviderClientTemplate also encourages developers to separate object-building and ContentValues-building logic from other responsibilities that sometimes get intertwined in data access code.  Developers are encouraged to write self-contained RowMapper/RowCallbackHandler implemenations for querying, and ContentValuesMapper implementations for inserting/updating.  Such mappers and handlers serve a single purpose and can potentially be reused throughout a project (or projects.)

For convenience, ContentProviderClientTemplate provides a number of simple RowMapper implementations that address a common use case - querying for the value of a single column.

A ContentValuesMapper implementation also exists that allows ContentValues to be build from business objects by simply annotating the appropriate fields.
