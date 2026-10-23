# Final Automation Report: Toolshop regression pack

Repo: https://github.com/Beliya470/toolshop-regression-pack. Jira story: TF40-95.

## What I automated and why
8 cases for the customer flows that matter most: the brands list, product search (4 terms), an unknown product, the password rule at registration, my own profile, another customer's profile, and the login page. 6 run on the API because it is fast and stable, and only the 2 login cases run in the browser because UI tests are slow and break easily. Each run makes its own customer, so no test depends on shared data or a hardcoded id. The pack runs with one `mvn test`, and the Jenkinsfile runs both suites in CI.

## What I left manual and why
Checkout, cart, payment and admin: they need more setup and more test data than I had time for, and a wrong automated check there would cost more than it saves. Visual layout and the look of the pages: a person sees these better than an assertion. Exploratory testing around the 8 cases (odd inputs, other browsers).

## Defects found (against the with-bugs shop)
| Jira | Defect | Pack case |
|---|---|---|
| TF40-105 (high) | Customer A reads the full profile of customer B: 200 instead of 404 | TF40-102 |
| TF40-106 (high) | The password hash is returned in the register answer and in the profile | TF40-101 |
| TF40-107 (medium) | Password minimum is 9 characters, the requirement says 8 | TF40-100 |
| TF40-108 (medium) | An unknown id that starts with digits (for example `3abc`) returns another product | TF40-99 |
| TF40-109 (medium) | Registration refuses the standard body and wants address, city and country as plain text | all (setup) |

I re-ran the API suite 3 more times: the four user cases failed every time. The unknown-product case failed in 1 of 4 runs, because it uses a random id and only ids that start with a digit trigger the bug (7 of 30 random ids). Brands, search (all 4 terms) and both UI login cases passed. TF40-109 is a different API contract and may not be a planned bug; I asked for a confirmation in the ticket.

## Known risks and gaps
- To run on the with-bugs API I had to add one option (`-Dregister.flat=true`) because registration did not work with the same body. The assertions were not changed.
- The unknown-product case can pass by luck on a buggy API, because the id is random.
- Only 8 cases: the with-bugs shop has more bugs than these cases can see (for example the stock field and other areas I did not test).
- The tests need the internet and Chrome. Jenkins was only run on a local copy of the repo.
- The Jira steps are written in the descriptions, not in the Xray step table.

## Recommendation
Flag. Do not release this version: a customer can read another customer's data and password hashes are exposed (TF40-105, TF40-106), and two more checks fail (TF40-107, TF40-108).
