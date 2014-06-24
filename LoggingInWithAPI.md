# Logging in WebDriver using ID API

Add to your local.conf file
```
loginEmail : "test.user@guardian.co.uk"
loginPassword : "asdf"
idApiRoot: "https://idapi.code.dev-theguardian.com"
```
A handy list of predefined envs at the guardian is here
(https://github.com/guardian/frontend/tree/master/common/conf/env)

Now in your Steps you can add `extends LoggingIn` or `with LoggingIn` as a trait.

Now just do `val myPage = logInToGUPage(MyPageObject.goto)` to start off with a logged in browser on that page!
