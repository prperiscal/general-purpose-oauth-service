# mytasks-oauth-service

Authorization Server responsible for verifying credentials and providing access tokens

## Contributing

Please read [CONTRIBUTING](https://gist.github.com/prperiscal/900729941edc5d5ddaaf9e21e5055a62) for details on our code of conduct, and the process for submitting pull requests to us.

## Workflow

Please read [WORKFLOW-BRANCING](https://gist.github.com/prperiscal/ce8b8b5a9e0f79378475243e2d227011) for details on our workflow and branching directives. 

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
git clone https://github.com/prperiscal/mytasks-oauth-service
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

An authorization grant is a credential representing the resource owner’s authorization used by the client to obtain an access token. There are different types of authentications, for example, if we are an user and we want to login using the web page, we need a token which can identify the user (we), the place from were we are login (we could be using a mobile app instead of the web), and also we want a refesh token to not be asked for a re-loging. On the other hand if we are now a service and we want to communicate with another one, we don't have (and don't want) a user credentials neither the need for a refresh token.

That's the use for the grants. They are set to a client to identify what kind of authentication is allowed to perform

Our Authentication server is using the following grants:

* __client_credentials__: Allows to retreive a token without user crendentials. Used in Resource services to authenticate themselfs when calling another service.
* __password__: Used by the Clients (web and mobiles) allows the retrieving of a user authentication
* __refresh_token__: If present the token will include a refresh token. Find more about this in 'Revoking Tokens' section

You can find how to configure this in the 'Clients registration' section

### JWT

### Clients registration

### User authetincation provider

### Endpoints and their purpose

### Revoking Tokens

## Authors

* **Pablo Rey Periscal** - *Initial work* -

See also the list of [contributors]() who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
