# SatelliteDemo

Satellite Tracker Demo  app for Android. Developed with Kotlin.
This application had 2 screens:
  1) Satellite List screen; shows satellite informations and them status.
  ![](ScreenShots/c33bcbdc6547c61b6987250c27fdd16e2fd02f6d/ScreenShots/Screen%20Shot%202022-02-21%20at%2000.04.54.png)
  2) Satellite Detail screen; shows selected satellite detail informations.
  ![](ScreenShots/c33bcbdc6547c61b6987250c27fdd16e2fd02f6d/ScreenShots/Screen%20Shot%202022-02-21%20at%2000.05.06.png)

Developed by MVVM design pattern with Clean Architecture . Thus, as can be seen in the picture below, a cyclic flow was achieved.
![](/mvvm-clean-arch.png)

## Libraries used

- Room
- Dagger-Hilt
- Gson
- Viewmodel
- StateFlow, SharedFlow
- Coroutines
- Material Librarary
- Navigation Components
- Custom View
- Unit Test (mocck, google-truth, coroutine-test)
