<p align="center"><img src="material/unknown.png" width="64px"></p>
<h1 align="center">Reincarnation</h1>
<p align="center">Reincarnation Core System</p>

**Note: This plugin is in development!!**

## Overview

This is the core Paper plugin for Reincarnation.

```
Version: 0.5.1
Minecraft Version: 1.17.1
Database Server: MariaDB 11.4.2
```

## Tables

This plugin requires several tables to work.

```sql
CREATE TABLE user(id VARCHAR(36), name VARCHAR(16), role VARCHAR(16), guild VARCHAR(36), exp INT UNSIGNED, money INT UNSIGNED, youtube VARCHAR(30), twitter VARCHAR(15), discord VARCHAR(32));
CREATE TABLE guild(id VARCHAR(36), name VARCHAR(16), master VARCHAR(36), exp INT UNSIGNED, money INT UNSIGNED);
CREATE TABLE quest(id TEXT, user VARCHAR(36));
CREATE TABLE friend(user1 VARCHAR(36), user2 VARCHAR(36));
```