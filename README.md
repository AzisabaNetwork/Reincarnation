# Reincarnation

Reincarnation Core System

## Overview

### About this plugin:

```yaml
Version: 0.6.1
Minecraft: 1.17.1
Database: MariaDB 11.4.2
```

### This plugin uses the following tables:

```sql
CREATE TABLE user(id VARCHAR(36), name VARCHAR(16), role VARCHAR(16), guild VARCHAR(36), exp INT UNSIGNED, money INT UNSIGNED, youtube VARCHAR(30), twitter VARCHAR(15), discord VARCHAR(32));
CREATE TABLE guild(id VARCHAR(36), name VARCHAR(16), master VARCHAR(36), exp INT UNSIGNED, money INT UNSIGNED);
CREATE TABLE quest(id TEXT, user VARCHAR(36));
CREATE TABLE friend(user1 VARCHAR(36), user2 VARCHAR(36));
```

## Get started

> [!NOTE]  
> This plugin was developed for use with [アジ鯖](https://www.azisaba.net)'s Reincarnation server.

### Prepare a paper server

Install Java 17 and download the Paper server for Minecraft 1.17.1 from [Paper MC](https://papermc.io/downloads/paper).

The downloaded Paper server creates a new directory and places it in it.

It is also useful to create a bat file in the directory to start the server.

```sh
java -jar -Xmx4G -Xms4G paper-1.17.1-410.jar -nogui
```

### Install a database server

Reincarnation uses Maria DB for storage.

Download the installer from [Maria DB](https://mariadb.org/) and run it.

```sql
CREATE DATABASE {database name};
```

Create the necessary tables in the database you created by executing the SQL listed in `Overview > This plugin uses the following tables`

### Install plugin

Create a local repository and run build with Gradle.

```sh
$ git clone https://github.com/AzisabaNetwork/Reincarnation.git
```

Move the built jar file to the plugins directory in the server directory.

This completes the installation.

### Configure config.yml

When the server is started for the first time, a config.yml is generated in `/plugins/Reincarnation`.

Describe the database connection settings here.

> [!IMPORTANT]  
> The user described here should be granted sufficient privileges to operate the database to be used.

```yaml
database:
  url: jdbc:mariadb://localhost/{database name}
  user: # User used in connection.
  pass: # password used in connection.
```

Restart the server and it will work.

### How to use

The actual use of the plugin can be found in the Documents channel of the Reincarnation Discord server if you are an Azisaba administrator, otherwise in-game using the /rc command and its tab completion.

Do not expect much documentation to be added to README.

## Finally

This is the end of the README.

Have a nice day!