# legal-entities

Spring Boot based restful API application offering CRUD features.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

```shell
cd into project directory and run below command: 

mvn spring-boot:run
```

## REST API

### POST (Create Entity) Request Url: http://localhost:8080/legal-entities
### POST Request Body:

```shell
{
	"name":"{{name}}",
	"incorporationDate":"{{yyyy-mm-dd}}",
	"countryOfIncorporation": "{{CH|UK|US}}",
	"totalNumberOfShares":{{number}},
	"shareHolders" : [
			{
				"numberOfShares":{{number}},
				"name":"{{name}}"
			}
		]
}

``` 

*Error Scenarios:*

Return 400 BadRequest if Country not either CH, UK, US.

### GET http://localhost:8080/legal-entities 

### PUT (Update Entity) Request Url: http://localhost:8080/legal-entities/{{id}}
### PUT Request Body:

```shell
{
        "id":"{{number}}",
	"name":"{{name}}",
	"incorporationDate":"{{yyyy-mm-dd}}",
	"countryOfIncorporation": "{{CH|UK|US}}",
	"totalNumberOfShares":{{number}},
	"shareHolders" : [
			{
				"numberOfShares":{{number}},
				"name":"{{name}}"
			}
		]
}

``` 

*Error Scenarios:*

Returns 404 with appropriate message if entity is not found for the given `id`! 

Return 400 BadRequest if Country not either CH, UK, US.

Returns 400 BadRequest when `id` in request body is not same as the one in url.


Note: In this Put request all fields are optional except `id`. For example you can just send shareHolders to add to the existing list for the entity with given id or just send `totalNumberOfShares` to be edited. All are taken care of!


### DELETE http://localhost:8080/legal-entities/{{id}}

*Error Scenarios:*

Returns `204 No Content` upon successful entity deletion. You can cross check by calling GET.

Returns 404 with appropriate message if entity is not found for the given `id`!


## Project Code:

### Source

Enum, Service, Resource, Exceptions and Model packages.

### Tests

Enum, Service.
