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

## Contract CO2: View Lessons Process

| **Operation:**        | `viewLessons()`                              |
|-----------------------|---------------------------------------------------------- ------|
| **Cross References:** | Use Case: Process Offerings                                     |
| **Pre-conditions:**   | - Instructor has logged in                                      |
| **Post-conditions:**  | - If valid, the user successfully logs in (association formed). |

---

## Contract CO3: Select Lesson

| **Operation:**        | `selectLesson(instructor, lesson)`                       |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - Instructor has been authenticated by the system.       |
|                       | - `Lesson` and `Instructor` objects exist.               |
|                       | - No other offering exists for the same `Lesson` with the same instructor. |
| **Post-conditions:**  | - An instance of `Offering` is created (instance creation).                  |
|                       | - The `Offering` is associated with the `Instructor` (association formed).    |
|                       | - The `Offering` is associated with the `Lesson` (association formed).        |
|                       | - The `id` attribute is initialized in the `Offering` (attribute modification).   |
|                       | - The `Offering` availability is set to "Unavailable" (attribute modification).   |

---

## Contract CO4: Make Offering Available

| **Operation:**        | `makeOfferingAvailable(admin, lesson)`                  |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - The `Offering` object exists.                          |
|                       | - Administrator has been authenticated by the system.    |
| **Post-conditions:**  | - The `Offering`'s availability attribute is updated(attribute modification). |

---

## Contract CO5: View Offerings

| **Operation:**        | `viewOfferings()`                                         |
|-----------------------|----------------------------------------------------------|
| **Cross References:** | Use Case: Process Offerings                              |
| **Pre-conditions:**   | - The `Offering` object exists.                          |
| **Post-conditions:**  | - Offerings are displayed for viewing.                   |
