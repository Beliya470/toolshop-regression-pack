# Capstone plan: Toolshop regression pack

## What is in scope
8 cases for the main customer flows of Toolshop: the brands list, product search, an unknown product, the password rule at registration, my own profile, another customer's profile, and the login page.

Not in scope today: checkout, cart, admin, and the run against the with-bugs shop (that is the next morning).

## Which level
- **API (6 cases):** fast, and they do not depend on the page.
- **UI (2 cases):** only the two login cases. The login page is the one place where a user really needs the browser. UI tests are slow and break easily, so I keep few.

## Test data
- The pack makes its own customer for every run (`fellow+<time>@test.com`), logs in once and keeps the token for all API tests.
- The account boundary case registers a second customer (B).
- No seeded account and no hardcoded id. Ids come from the answers. The unknown product id is a random UUID.
- The Toolshop data is shared and resets, so no test asserts an exact count.

## Jira
Story TF40-95 (9 acceptance criteria), precondition TF40-96, and the 8 cases TF40-97 to TF40-104. Every `@DisplayName` starts with its Jira key.

| Jira | Level | Case | Covers | Kind |
|---|---|---|---|---|
| TF40-97 | API | Brands list is not empty, every brand has an id and a name | AC1 | positive |
| TF40-98 | API | Search returns only names that contain the term (pliers, PLIERS, Pli, hammer) | AC2, AC3 | data-driven |
| TF40-99 | API | Unknown product id gives 404 and no product data | AC4 | negative |
| TF40-100 | API | Password of 7 characters is rejected, 8 is accepted | AC5 | boundary, negative |
| TF40-101 | API | Logged-in customer reads own profile with `GET /users/me` | AC6 | needs login |
| TF40-102 | API | Customer A cannot read the profile of customer B | AC7 | account boundary, negative |
| TF40-103 | UI | Valid login lands on "My account" | AC8 | needs login |
| TF40-104 | UI | Unknown email shows "Invalid email or password" | AC9 | negative |

## What comes next
1. Run the pack against the with-bugs UI and API (`-Dui.url=...` and `-Dbase.uri=...`).
2. Log a Jira bug for every real failure, with the failing assertion as evidence.
3. Write the 1-page Final Automation Report.
4. Present it.
