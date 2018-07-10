# ShinobuBot
```
Build: 20180710-xxxx
```

ShinobuBot is a cute multipurpose Discord Bot written in Java. It uses the JDA library to interact with Discord.

NOTE: Self-hosting is not yet supported (partially). I'm working on this. Host at your own risk because I will not help you with self-hosting. This repository was created for transparency purposes and to showcase the capabilites of the bot. If you're going to use my codebase for your bot, please make sure you do it in accordance with the license (MIT License, because I love you guys).

JDK Requirement:
- OpenJDK 8 or higher

Required Libraries:
- JDA Discord Library (Latest) (with Dependencies)
- JDA Utilites (All Jars) (Latest)
- Logback
- SLF4J
- Java EE 7 or higher
- Glassfish Server 4.1 or higher (Although newer versions of Glassfish are a pain to install on NetBeans 8.x)
- SQLite Java Driver

Self-hosting:
```
1. Clone the repository.
2. Add to the required libraries to the classpath.
3. Open SQLite and create the needed tables (check schema.sql). Save as shinobu.db.
4. Copy shinobu.db to the files folder.
5. Open config.json and edit the needed strings.
6. Compile the bot.
7. Now you're up and running.
To see the commands, type !cmds or !help.
```

