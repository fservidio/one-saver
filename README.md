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
    ├── .github/ISSUE_TEMPLATE                  # Contains template specifications for GitHub issues customization
    ├── docs                                    # Contains the documentation for the API available from the OneSaver application
    ├── gradle/wrapper                          # Contains jar files and gradle properties to run gradle scripts without the need to download and install it in advance
    ├── specs                                   # Contains the OpenAPI specifications for Starling Bank API
    ├── src                                     # Contains the source files
    ├── .gitignore                              # List of files not to be included on the remote repo
    ├── LICENSE                                 # The software is released with Apache-2.0 license due to libraries included in the project (ie. Spring)
    ├── README.md                               # This file
    ├── build.gradle                            # Configuration file that includes the dependencies definitions used in the project
    ├── gradlew                                 # Gradle startup script file for Unix/Linux
    └── gradlew.bat                             # Gradle startup script for Windows


## Source and test directories
    .
    ├── ...
    └── src
        ├── main
        │   ├── java
        │   │   └── com.starling.onesaver
        │   │       ├── client                  # Contains the implementation for the WebClient to perform API calls to Starling Bank services
        │   │       ├── controller              # Contains the controllers that expose REST endpoints
        │   │       ├── service                 # Contains the services for the business logic
        │   │       └── util                    # Contains utils classes such as date converter
        │   └── resources                       # Properties files. Store the access token here as specified above.
        └── test
            ├── java
            │   └── com.starling.onesaver
            │       ├── controller              # Controller tests such as validations and services data flow
            │       ├── e2e                     # Contains tests for the entire flow from getting the account details to saving the roundup value
            │       └── integration             # Contains tests that are performed through Starling Bank sandbox APIs
            └── resources                       # Properties files. Store the access token here as specified above.

API specifications available [here](./docs/api.md).