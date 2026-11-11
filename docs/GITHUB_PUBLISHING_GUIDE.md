# Publish your portfolio and API to GitHub

## 1. Keep the existing website intact

Your live website remains in `calvinstechsupport/calvinstechsupport.github.io`. Do not replace that repository or change its Pages publishing branch. Add `WEBSITE_PORTFOLIO_README.md` from this ZIP as a new root-level file named `README.md` if the repository does not already have one. This is documentation only; it does not change the website pages.

## 2. Create the API repository

1. Sign in to https://github.com/new.
2. Repository name: `network-device-inventory-api`.
3. Owner: `calvinstechsupport`.
4. Visibility: Public (this is a portfolio project; never commit credentials).
5. Leave the options to add a README, `.gitignore` and licence unchecked because this project already contains them.
6. Click Create repository.

## 3. Upload the API project from Windows

1. Extract `network-device-inventory-api.zip` into a folder such as `Documents\GitHub\network-device-inventory-api`.
2. Open PowerShell in that folder.
3. Run these commands:

```powershell
git init
git add .
git commit -m "Initial Network Device Inventory API"
git branch -M main
git remote add origin https://github.com/calvinstechsupport/network-device-inventory-api.git
git push -u origin main
```

If Git prompts for sign-in, complete the Git Credential Manager browser sign-in. Do not paste a password or token into this guide or into the source files.

## 4. Create the profile README

1. Create a second public GitHub repository named exactly `calvinstechsupport` (same as the username).
2. Copy `PORTFOLIO_PROFILE_README.md` from this project, rename the copy to `README.md`, and put it in the root of that profile repository.
3. Commit and push. GitHub then displays the README on the profile page.

## 5. Run and test before advertising the API

Install a Java 21 JDK and Apache Maven 3.9.11 or later. In PowerShell, from the API project folder:

```powershell
java -version
mvn -version
mvn verify
mvn spring-boot:run
```

Open `http://localhost:8080/swagger-ui.html` and try the POST, GET, PUT and DELETE examples in `docs/api-examples.http`. Do not call the project deployed until these local tests pass and the hosted endpoints have been checked.

## 6. Don't publish assessment material

For coursework repositories, publish only original work that you are allowed to share. Do not upload exam papers, answer keys, locked Packet Tracer assessment files, Blackboard content, student numbers, customer data, passwords or API keys. Screenshots can support a project README, but include a short explanation of what you personally designed, configured and tested.
