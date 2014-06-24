# Parallel testing with SBT
To limit the number of concurrent tests, e.g. 4, you can add this to your sbt file
```
concurrentRestrictions in Global := Seq(
  Tags.limit(Tags.Test, 4)
)
```