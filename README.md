![A report system that allows you to report players](https://raw.githubusercontent.com/PositionV2024/Test/refs/heads/main/media/Screenshot%202024-09-21%20113646.png)
# Project test
Dummy description for dummy.
## Features
### Report data
When you report a player, a report data will be generated in the report.yml. This includes, the name of the player that is reported, the name of the player that reports, the reported reason, and finally, the time that the report was made.
### Clear player's report data
Clear player's report data when it is no longer required. This is to ensure that banned player's report data is not in the file.
## report.yml (example)
```
f798e323-b270-30de-9547-b053e429138b:
  name:
  - '233'
  reason:
  - example_reason (0)
  - example_reason (1)
  - example_reason (2)
  - example_reason (3)
  reported by:
  - '233 : f798e323-b270-30de-9547-b053e429138b'
  - '233 : f798e323-b270-30de-9547-b053e429138b'
  - '233 : f798e323-b270-30de-9547-b053e429138b'
  - '233 : f798e323-b270-30de-9547-b053e429138b'
  date:
  - '2024-09-24 : 11-31-14'
  - '2024-09-24 : 11-31-22'
  - '2024-09-24 : 11-31-28'
  - '2024-09-24 : 11-31-38'
```
