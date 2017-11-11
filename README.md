## Scala/Playframework messenger

### Prerequistes
- Mongo running on http://127.0.0.1:27017

### Endpoints

| Method | URL | Body | Description |
| ------ | --- | ---- | ----------- |
| GET | {host}/users/:userName/followers?pageNumber=1&perPage=20 | NA | Fetches user's followers |
| PUT | {host}/users/:user/followers/:follower | NA | For a ```user``` to follow another user (```follower```) |
| POST | {host}/users | ```{ "userName": "sachin248", "firstName": "Sachin", "lastName": "Tendulkar" } ``` | Creates a user if not exists. |


### How to run

Visit ```{host}```
> {host} for local machine will be ```http://127.0.0.1:9000```
