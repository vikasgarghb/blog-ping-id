# Blog Ping Id

This project provides a sample to integrate PingAccess and PingFederate to be used as auth resource server and provider
with your services. This is using Java Spring Boot services but can be used with services written in any other language
like nodeJS.

## PingAccess and PingFederate

### Starting the containers

PingAccess and PingFederate both are provided via docker containers. In order to start them, run `docker-compose up -d`.
You will need to create a devops account on pingId to get the credentials. Once you have the credentials, you can either
use `ping-devops` utility to provide them OR pass them as `.env` file to `docker-compose up -d` command.

If using `ping-devops` utility, then spin up the containers
via, `docker-compose --env-file ${HOME}/.pingidentity/devops up -d`.

If using environment variables, then create a `.env.local` file and add the following content,

```
PING_IDENTITY_DEVOPS_USER=<user name>
PING_IDENTITY_DEVOPS_KEY=<user key>
```

and then run the command, `docker-compose --env-file .env.local up -d`

After the containers are started you can use the following credentials to login to the admin console. For pingaccess,
use `https://localhost:9000`. For pingfederate, use `https://localhost:9031`. The user credentials
are: `administrator/2FederateM0re`.

### Setup

So, both PingAccess and PingFederate comes pre-configured with some things. For our setup we will make some changes to
that and also add some of our own.

#### PingFederate

*Signing Certificate* -- First thing you will need is a signing certificate for your access token.

1. Go to Security > Signing & Decryption Keys & Certificates
2. Create New
3. Name: *Todo Cert*
4. Subject Alternate Names: *DNS Name -- host.docker.internal*
5. Organization: any name
6. Country: *US*
7. Save
8. Create New
9. Name: *Tweet Cert*
10. Subject Alternate Names: *DNS Name -- host.docker.internal*
11. Organization: any name
12. Country: *US*
13. Save

*Access Token Manager* -- In order to generate token, you will need to define a access token manager.

1. Go to Applications > Oauth > Access Token Management
2. Create New Instance
3. Instance Name: *Todo Token Management*
4. Instance Id: *todotokenmgmt*
5. Next
6. Under Instance Configuration
7. Under Certificates, add a Key Id: todokey and Certificate as Todo Cert. Update.
8. JWS Algorithm: *RSA Using SHA-256*
9. Active Signing Certificate Key Id: *todokey*
10. Show Advanced Fields
11. Issuer Claim Value: *https://pingfederate:9031*
12. Audience Claim Value: *<todo-service-host>:8082*
13. JWKS Endpoint Path: /todoauthtoken/jwks -- This needs to be unique for each token manager.
14. Under Access Token Attribute Contract, add a new attribute to extend the USER_KEY subject.
15. Under Resource URIs, add a base resource uri, *https://<virtual-host-on-ping-access>:3000*
16. Save
17. Repeat steps 2-15 for Tweet Service.

*Access Token Mapping* -- This is needed to fulfill the access token attribute contracts.

1. Go to Applications > Oauth > Access Token Mappings
2. From Context dropdown, select Client Credentials and Access Token Manager as one of the token managers created above.
3. Under Contract Fulfillment, select Context and ClientId in the respective dropdowns.

*Clients* -- Now comes the client creation to be used by the services to get the access tokens.

1. Go to Applications > Oauth > Clients
2. Add Client
3. Client Id: todo_client
4. Name: Todo Client
5. Client Authentication: *Client Secret*
6. Client Secret: Either use Generate Secret button OR enter one.
7. Request Object Signing Algorithm: *RSA Using SHA-256*
8. JWKS Url: *https://localhost:9031/ext/authtoken/jwks* -- This is the same url which you set in #12 under *Access
   Token Management* above.
9. Allowed Grant Types: *Client Credentials*
    1. You can also select *Access Token Validation (Client is a resource server)* if you will be using this client id
       for pingaccess to access pingfederate.
10. Default Access Token Manager: *Todo Token Mgmt*
11. Save.
12. Repeat the same for another client for tweet. #9.1 is only needed for the client to be used by pingaccess.

#### Pingaccess

*Token Provider* -- This is already set by default.

1. Go to Settings > System > Token Provider.
2. Only change you will need to do is select *Send Audience* under *Oauth Resource Server*
3. If you want to use a different client id then update the id and secret.

*Virtual Host* -- This is needed to access the application via pingaccess.

1. Go to Applications > Applications > Virtual Hosts
2. Add Virtual Host
3. Host: Use the same one as used on pingfederate's *Access Token Manager*'s Resource URI tab.
4. Port: 3000 -- This is the default pingaccess application port. It can be configured on startup to a different one.

*Sites* -- Add your API information.

1. Go to Applications > Sites
2. Add Site
3. Name: *Todo API*
4. Targets: *host.docker.internal:8082*
5. Secure: *Yes*
6. Save
7. Repeat the same for Tweet API.

*Application* -- Add your Application information.

1. Go to Applications > Applications
2. Add Application
3. Name: *Todo App*
4. Context Root: */todos*
5. Virtual Hosts: Use the one created under *Virtual Host* step.
6. Application Type: *API*
7. SPA Support: *Checked*
8. Access Validation: *Token Provider*
9. Destination: *Site*
10. Site: Select the site created under *Sites* step.
11. Require Https: *Checked*
12. Save
13. Repeat the same for Tweet App.

#### Certificate

Since both PA and PF are running inside the docker container over https, for the spring boot application to access these
we need to add the certificates to the java trust store. In order to do so, get the certificate by running the following
command,

```
// For PingAccess
openssl s_client -showcerts -connect localhost:3000
```

Copy the content between `-----BEGIN CERTIFICATE-----` and `-----END CERTIFICATE-----` inclusive in a file and then
import that cert file to java trust store by running the command,

```
sudo keytool -import -alias pingaccess-docker -keystore /path/to/java/security/cacerts -file /path/to/cert/file/created/above
```

Do the same for PingFederate certificate. To get the certificate,

```
// For PingFederate
openssl s_client -showcerts -connect localhost:9031
```

#### Host File

Open your hosts file (/etc/hosts) and add the following,

```
127.0.0.1 pingfederate <...virtual hosts defined on pingaccess>
```

#### Logs

The logs for PA and PF both are available under the same location `/opt/out/instance/log` in the respective containers.

## Local Certificate

In order to run the services over https, you will need to install certificate. For that you can make use of `mkcert`.

### Installing `mkcert`

```
brew install mkcert
```

### Creating certificate using `mkcert`

```
mkcert -install

mkcert -pkcs12 localhost 127.0.0.1 ::1 host.docker.internal
```

This will create and install the certificate in the trusted keystore.

### Using the certificate

Now create a directory in the root folder, `certs` and copy the generated certificate file to this location. Now when
you start any of the services in this project they will be able to run on `https`.

## Project Setup

This project contains 3 spring-boot services.

- *bff-service* -- This is an aggregator service like a client accessing the other services over pingaccess.
- *todo-service* -- Serivce providing APIs for Todo.
- *tweet-service* -- Serivce providing APIs for Tweet.

### BFF Service Config

The config can be found under `bff-service/src/main/resources/application.yml`. The `auth` segment contains the token
endpoint for pingfederate. The `audience`, `clientId` and `clientSecret` are provided under individual service config
segments like `todo` and `tweet`.

### Start the services

Either you can IDE to start all the services OR can start them individually from command line using the
command `gradle bootRun`.

### Usage

Once the services have started, you can access it on, `https://localhost:8084/bff`. When accessing for the first time,
you will see a response like this,

```json
{
  "todos": [],
  "tweets": []
}
```

To add a new todo, make the request to `https://localhost:8084/bff/todo` and in the body provide a json string. Similar
API `/bff/tweet` to add a new tweet.
