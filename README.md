## Hours spent : 11

# The Motion Tests app !
This application contains a list of motion tests that can be performed by a user !
Each test is in its own module so the host app can import it or not. (For this demo, only the moving square test is available)

## The Moving Square test
The moving square test lets the user move a 70*70px square on the screen while recording:
* The positions of touches points
* Whether or not the square touched a border of the screen

## Working flow
### Git Flow
I am used to work in a team following git flow concepts, it means defining some specific branches with roles :
- master : the production branch, containing the code currently in production.
- dev : the branch on which we implement new features, contains the latest development changes.
- features : we create feature branches from dev to implement new features that will then be merged on dev (see name convention below).
- release : When ready we build a release candidate from dev and we start some rollout (alpha, beta... depending on the strategy). Once the rollout is completed, we merge it on master and dev.
- hotfix : If we need to fix a bug quickly, we can branch from master, do our changes and merge back on master and dev.

We usually tag branches with some convention name in order to trigger actions on the CI.

### Convention
In this project, the feature branches have been named : 'ANDX-name-of-feature'
The goal is to have a unique 'X' id for every feature so we keep track of who is working on which issue and what is the status (When working in agile team with a kanban board)

## Architecture
### MVVM
The application uses an MVVM architecture which is the most common one (in Android apps), the basic components of this architecture are :
- The model: Containing the data of the application
- The view: What will be shown on the screen, what the user will interact with
- The viewmodel: acting like a bridge between the model and the view. It will be able to get the data and transform it so it can be used by the view. The view will be subscribed to it to be updated when needed.

MVVM is the recommended pattern for Android app.

### Modules
In order to respect the "separation of concerns" concept and to make the tests easier, the Moving square app is separated in 3 modules :
- The data module, exposing repositories and handling how the data is retrieved.
- The domain module, supposed to encapsulate business logic (not so important in this current app as it is very simple but still!).
- The ui module, displaying the data on the screen, containing all the Android specific related code.

### Dynamic themes
The app supports dynamic theming the light and dark theme will be updated according to the device theme

### Flow
I chose to use Kotlin Flow (over live data / Rx) for async tasks.
I prefer Flow over LiveData mostly because LiveData is bound to Android while we can use Flow everywhere as it comes from Kotlin (like in our domain and data modules).
Rx is a great reactive solution too, I simply had to make a choice ;)

## Libraries

### Hilt
Hilt (dagger) for the dependency injection, reducing boilerplate code and making testing easier. (Alternative I used : Koin)

### Room
A local storing library allowing us to use SQL database for our local data. My implementation may not be perfect as I never had the opportunity to use it before!

### Improvment / Nice to have (if more time :) )
- The UI right... ?
- The square modules (ui, domain and data) are thought as to be a separated library, we should be able to upload them and retrieve it (via maven for instance) in different client apps
- The theming is only including light and dark mode, we could add some accessibility themes (for users that would need very contrasted colors)
- We could add some analytics libs in order to aggregate data (Firebase...)
- Configure a CI/CD env that would run the test and deploy the app (Or only a module)
- A keystore strategy, handled by the CI/CD
- The square / touch XY reports are only displayed in a dialog and not well presented / formatted
- Considering we save all the square XY, we could have a replay feature that would replay a whole motion
- Some of the square position calculation currently done in the SquareFragment could be moved to the SquareViewModel
- The app should be forced in landscape or portrait, if you mix both you can enter in weird states
- Some codestyle for readability and consistency
- Lint checks to avois mistakes
- Test room using https://developer.android.com/training/data-storage/room/testing-db
- The error management is really basic
- More Unit tests
- UI tests, I don't have enough time to implement UI tests but it would make a lot of sense in the square module to check that everything is happening normally when we move the square or hit a border
- We could have also tested the square module integration within the app, checking we can configure it properly from the app (configure the square size)

Thank you for your time, reading this and the code!!!


# WIP

todo : 
tests
faire une release 1.0

Nice to have :


Don't forget to test at the end :
test errors states



