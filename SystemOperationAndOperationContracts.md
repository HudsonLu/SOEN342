# System Operations: Use Case 1 - Process Offerings

- `login(credentials)`
- `makeOfferingAvailable(admin,lesson)`
- `selectLesson(instructor,lesson)`
- `viewOffering()`

# Operation Contracts

## Contract CO1: login

| **Operation:**       | `login(userID, phoneNumber)`                          |
|----------------------|----------------------------------------------------|
| **Cross References:** | Use case Process Offerings                        |
| **Pre-conditions:**  | Credentials (`userID`, `phoneNumber`) must be provided |
| **Post-conditions:** | If valid, the user successfully logs in. Otherwise, an error message will be displayed. |


## Contract CO2: makeOfferingAvailable

| **Operation:**       | `makeOfferingAvailable(admin,lesson)`                          |
|----------------------|----------------------------------------------------|
| **Cross References:** | Use case Process Offerings                        |
| **Pre-conditions:**  | Offer(s) exist(s) |
| **Post-conditions:** | Offer's attribute availability was modified (modification of attribute)|


## Contract CO3: selectLesson

| **Operation:**       | `selectLesson (instructor,lesson)`                          |
|----------------------|----------------------------------------------------|
| **Cross References:** | Use case Process Offerings                        |
| **Pre-conditions:**  | Offer(s) exist(s) |
| **Post-conditions:** | Offer is associated with Instructor (association formed) |


## Contract CO4: viewOffering

| **Operation:**       | `viewOffering()`                          |
|----------------------|----------------------------------------------------|
| **Cross References:** | Use case Process Offerings                        |
| **Pre-conditions:**  | Offers are valid |
| **Post-conditions:** | Offers can be viewed|
