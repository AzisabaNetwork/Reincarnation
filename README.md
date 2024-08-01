<p align="center"><img src="material/unknown.png" width="64px"></p>
<h1 align="center">Reincarnation</h1>
<p align="center">Reincarnation Core System</p>

**Note: This plugin is in development!!**

## Overview

This is the core Paper plugin for Reincarnation.

```
Version: 0.3.1
Minecraft Version: 1.17.1
Database Server: MySQL 8.0
```

## Tables

This plugin requires several tables to work.

```sql
CREATE TABLE user(id VARCHAR(36), name VARCHAR(16), role VARCHAR(16), guild VARCHAR(36), exp INT UNSIGNED, money INT UNSIGNED);
CREATE TABLE guild(id VARCHAR(36), name VARCHAR(16), master VARCHAR(36), exp INT UNSIGNED, money INT UNSIGNED);
CREATE TABLE quest(id TEXT, user VARCHAR(36));
CREATE TABLE friend(user1 VARCHAR(36), user2 VARCHAR(36));
```