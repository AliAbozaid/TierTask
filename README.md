## Tier-Task

Tier Android Assignment!

## Code structure
I’m following the clean architecture.

## CI
Setup your Jenkins to run the below command based on the received variables and the correct env for each project setup

```
bundle exec fastlane publishFirebase build_type:Release release_notes:'Test1' beta_group:'Test' version_code:2 version_name:'0.0.1' --env release
```

build_type:`Release` or `Debug`
release_notes:`Your Release notes`
beta_group:`Your Beta test group`
version_code: `Your version code`
version_name: `Your version name`

For beta Release you should use `Alpha` like below but add your store keys first to env

```
bundle exec fastlane Alpha build_type:Release release_notes:'Test1' beta_group:'Test' version_code:2 version_name:'0.0.1' --env release
```

For Huawei use `publishHuawei` like below but add your store keys first to env

```
bundle exec fastlane publishHuawei build_type:Release release_notes:'Test1' beta_group:'Test' version_code:2 version_name:'0.0.1' --env release
```

## Developed by

This project developed by Ali Abdelahlim you can find me [here](https://www.linkedin.com/in/aliabozaid/)
 

