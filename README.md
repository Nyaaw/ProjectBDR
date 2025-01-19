# Project BDR/DAI 

## Autors <!-- omit in toc -->
- Alex Berberat
- Lisa Gorgerat
- Pierric Ripoll


## Table of Contents <!-- omit in toc -->
- [README Criterion (to remove later)](#readme-criterion-to-remove-later)
- [Introduction](#introduction)
- [Getting Started](#getting-started)
  - [General Prerequisites](#general-prerequisites)
  - [Recommendation](#recommendation)
  - [As a User](#as-a-user)
  - [As a Developer](#as-a-developer)
    - [Additional Prerequisites](#additional-prerequisites)
    - [Setup Using GitHub](#setup-using-github)
      - [Clone the repository](#clone-the-repository)
      - [Build the application source code](#build-the-application-source-code)
      - [Build the application package](#build-the-application-package)
- [Deployment](#deployment)
  - [Prerequisites](#prerequisites)
    - [Online Use](#online-use)
    - [Local Use](#local-use)
      - [With Docker](#with-docker)
      - [Without Docker](#without-docker)
  - [Steps to deploy](#steps-to-deploy)
    - [With Docker](#with-docker-1)
    - [Without Docker](#without-docker-1)
- [Demonstration](#demonstration)
  - [Project](#project)
  - [API](#api)
- [Complementary Information](#complementary-information)
  - [API](#api-1)
  - [DNS Configuration](#dns-configuration)





## README Criterion (to remove later)
- Category 1 - Git, GitHub and Markdown
  - [ ] The README is well structured and explains the purpose of your web with the authors' names so new users can understand it and know who is behind the application
- Category 2 - Java, IntelliJ IDEA and Maven
  - [x] The README describes explicit commands to clone and build your network application with Git and Maven so new developers can start and develop your project on their own computer
- Category 3 - Docker and Docker Compose
  - [x] The README describes explicit commands to build and publish your network application with Docker
  - [ ] The README describes explicit commands to use your network application with Docker Compose so other people can easily use it
- Category 6 - HTTP and curl
  - [ ] The README (or repository) contains the application protocol interface (API) that describes the web application
  - [ ] The README explains how to use your web application with explicit examples using curl with outputs to demonstrate how to interact with your web application deployed on the Internet
- Category 7 - Web infrastructures
  - [x] The README (or repository) contains instructions how to install and configure the virtual machine with each step
  - [x] The README (or repository) contains explains how to configure the DNS zone to access your web application
  - [ ] The README (or repository) contains instructions how to deploy, run and access the web applications with Docker Compose
  - [x] The README displays the domain names configuration in the DNS zone to validate everything is set up right

- [ ] And More
- [ ] Complet all the `TOADD`
- [ ] Verify all the `MAYBE`










## Introduction
This web application allows users to manage medias.
Users can put medias in customised lists or basic lists such as the finished media list. They can also add notes or comments to the media they interact with.
Finally, users can add medias themselves, if they are not in the database.

The types of medias that can be managed are: 
- Books
- Comics
- Movies
- Series
- Video Games


## Getting Started 
### General Prerequisites
- Docker
- Docker Compose

To use the docker image, you first need to ensure that docker is installed on your machine.
If that's not the case, please go to the official website ([Dockerdocs](https://docs.docker.com/engine/)) and follow the instruction for the version you need.


### Recommendation
Use Docker and Docker compose instead of running the application natively it's way more easy.



### As a User
To begin you'll need our app package that you can find [here](TOADD).  

You can also use this command in your terminal:  
```sh
docker pull ghcr.io/<TOADD>
```

Then you can skip to this [section](#deployment).



### As a Developer
#### Additional Prerequisites
- JDK
- Maven (optional, a maven wrapper comes with the project)


#### Setup Using GitHub 
##### Clone the repository
1. Go to the [repository](https://github.com/Nyaaw/ProjectBDR) on GitHub and choose your favorite clone option.
2. Open the terminal in the folder where you want to clone the repository.
3. Clone the repo.
    ```sh 
    git clone https://github.com/Nyaaw/ProjectBDR.git
    ```
4. Change git remote url to avoid accidental pushes to base project.
    ```sh
    git remote set-url origin <github_username/repo_name>
    git remote -v
    ```

##### Build the application source code
To build the application, you can use the following commands in your terminal.
```sh
# Download the dependencies and their transitive dependencies
./mvnw dependency:go-offline

# Package the application
./mvnw clean package
```

##### Build the application package
If you were to change our application, you will need to build it again and publish it on your account.

To build the app:

Go into the folder of the app and use the command:
```sh
docker build -t webserver .
```

To publish the container on GitHub:

1. You first need to connect to your account:
    ```sh
    docker login ghcr.io -u <username>
    ```

2. You need to tag the image:
    ```sh
    # Tag the image with the correct format
    docker tag <TOADD> ghcr.io/<username>/<TOADD>:latest
    ```

3. You publish the container on your account:
    ```sh
    docker push ghcr.io/<username>/<TOADD>
    ```



## Deployment
### Prerequisites
You can either choose to deploy localy or online.



#### Online Use
- Cloud Based Virtual Machine
  - Docker
  - Docker Compose
- Domain Name


> [!NOTE]
> You can also use a server instead of a Cloud Based VM if you have one available, the application set up should be pretty similar but as we won't explain this scenario you might need to do some research to succed.  

If you don't know how to set up a VM you can read [this document](docs/VM_Help.md) to get the basics.  
If you don't have a domain name and don't know how to get one you can read [this document](docs/Domain_Name_Help.md) to get the basics.  



#### Local Use
##### With Docker
- Docker
- Docker Compose


##### Without Docker
- Java

> [!NOTE]
> To run the project locally, you need to have a Postgre Database with the following settings (if you modify any of the following, do not forget to modify the file `src/main/java/ch/heigvd/dai/Main.java`).

- Database name : mediatheque

We have sql scripts for creating the content of the database and to give it base data, if you want to fill it. The scripts are in `docs/BDR-phases` in phase 3 and 4. The order to run them is `DDL.sql`, `DML.sql` and `base_struct_and_data.sql`.



### Steps to deploy
#### With Docker

> [!IMPORTANT]
> If you want too run the application online you will need to change the name of the domain in ``docker-compose.yaml`` to your domain that is linked to your vm.
> - Line 57 : ``- "traefik.http.routers.webserver.rule=Host(`<your domain name>`)"``
> - Line 61 : ``- "traefik.http.routers.webserver-secure.rule=Host(`<your domain name>`)"``


To run the project with docker, after packaging and building the project with Docker, use the following command:
````shell
docker compose up
````

If you run it localy you can access by going to the address ``localhost:8080`` in your browser.  
And if you run it online you juste need to go to your domain name in your browser.



#### Without Docker
To run the project without docker, after packaging, run this command:
```sh
java -jar target/webserver.jar
```

To see the result, go to ``localhost:8080`` in your browser.






## Demonstration
### Project 

TOADD


### API
Demonstration made using curl to simulate request.

TOADD








## Complementary Information
### API
If you need more informations about the API you can consult [this file](docs/API.md).


### DNS Configuration
We used ``Duck DNS`` to get our domain. ``Duck DNS`` give automatically an `A` record.
We made our domain point to our VM.
````shell 
lg-heig-vd.duckdns.org points to 4.180.67.252
```` 
Now we can access our website using: ``http://lg-heig-vd.duckdns.org``.

To see the ``TTL`` of our domain, we used this command:
``dig +nocmd +noquestion +nocomments +stats``

The result was: ``lg-heig-vd.duckdns.org. 60      IN      A       4.180.67.252``

To see all the records and information, the ``nslookup`` command is very useful.
Exemples:
- The ``A`` record: ```nslookup -type=a lg-heig-vd.duckdns.org```
  - Answer:
  ```shell
  Server:         127.0.0.53
  Address:        127.0.0.53#53
  
  Non-authoritative answer:
  Name:   lg-heig-vd.duckdns.org
  Address: 4.180.67.252
   ``` 

- The ``MX`` record: ```nslookup -type=mx lg-heig-vd.duckdns.org```
  - Answer:
  ```shell
  Server:         127.0.0.53
  Address:        127.0.0.53#53
  
  Non-authoritative answer:
  lg-heig-vd.duckdns.org  mail exchanger = 50 lg-heig-vd.duckdns.org.
  
  Authoritative answers can be found from:
  lg-heig-vd.duckdns.org  internet address = 4.180.67.252
   ```







