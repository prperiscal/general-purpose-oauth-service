# mytasks-oauth-service

Authorization Server responsible for verifying credentials and providing access tokens

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/prperiscal/900729941edc5d5ddaaf9e21e5055a62) for details on our code of conduct, and the process for submitting pull requests to us.

## Workflow

You can found our workflow explined [here](https://gist.github.com/prperiscal/ce8b8b5a9e0f79378475243e2d227011). 

## Technologies involved

* [Spring 5.0.1.RELEASE](https://spring.io/) / [Spring boot 2.0.0.M3](https://projects.spring.io/spring-boot/)
* [Spring Security OAuth](http://projects.spring.io/spring-security-oauth/). Base for our Authorization Server
* [Spring Cloud](https://cloud.spring.io/spring-cloud-netflix/)
  * __Feign__ to comunicate with user and tenant services internaly
  * [__Hystrix__](https://github.com/Netflix/Hystrix/blob/master/README.md) Latency and fault tolerance support on points of access
  * [__Eureka Client__](https://github.com/Netflix/eureka/blob/master/README.md) For announcing the services in the eureka server and make it visible
  * [__Ribbon__](https://github.com/Netflix/ribbon/blob/master/README.md) Load balancer

## Getting Started

Get you a copy of the project up and running on your local machine for development and testing purposes with:
```
git clone https://github.com/prperiscal/mytasks-oauth-service
```
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

This service, as explained under, comunicates with User and Tenant service, so for proper working these other services will be needed to be up and running

## Internals

Here is explained what is the Authotization server and how is it configured.

### OAuth 2.0

OAuth2 is the standardized authorization protocol/framework that we are using in our project. It is defined in the [RFC 6749](https://tools.ietf.org/html/rfc6749).

[Spring Security OAuth project](http://projects.spring.io/spring-security-oauth/) provides all the necessary API we might need in order to develop an OAuth2 compliant implementation using Spring.

For a basic knowledge on OAuth2 fundamentals check this excellent [article](http://www.bubblecode.net/en/2016/01/22/understanding-oauth2/)

### OAuth Roles

### OAuth Grants

### JWT

### Clients registration

### User authetincation provider

### Endpoints and their purpose

## Authors

* **Pablo Rey Periscal** - *Initial work* -

See also the list of [contributors]() who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
