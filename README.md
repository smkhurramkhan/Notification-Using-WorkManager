# Notification-Using-WorkManager
Schedule Notification using Work Manager and android 12 fix
Sometimes it is required to display a notification at a specific time, a task that unfortunately is not trivial on the Android system, as there is no method setTime() or similiar for notifications. This example outlines the steps needed to schedule notifications using the Work Manager.

Why WorkManager?

WorkManager, a compatible, flexible and simple library for deferrable background work. WorkManager is the recommended task scheduler on Android for deferrable work, with a guarantee to be executed.
