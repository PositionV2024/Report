![A report system that allows you to report players](https://raw.githubusercontent.com/PositionV2024/Test/refs/heads/main/media/Screenshot%202024-09-21%20113646.png)
# Project test
Dummy description for dummy.
## Features
### Report data
When you report a player, a report data will be generated in the report file. This includes, the name of the player that is reported, the name of the player that reports, the reported reason, and finally, the time that the report was made.
### Check player's report via in-game
There is a command to check player's report via in-game. It will mirror what is in the report file.
### Reload
Reload through in-game, instead of restarting the whole server.
### Clear player's report
Clear player's report data when it is no longer required.
### Version checker
I have integated a system to the plugin where if the plugin is loaded, and this github release page is updated, you will receive new updates via in-game. You can also disable this through the config file.
## Commands
`/report version`
<details>
  <summary>Check the latest version of the plugin.</summary>
</details>
`/report reload`
<details>
  <summary>Reloads the necessary files.</summary>
</details>
`/report clear <player>`
Clears the specified player's report data.
`/report check <player>`
Check the specified player's report data.
## report.yml (example)`
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
