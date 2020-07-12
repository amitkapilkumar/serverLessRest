ServerLessRest Expose below set of API:
/user/profiles - List all the profiles details
/user/profile/123 - List profile with id 123
/user/profile/546 - List profile with id 546
/user/profile/894 - List profile with id 894
/rates/latest - Fetch latest rates from api-exchange

Server is started on port 8002

API's can be invoked via curl or web browser by hitting URL's:

1) curl localhost:8002/user/profiles
2) curl localhost:8002/user/profile/123
3) curl localhost:8002/user/profile/546
4) curl localhost:8002/user/profile/894
5) curl localhost:8002/rates/latest

The jar file can be executed as : java -jar ServerLessRest.jar

SRC and Test folder is attached in the zip

Application don't have capability to add profile, by default 3 profiles will be loaded on server start.
