ContentProviderTemplate
=======================

Modeled after Spring JdbcTemplate, ContentProviderTemplate aims to streamline the process of querying Android ContentProviders.  Like JdbcTemplate, ContentProviderTemplate encapsulates the details of querying, greatly reduces the amount of boilerplate code needed in a project.

The design of ContentProviderTemplate also encourages developers to separate object-builing logic from other responsibilities that sometimes get intertwined in data access code.  Developers are encouraged to write self-contained, reusable RowMapper and RowCallbackHandler implemenations that serve a single purpose and can be reused throughout their project(s).

ContentProviderTemplate also provides a number of simple RowMapper implementations that addresses a common use case - querying for the value of a single column.
