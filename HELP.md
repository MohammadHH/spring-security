# Spring Security

## Authentication Configuration

To configure spring security, Authentication Manager is what does the authentication, this class has authenticate()
method which either returns a successful authentication or throws an error. You configure such a manager with
authentication manager builder class.You can get the builder by extending WebSecurityConfigurerAdapter and override
configure(AuthManagerBuilder auth)

## Authorization Configuration

With HttpSecurity you define restrictions on a path. You can get this object by extending WebSecurityConfigurerAdapter
and override configure(HttpSecurity security). Go from most restrictive to least (eg. specify ant matchers on admins
urls first then on normal users)

## How Security Authentication Works

Spring add security to your app by using Filters. A filter is a construct that intercepts your http requests before it
goes to a servlet. AuthenticationProvider is the one that do the actual authentication, Authentication will hold the
principal eventually.

The filter takes credentials as input then delegate that into authentication manager which chooses between different
providers (eg. LDAP, Basic authentication) the provider authenticate the user by getting back its details (eg. from a
DB) then if the user is successfully authenticated, its principals gets returned to the filter, otherwise an excepetion
occurs and the filter should respond to that exception.

![Spring Security](https://i.ibb.co/fvqRqpj/Screenshot-from-2021-02-06-17-19-10.png)

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-security)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

