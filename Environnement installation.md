### 1 : Update your machine

```bash
$ sudo apt update && sudo apt upgrade
```

### 2 : Install apache2

```bash
$ sudo apt install apache2 apache2-utils
```

### 3 : Install PhP

```bash
$ sudo apt install php php-pgsql libapache2-mod-php
```

### 4 : Install Postgresql

```bash
$ sudo apt install postgresql postgresql-client postgresql-client-common postgresql-contrib
```

### 5 : Install PhpPgAdmin

```bash
$ sudo apt install phppgadmin
```

### 6 : Initialize Postgresql

```bash
$ sudo -i -u postgres
$ psql

CREATE USER root WITH PASSWORD 'root';
CREATE DATABASE "projectbdr";
GRANT ALL ON DATABASE "projectbdr" TO root;
\q

$ exit
```

### 7 : Configure PhpPgAdmin

```bash
$ sudo nano /usr/share/phppgadmin/conf/config.inc.php
```

Find and edit the line:
```bash
$ conf['extra_login_security']= false;
```

press `CTRL + w` and `CTRL + x` to save and exit

### 8 : Configure your VirtualHost

Just start by creating a directory where you will your local website

```bash
$ cd ~
$ git clone git@github.com:Nyaaw/ProjectBDR.git
```

Make a symbolic to `/var/www/` :

```bash
$ sudo ln -s /home/USER/ProjectBDR/src/ /var/www/html/
```

You will have to make sure that every directory in the path to the website source is readable and executable by apache :

```bash
$ sudo chmod +rx /home/USER
$ sudo chmod +rx /home/USER/ProjectBDR
$ sudo chmod +rx /home/USER/ProjectBDR/src
$ sudo chmod +rx /home/USER/ProjectBDR/src/index.html
```

### 9 : Helpful script

I don’t want my webserver start each time I boot my computer. So I deciding to disable the autostart and create a little script that gonna start it or not.

First, disable apache2 and postgresql on start.

```bash
$ sudo systemctl disable apache2
$ sudo systemctl disable postgresql
```

I want something easy to use to start my webserver. So create a new script file

```
sudo nano /usr/local/bin/webserver-launcher
```

Add the following block to it. It simply runs the commands one after the other that start, stop or restart this services.

```bash
#!/bin/bash

if [ "$1" != "" ]; then
   cmd="sudo service apache2 $1; sudo service postgresql $1"
   eval $cmd
else
   echo "No option was provided"
fi
```

Add the permission to execute this script and open your .bashrc.

```bash
$ sudo chmod +x /usr/local/bin/webserver-launcher
```

Reload wsl.

```bash
$ wsl.exe --shutdown
```

Et voilà !
You can now stop, start or restart easily your webserver.

```bash
$ webserver-launcher < stop | start | restart >
```

### 10 : Finished

Open your favorite browser and visit :

[http://localhost/src]()

