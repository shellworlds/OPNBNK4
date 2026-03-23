-- Per-service databases for local Docker Compose (single Postgres instance).
CREATE DATABASE accountdb OWNER bank;
CREATE DATABASE transactiondb OWNER bank;
CREATE DATABASE openbankingdb OWNER bank;
CREATE DATABASE authdb OWNER bank;
CREATE DATABASE frauddb OWNER bank;
CREATE DATABASE coresimdb OWNER bank;
CREATE DATABASE notificationdb OWNER bank;
