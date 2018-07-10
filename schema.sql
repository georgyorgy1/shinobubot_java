CREATE TABLE "custom_commands"
(
    "guild" TEXT NOT NULL,
    "command_name" TEXT NOT NULL,
    "response" TEXT NOT NULL
);

CREATE TABLE "infractions_channels"
(
    "guild" TEXT NOT NULL PRIMARY KEY,
    "private_channel" TEXT NOT NULL,
    "public_channel" TEXT NOT NULL
);

CREATE TABLE "listed_roles"
(
    "guild" TEXT NOT NULL,
    "role_name" TEXT NOT NULL
);

CREATE TABLE "infractions"
(
    "case_number" INTEGER PRIMARY KEY,
    "guild" TEXT NOT NULL,
    "username" TEXT NOT NULL,
    "user_id" TEXT NOT NULL,
    "mod_action" TEXT NOT NULL,
    "reason" TEXT NOT NULL,
    "message_id" TEXT NOT NULL,
    "start_date" TEXT NOT NULL,
    "end_date" TEXT NOT NULL
);
