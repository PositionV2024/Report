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
  - Example reason 1
  - Example reason 2
  reported by:
  - PositionV
  - popo
  date:
  - '2024-09-26 : 21-47-22'
  - '2024-09-26 : 21-50-14'
  Player reported amount: 2
```
