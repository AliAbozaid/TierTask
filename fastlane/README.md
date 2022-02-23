fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew install fastlane`

# Available Actions
### clean
```
fastlane clean
```

### download_translation
```
fastlane download_translation
```
Download all translation form POEditor
### upload_translation
```
fastlane upload_translation
```
upload all translation to POEditor
### publishFirebase
```
fastlane publishFirebase
```
Upload (development or staging or production) debug build to Firebase App Distribute
### tag
```
fastlane tag
```

### send_slack
```
fastlane send_slack
```

### send_slack_fail
```
fastlane send_slack_fail
```


----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
