# System Operations: Use Case 1 - Process Offerings

The following operations are part of the **Process Offerings** use case:

- `initiateOfferingProcess()`
- `viewLessons()`
- `selectLesson(instructor, lesson)`
- `makeOfferingAvailable(administrator, lesson)`
- `viewOfferings()`

---

# Operation Contracts

## Contract CO1: Initiate Offering Process

| **Operation:**        | `initiateOfferingProcess()`                              |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - Administrator has logged in.                           |
| **Post-conditions:**  | - If valid, the user successfully logs in (association formed). |

---

## Contract CO2: View Lessons 

| **Operation:**        | `viewLessons()`                                          |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - Instructor has logged in.                              |
| **Post-conditions:**  | - Lessons are displayed for viewing.                     |

---

## Contract CO3: Select Lesson

| **Operation:**        | `selectLesson(instructor, lesson)`                       |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - Instructor has been authenticated by the system.<br>   |
|                       | - `Lesson` and `Instructor` objects exist.<br>           |
|                       | - No other offering exists for the same `Lesson` with the same instructor. |
| **Post-conditions:**  | - An instance of `Offering` is created.<br>              |
|                       | - The `Offering` is associated with the `Instructor`.<br>|
|                       | - The `Offering` is associated with the `Lesson`.<br>    |
|                       | - The `id` attribute is initialized in the `Offering`.<br>|
|                       | - The `Offering` availability is set to "Unavailable".   |

---

## Contract CO4: Make Offering Available

| **Operation:**        | `makeOfferingAvailable(admin, lesson)`                  |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - The `Offering` object exists.<br>                      |
|                       | - Administrator has been authenticated by the system.    |
| **Post-conditions:**  | - The `Offering`'s availability attribute is updated.    |

---

## Contract CO5: View Offering

| **Operation:**        | `viewOffering()`                                         |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - The `Offering` object exists.                          |
| **Post-conditions:**  | - Offerings are displayed for viewing.                   |
