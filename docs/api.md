# Introduction

This file contains the specifications for the REST endpoints exposed by OneSaver application.

## Use Cases

The application allows the customer to retrieve the account balance, check the potential saving from a round-up of
 the OUT transactions performed in a week since a given date, and transfers the amount from the primary account to the saving space.

### Assumptions
- The customer has a primary account
- There is only one saving space available

## Account balance

This API fetched the balance on the primary account.

### Endpoint 
```http
GET /account-balance
```

### Responses

The value of the account balance in minor units.

### Status Codes

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |


## Round-up potential savings

This API retrieves the amount of potential savings from rounding up all the transactions marked as FASTER_PAYMENTS_OUT in a week since a given date.
### Endpoint
```http
GET /roundup
```

| Parameter | Type         | Description                                                       |
|:----------|:-------------|:------------------------------------------------------------------|
| `from`    | `yyyy-MM-dd` | **Required**. Starting value of the date interval. Ex. 2024-04-01 |

### Responses

The value of the potential savings in minor units.

### Status Codes

| Status Code | Description   |
|:------------|:--------------|
| 200         | `OK`          |
| 400         | `BAD REQUEST` |

### Example
```bash
curl -X GET -F from=2024-04-18 http://localhost:8080/roundup 
```

## Saving space details

This API retrieves the details of the existing saving space from the customer account.

### Endpoint
```http
GET /savinggoal
```

### Responses

The details of the saving space in JSON format.

### Status Codes

| Status Code | Description |
|:------------|:------------|
| 200         | `OK`        |


## Save round-up value into saving goal

This API performs the internal transfer of the amount of the round-up from the primary account to the saving goal.

### Endpoint
```http
GET /save-roundup
```

| Parameter | Type         | Description                                                       |
|:----------|:-------------|:------------------------------------------------------------------|
| `from`    | `yyyy-MM-dd` | **Required**. Starting value of the date interval. Ex. 2024-04-01 |

### Responses

`true` if the internal transfer was successful.

### Status Codes

| Status Code | Description   |
|:------------|:--------------|
| 200         | `OK`          |
| 400         | `BAD REQUEST` |