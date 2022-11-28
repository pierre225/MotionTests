## Hours spent : 4

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

## Libraries

### Hilt
Hilt (dagger) for the dependency injection, reducing boilerplate code and making testing easier. (Alternative I used : Koin)

### Room
A local storing library allowing us to use SQL database for our local data. My implementation may not be perfect as I never had the opportunity to use it before!


# WIP

todo : 
display data

Nice to have :
Some analytics with firebase or other
considering we save the motion we could have a feature to replay a motion
The test module could be in another repo and retrieved via maven

Don't forget to test at the end :
Make sure we track the finger x,y and not the square xy
Adaptive theme
rotation screen behavior

