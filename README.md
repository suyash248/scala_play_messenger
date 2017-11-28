## Scala/Playframework messenger

### Prerequistes
- Mongo running on http://127.0.0.1:27017

### Endpoints

| Method | URL | Body | Description |
| ------ | --- | ---- | ----------- |
| GET | {host}/users/:userName/followers?pageNumber=1&perPage=20 | NA | Fetches user's followers |
| PUT | {host}/users/:user/followers/:follower | NA | For a ```user``` to follow another user (```follower```) |
| POST | {host}/users | <pre/>{ <br/> "userName": "sachin248",<br/> "firstName": "Sachin",<br/> "lastName": "Tendulkar" <br/>}  | Creates a user if not exists. |


### How to run

Visit ```{host}```
> {host} for local machine will be ```http://127.0.0.1:9000```
