# AI tool use (iP.AI)

Record of AI-assisted work on this project. Updated periodically.

## Tools used

* **Cursor** (AI-assisted editor, used mainly for guidance, refactoring suggestions, and debugging support)

---

## Log

### Level increments

* **Level 7–10**: Implemented manually.
  Used Cursor occasionally to clarify edge cases, check logic structure, and suggest small refactorings.

---

### Category A (coding standard / tooling)

* **A-Gradle**: Manual setup; used Cursor to clarify configuration errors.
* **A-JUnit**: Wrote tests manually; used Cursor to review assertion coverage and suggest additional edge cases.
* **A-Streams**: Refactored loops into streams myself; used Cursor to compare alternative stream patterns and improve readability.
* **A-Varargs**: Manual implementation.
* **A-Packages**: Manual restructuring.
* **A-MoreOOP**: Manual refactoring; used Cursor to review class responsibilities.
* **A-Jar**: Manual setup following Gradle guide.
* **A-JavaDoc**: Wrote documentation manually; used Cursor to improve phrasing and clarity.
* **A-CodingStandard**: Manual formatting and naming adjustments.
* **A-Checkstyle**: Manual fixes; used Cursor to interpret some Checkstyle warnings.
* **A-CodeQuality**: Refactored manually; used Cursor to identify potential simplifications and dead code.
* **A-Assertions**: Added assertions manually; used Cursor to double-check appropriate assertion placement.
* **A-CI**: Manual setup; used Cursor to troubleshoot YAML/config syntax issues.
* **A-AiAssisted**: Manual Setup

---

### Category B (extension)

* **B-Reminders extension**:
  Core logic, command structure, and integration were implemented manually.
  Cursor was used to:

  * Suggest alternative method structures
  * Review parser branching logic
  * Help identify minor bugs
  * Propose additional test cases

---

### Bugfixes / follow-ups

* **List double-print fix**:
  Investigated manually; Cursor helped point out duplicate method calls and suggested simplifying the delegation structure in `Ui` and `GuiUi`.

---

## Observations

* **What worked well**:
  Cursor was helpful for reviewing code structure, spotting redundant logic, interpreting error messages, and suggesting refactorings. It was particularly useful when debugging or verifying edge cases.

* **Limitations**:
  AI suggestions sometimes required correction or adjustment to match the project’s structure and naming conventions. Manual review was necessary to ensure correctness.

* **Overall impact**:
  AI acted as a productivity assistant and second pair of eyes rather than a replacement for implementation. It helped speed up debugging and refactoring while core design and logic decisions remained manual.
