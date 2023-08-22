
#Plugin Temporary-code

##Introduction
This plugin allows the generation of temporary code.


##Configuration

##### Admin feature:

From the admin feature, you can configure the length, the validity period and the character type of the temporary code.

##### Service:

With the service <strong>TemporaryCodeService</strong> you can generate, verify and use the temporary code.


## REST API:

##### Generate temporary code

```
GET /rest/api/temporarycode/generate

Headers
configuration_id string (default if null)
user_id string (required)
action_name string (required)
```



##### Verify temporary code

```
GET /rest/api/temporarycode/verify

Headers
temporary_code string (required)
user_id string (required)
action_name string (required)
```

##### Consume temporary code

```
GET /rest/api/temporarycode/consume

Headers
temporary_code string (required)
user_id string (required)
action_name string (required)
```

## Usage