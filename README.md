
#Plugin Temporary-code

##Introduction
This plugin allows the generation of temporary code.


##Configuration

##### Admin feature:

From the admin feature, you can configure the length, the validity period and the character type of the temporary code.

##### Service:

With the service <strong>TemporaryCodeService</strong> you can generate, verify and use the temporary code.


##### Interface & XPage:

Implement the interface <strong>ITemporaryCodeValidator</strong> for access to the validation view.

Inject the new validator into the context.

```
<bean id="temporarycode.TestTemporaryCodeValidator" class="fr.paris.lutece.plugins.temporarycode.web.TestTemporaryCodeValidator" />
```

View URL (Need to change {id_validator})

```
jsp/site/Portal.jsp?page=temporaryCodeValidator&view=viewTemporaryCode&validator_id={id_validator}
```

## Usage