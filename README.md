# Project BDR/DAI 

## Autor <!-- omit in toc -->
- Alex Berberat
- Lisa Gorgerat
- Pierric Ripoll


## Table of Content <!-- omit in toc -->
- [README Criterion (to remove later)](#readme-criterion-to-remove-later)
- [Introduction](#introduction)
- [Getting Started Using GitHub](#getting-started-using-github)
  - [Prerequisites](#prerequisites)
  - [Recommendation](#recommendation)
  - [Setup](#setup)
    - [Clone the repository](#clone-the-repository)
    - [Build the application](#build-the-application)
- [Getting Started Using Docker](#getting-started-using-docker)
  - [Prerequisites](#prerequisites-1)
  - [Setup](#setup-1)
    - [Get the package](#get-the-package)
    - [Build the application](#build-the-application-1)
- [Usage](#usage)
  - [Run the application](#run-the-application)
    - [Using Docker](#using-docker)
    - [Without Docker](#without-docker)
- [Demonstration](#demonstration)





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
- [ ] Complet all the TOADD








## Introduction




## Getting Started Using GitHub
### Prerequisites
- JDK
- Maven (optional, a maven wrapper comes with the project)
- Docker
- 

### Recommendation

### Setup
#### Clone the repository
1. Go to the [repository](https://github.com/AlexB-HEIG/DAI-Practical-work-2) on GitHub and choose your favorite clone option.
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


#### Build the application
To build the application, you can use the following commands in your terminal.
```sh
# Download the dependencies and their transitive dependencies
./mvnw dependency:go-offline

# Package the application
./mvnw package
```




## Getting Started Using Docker
### Prerequisites
To use the docker image, you first need to ensure that docker is installed on your machine.
If that's not the case, please go to the official website ([Dockerdocs](https://docs.docker.com/engine/)) and follow the instruction for the version you need.



### Setup
#### Get the package
You can find our package at [this link.]()

or use this command:
```sh
docker pull ghcr.io/<TOADD>
```

#### Build the application
If you were to change our application, you will need to build it again and publish it on your account.

To build the app:

Go into the folder of the app and use the command:
```sh
docker build -t <TOADD> .
```

To publish the container on GitHub:

1. You first need to connect to your account:
````sh
docker login ghcr.io -u <username>
````

2. You need to tag the image:
````sh
# Tag the image with the correct format
docker tag <TOADD> ghcr.io/<username>/<TOADD>:latest
````

3. You publish the container on your account:
````sh
docker push ghcr.io/<username>/<TOADD>
````



## Usage
Once the app is built, you can run it.


### Run the application
#### Using Docker




#### Without Docker 




## Demonstration

