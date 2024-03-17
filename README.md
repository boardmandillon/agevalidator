A manufacturing company has an inventory system for recording machine IDs and machine ages within a
fleet.
Age is stored as a string in the form of ‘<number> <time unit>’, e.g. ‘1 year’, ’16 months’.

After a period of use, it was pointed out that some of the time units had been entered incorrectly
resulting in unreasonably long ages e.g. ’90 days’ was incorrectly recorded as ’90 years’.
You’ve been asked to develop a standalone REST microservice to validate a list of machine ages that
are sent up to your service.
The service will accept a list of {<machine ID>, <age string>} and return a list of machines whose
ages are unreasonably long compared to the other machines in the request body.
Think of this as an outlier detection problem. For an intro into the subject,
see https://towardsdatascience.com/a-brief-overview-of-outlier-detection-techniques-1e0b2c19e561.
You’re welcome to use any relevant algorithm. Getting extremely high degrees of accuracy is not the
goal of the task.

You will need to

1. Implement a microservice that must be implemented in Java. Any REST/web framework can be used for
   plumbing
2. Generate your own data to send as the request body and send through the file or program that
   generates the request body with your submission

Note use of open source libraries is permitted (and encouraged!)
Please document any improvements and additions you would want to make if this was a production
project.
The answer must be submitted by email and not posted to a public website or code repository (such as
GitHub).

---

### TEST DATA

1. Test case with valid machine ages:

``` 
curl -X POST http://localhost:8080/v1/validate -H "Content-Type: application/json" -d '[ "01, 1 year", "02, 16 months", "03, 90 days", "03, 90 days", "04, 90 days","05, 90 days", "06, 90 days", "07, 90 days", "08, 90 days", "09, 90 days", "10, 90 days", "11, 90 days" ]'
```

2. Test case with outlier machine ages:

``` 
curl -X POST http://localhost:8080/v1/validate -H "Content-Type: application/json" -d '[ "01, 1 year", "02, 16 months", "03, 90 days", "03, 90 days", "04, 90 days","05, 90 days", "06, 90 days", "07, 90 days", "08, 90 days", "09, 90 days", "10, 90 days", "11, 90 years" ]'
```

3. Test case with invalid machine ages:

``` 
curl -X POST http://localhost:8080/v1/validate -H "Content-Type: application/json" -d '[ "01, 1 year", "02, 16 months", "03, 90 days", "03, 90 days", "04, 90 days","05, 90 days", "06, 90 days", "07, 90 days", "08, 90 days", "09, 90 days", "10, 90 days", "11, 90 dayss" ]'
```

---

### IMPROVEMENTS AND ADDITIONS

#### Improve Swagger Documentation

Provide a clearer reference for clients. Swagger has incorrect examples for the request body.

#### Custom Exception Handling

Custom exception handling will better help diagnose issues with the data for clients.

#### Health Checks

Integrate Health checks so it can be inferred when the service is experiencing any issues.

#### Secure the API

Endpoints should be made secure with API keys.