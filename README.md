# Toolshop regression pack

A small automated regression pack for the Toolshop practice shop: 6 API tests (REST Assured) and 2 UI tests (Selenium). Java 21, Maven, JUnit 5.

## Run it from a fresh clone (3 steps)

1. Install JDK 21, Maven 3.9 or newer and Google Chrome.
2. `git clone https://github.com/Beliya470/toolshop-regression-pack.git` and `cd toolshop-regression-pack`
3. `mvn test`

That runs the API tests and then the UI tests. Chrome opens a window for the UI tests. On a server without a screen use `mvn test -Dheadless=true`.

Only one part: `mvn -pl api-tests test` or `mvn -pl ui-tests test`.

## Settings

| Setting | Default | What it does |
|---|---|---|
| `-Dbase.uri=...` | `https://api.practicesoftwaretesting.com` | The address of the API (API tests, and the API call that makes the customer for the UI test). |
| `-Dui.url=...` | `https://practicesoftwaretesting.com` | The address of the shop in the browser. |
| `-Dheadless=true` | off | Chrome runs without a window (for CI). |
| `-Dregister.flat=true` | off | Only for the with-bugs API: adds address, city and country as plain text to the registration body, because that API refuses the body without them. |

## Run it against the with-bugs shop

```
mvn test -Dmaven.test.failure.ignore=true -Dregister.flat=true "-Dui.url=https://with-bugs.practicesoftwaretesting.com/#" -Dbase.uri=https://api-with-bugs.practicesoftwaretesting.com
```

The with-bugs shop opens its pages after a `#`, so the UI address ends with `/#`. Without `-Dregister.flat=true` every test stops at the registration step. The failures I found are in `REPORT.md`.

## What is in it

| Jira | Level | What it checks |
|---|---|---|
| TF40-97 | API | `GET /brands`: a non-empty list, every brand has an id and a name. |
| TF40-98 | API | Product search with 4 terms (`pliers`, `PLIERS`, `Pli`, `hammer`): every name contains the term, ignoring case. Parameterized. |
| TF40-99 | API | Unknown product id (a random UUID) gives 404 and "Requested item not found". Negative. |
| TF40-100 | API | Password boundary: 7 characters are rejected (422), 8 are accepted (201). Boundary and negative. |
| TF40-101 | API | `GET /users/me` with the token returns my own profile. Needs a login. |
| TF40-102 | API | Customer A cannot read the profile of customer B (404, "You are not authorized to view this user."). Account boundary. |
| TF40-103 | UI | Valid login lands on "My account". Needs a login. |
| TF40-104 | UI | Unknown email shows "Invalid email or password". Negative. |

Every `@DisplayName` starts with its Jira key. The story is TF40-95 and the precondition is TF40-96.

## How the tests stay independent

- `BaseApiTest` sets the API address once, registers one new customer for the run (`fellow+<time>@test.com`) and keeps the token, so every API test reuses it. No seeded account is used.
- The UI login test registers its own new customer through the API before it runs.
- No test uses a hardcoded id. Ids come from the answers, or a random UUID is made.

## CI

The `Jenkinsfile` has the stages Checkout, Build, API Tests and UI Tests (headless), and publishes the JUnit results of both projects.
