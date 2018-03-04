[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1f994734288748f18a7f9d8645ba3711)](https://www.codacy.com/app/prperiscal/general-purpose-oauth-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=prperiscal/general-purpose-oauth-service&amp;utm_campaign=Badge_Grade)

# general-purpose-environment

General purpose micro-services and architecture provide an extended and robust base from where a new project
can be started and developed following a consolidated agile approach and with the most used and assumed technologies in the 
micro-service spring development ecosystem.

Take a look on [General Purpose Environment](https://gist.github.com/prperiscal/900729941edc5d5ddaaf9e21e5055a62) to get more information on how everything integrates together and how the agile and continuous integration development should work.

# general-purpose-oauth-service

Authorization Server responsible for verifying credentials and providing access tokens.

Communicates directly with User service and with tenant/realm service in order to retrieve information to ensure and build the users credentials.

![OAuth_Diagram](https://image.ibb.co/ifdDjb/oauth.png)

## Contributing

Please read [CONTRIBUTING](https://gist.github.com/prperiscal/900729941edc5d5ddaaf9e21e5055a62) for details on our code of conduct, and the process for submitting pull requests to us.

## Workflow

Please read [WORKFLOW-BRANCHING](https://gist.github.com/prperiscal/ce8b8b5a9e0f79378475243e2d227011) for details on our workflow and branching directives. 

## Technologies involved

* [Spring 5.0.1.RELEASE](https://spring.io/) / [Spring boot 2.0.0.M3](https://projects.spring.io/spring-boot/)
* [Spring Security OAuth](http://projects.spring.io/spring-security-oauth/). Base for our Authorization Server
* [Spring Cloud](https://cloud.spring.io/spring-cloud-netflix/)
  * [__Feign__](https://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#spring-cloud-feign) to communicate with user and tenant services internally
  * [__Hystrix__](https://github.com/Netflix/Hystrix/blob/master/README.md) Latency and fault tolerance support on points of access
  * [__Eureka Client__](https://github.com/Netflix/eureka/blob/master/README.md) For announcing the service in the eureka server and make it visible
  * [__Ribbon__](https://github.com/Netflix/ribbon/blob/master/README.md) Load balancer

## Getting Started

Get you a copy of the project up and running on your local machine for development and testing purposes with:
```
git clone https://github.com/prperiscal/general-purpose-oauth-service
```
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

This service communicates with User and Tenant services to retrieve information while authorizing an user.
Eureka server must be running too.

## Internals

Here is explained what is the Authorization server and how is it configured.

### OAuth 2.0

OAuth2 is the standardized authorization protocol/framework that we are using in our project. It is defined in the [RFC 6749](https://tools.ietf.org/html/rfc6749).

[Spring Security OAuth project](http://projects.spring.io/spring-security-oauth/) provides all the necessary API we might need in order to develop an OAuth2 compliant implementation using Spring.

For a basic knowledge on OAuth2 fundamentals check this excellent [article](http://www.bubblecode.net/en/2016/01/22/understanding-oauth2/)

### OAuth Roles

We are working with the four Roles specified in OAuth2 definition as follow:
 * __Resource owner__ (the user):  The person who is giving access to some api
 * __Resource server__: Is the server that contains the resources or information that is being accessed by the third-party application. The resource server must be able to accept and validate access tokens and grant the request if the user has allowed it.
 * __Authorization server__: This service. Is what the user interacts with when an application is requesting access to their account
 * __Client__: Applications from where the user access our system.
    * __Confidential Clients__: Our web application which code is not accessible to users. So we have the ability to maintain the confidentiality of the client_secret
    * __Public Clients__: Our mobile applications. Because they cannot maintain the confidentiality of a client_secret.

### OAuth Grants

An authorization grant is a credential representing the resource owner’s authorization used by the client to obtain an access token. There are different types of authentications, for example, if we are an user and we want to login using the web page, we need a token which can identify the user (we), the place from were we are login (we could be using a mobile app instead of the web), and also we want a refresh token to not be asked for a re-logging. On the other hand if we are now a service and we want to communicate with another one, we don't have (and don't want) a user credentials neither the need for a refresh token.

That's the use for the grants. They are set to a client to identify what kind of authentication is allowed to perform

Our Authentication server is using the following grants:

* __client_credentials__: Allows to retrieve a token without user credentials. Used in Resource services to authenticate themselves when calling another service.
* __password__: Used by the Clients (web and mobiles) allows the retrieving of a user authentication
* __refresh_token__: If present the token will include a refresh token. Find more about this in 'Revoking Tokens' section

You can find how to configure this in the 'Clients registration' section

### JWT

JWT is used to extends the token inside-information. With a jwt token any Resource server can authenticate the user knowing his/her roles and scopes without contacting the authentication server internally. 

The authentication/authorization flow becomes simple:
![JWT flow](https://cdn-images-1.medium.com/max/1600/1*SSXUQJ1dWjiUrDoKaaiGLA.png)

The JWT configuration is set inside com/general/purpose/oauth/config/JwtConfig.java class. You can find there more information regarding how configure it.

### Clients registration

Each token retrieving request must provide the client credentials, even if there is not user and we are trying to retrieve a token for an internal service.
This credentials are placed inside com/general/purpose/oauth/config/Oauth2Config.java class. 

### User authentication provider

When a token is requested for a password grant, the user credentials must be checked.
For that propose the AuthenticationProvider class is used. The process will communicate with the User service to retrieve the user information and checks that everything is fine (meaning password validation) and the tenant service to get the realm information.

### Endpoints and their purpose

[Waiting for doc-apis].

### Revoking Tokens
Following directions for Self-Encoded tokens from [oauth.com](https://www.oauth.com/oauth2-servers/listing-authorizations/revoking-access/):

As we have a truly stateless mechanism of verifying tokens, and our resource server is validating tokens without sharing information with another system (because of jwt use), then the only option is to wait for all outstanding tokens to expire, and prevent the application from being able to generate new tokens for that user. This is the primary reason to use extremely short-lived tokens.

Since there is no mechanism to invalidate individual access tokens, instead we will need to invalidate the application’s refresh tokens for the particular user. This way the next time the application attempts to refresh the access token, the request for a new access token will be denied.

## Authors

* **Pablo Rey Periscal** - *Initial work* -

See also the list of [contributors]() who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
