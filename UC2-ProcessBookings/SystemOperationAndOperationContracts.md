# System Operations: Use Case 1 - Process Bookings

- `initiateBooking(client_id)`
- `getOffering(offering_id)`
- `makeBooking(client, offering)`
- `endRegistration(client_id)`

# System Operation Contracts

---

## Contract CO1: `initiateBooking`

| **Operation**       | `initiateBooking(client_id)`         |
|---------------------|--------------------------------------|
| **Cross References**| Use case Process Bookings           |
| **Pre-conditions**  | - `Client` exists. <br> - `Client` is logged in to the system. |
| **Post-conditions** | - `cli:Client` instance’s booking status has been set to "initiated". (attribute modification) <br> - An association is formed between the `cli:Client` instance and a `Booking` instance. (formation of association) |

---

## Contract CO2: `getOffering`

| **Operation**       | `getOffering(offering_id)`           |
|---------------------|--------------------------------------|
| **Cross References**| Use case Process Bookings           |
| **Pre-conditions**  | - `Offering` exists.                |
| **Post-conditions** | - An association is established between the `cli:Client` and the retrieved `off:Offering` instance for the session. (formation of association) |

---

## Contract CO3: `makeBooking`

| **Operation**       | `makeBooking(client, offering)`      |
|---------------------|--------------------------------------|
| **Cross References**| Use case Process Bookings           |
| **Pre-conditions**  | - `off:Offering` exists. <br> - `cli:Client` exists and has an active booking session. |
| **Post-conditions** | - A `Booking` object, `bok`, has been created. (object creation) <br> - `bok` has been associated with the `cli:Client` and `off:Offering` objects. (formation of association) <br> - The `off:Offering` instance’s availability status is updated to "booked/unavailable". (attribute modification) |

---

## Contract CO4: `endRegistration`

| **Operation**       | `endRegistration(client_id)`         |
|---------------------|--------------------------------------|
| **Cross References**| Use case Process Bookings           |
| **Pre-conditions**  | - An active `Booking` associated with `cli:Client` exists. |
| **Post-conditions** | - The `cli:Client` instance’s session is closed, as well as the `Booking` instance. (attribute modification) <br> - `bok:Booking` instance is added to the list of confirmed bookings. (formation of association) |

---
