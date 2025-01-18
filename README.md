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
  - [Recommendation (MAYBE)](#recommendation-maybe)
  - [As a User](#as-a-user)
  - [As a Developer](#as-a-developer)
    - [Additional Prerequisites](#additional-prerequisites)
    - [Setup Using GitHub](#setup-using-github)
      - [Clone the repository](#clone-the-repository)
      - [Build the application source code](#build-the-application-source-code)
      - [Build the application package](#build-the-application-package)
- [Deployment](#deployment)
  - [Prerequisites](#prerequisites)
    - [Local Use (MAYBE not possible )](#local-use-maybe-not-possible-)
    - [Online Use](#online-use)
  - [Steps](#steps)
- [Usage](#usage)
  - [Run the application](#run-the-application)
    - [Using Docker](#using-docker)
    - [Without Docker (MAYBE remove)](#without-docker-maybe-remove)
- [Demonstration](#demonstration)
  - [Project](#project)
  - [API](#api)
- [Complementary Information](#complementary-information)





## README Criterion (to remove later)
- Category 1 - Git, GitHub and Markdown
  - [ ] The README is well structured and explains the purpose of your web with the authors' names so new users can understand it and know who is behind the application
- Category 2 - Java, IntelliJ IDEA and Maven
  - [ ] The README describes explicit commands to clone and build your network application with Git and Maven so new developers can start and develop your project on their own computer
- Category 3 - Docker and Docker Compose
  - [ ] The README describes explicit commands to build and publish your network application with Docker
  - [ ] The README describes explicit commands to use your network application with Docker Compose so other people can easily use it
- Category 6 - HTTP and curl
  - [ ] The README (or repository) contains the application protocol interface (API) that describes the web application
  - [ ] The README explains how to use your web application with explicit examples using curl with outputs to demonstrate how to interact with your web application deployed on the Internet
- Category 7 - Web infrastructures
  - [ ] The README (or repository) contains instructions how to install and configure the virtual machine with each step
  - [ ] The README (or repository) contains explains how to configure the DNS zone to access your web application
  - [ ] The README (or repository) contains instructions how to deploy, run and access the web applications with Docker Compose
  - [ ] The README displays the domain names configuration in the DNS zone to validate everything is set up right

- [ ] And More
- [ ] Complet all the `TOADD`
- [ ] Verify all the `MAYBE`










## Introduction

TOADD







## Getting Started 
### General Prerequisites
- Docker
- Docker Compose

To use the docker image, you first need to ensure that docker is installed on your machine.
If that's not the case, please go to the official website ([Dockerdocs](https://docs.docker.com/engine/)) and follow the instruction for the version you need.







### Recommendation (MAYBE)







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
./mvnw package
```

##### Build the application package
If you were to change our application, you will need to build it again and publish it on your account.

To build the app:

Go into the folder of the app and use the command:
```sh
docker build -t <TOADD> .
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


You can either choose to deploy localy or online. (MABYE not)




#### Local Use (MAYBE not possible )
- Virtual Machine 
  - Docker Compose
  - Java
  
- TOADD (MAYBE)





#### Online Use
- Cloud Based Virtual Machine
  - Docker Compose
  - Java
- Domain Name

- TOADD (MAYBE)


> [!NOTE]
> You can also use a server instead of a Cloud Based VM if you have one available, the application set up should be pretty similar but as we won't explain this scenario you might need to do some research to succed.  


If you don't know how to set up a VM you can read [this document](docs/VM_Help.md) to get the basics.  


If you don't have a domain name and don't know how to get one you can read [this document](docs/Domain_Name_Help.md) to get the basics.  







### Steps

TOADD













## Usage
Once the app is built, you can run it.


### Run the application
#### Using Docker

TOADD



#### Without Docker (MAYBE remove)




## Demonstration
### Project 

TOADD


### API
Demonstration made using curl to simulate request.

TOADD




## Complementary Information
If you need more informations about the API you can consult [this file](docs/API.md).

