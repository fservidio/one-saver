# One-saver

This repository contains the code for the application developed for the tech interview assignment.

## Important
Make sure that a valid token is specified inside the file ``src/main/application.properties``

Or alternatively, you can run the application and specify the property as an environment variable.

## Installation

Requirements:
Java SDK 21

```bash
./gradlew bootRun
```
You can specify the token env. variable as follows:

```gradle bootRun --args='--client.starling.token=<INSERT_TOKEN_HERE>'```

## Usage

```bash
http://localhost:8080
```


## Folder structure

### Top-level directories

    .
    ├── build                   # Compiled files (alternatively `dist`)
    ├── docs                    # Documentation files (alternatively `doc`)
    ├── src                     # Source files (alternatively `lib` or `app`)
    ├── test                    # Automated tests (alternatively `spec` or `tests`)
    ├── tools                   # Tools and utilities
    ├── LICENSE
    └── README.md
