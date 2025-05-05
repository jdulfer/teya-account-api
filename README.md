# Account API
### Purpose
The Account API contains all of the required resources for the Teya Technical Challenge. It manages the state of the Accounts within the system. In a real world implementation the Account state would be stored in a DB and I have tried to build the API with that in mind, but for now the Accounts are stored in memory
### Resources
- `GET: /hello`
    - Returns Hello World. Good to check it's working as intended
- `GET: /accounts/:accountId/balance`
    - Returns the balance of a given account ID
- `GET: /accounts/:accountId/transactions`
    - Returns a history of all the transactions associated with an account ID
- `POST: /accounts/:accountId/move-money`
    - Post Body: Contains the receiverAccountId and am amount to transfer
    - Moves the money from the path param `:accountId` to the accountId associated with the `receiverAccountId`
```json
{
  "receiverAccountId": "2",
  "amount": "2"
}
```