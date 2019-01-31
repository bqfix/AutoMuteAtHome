# AutoMuteAtHome
A simple app to automatically unmute the phone when at home, and mute it when going out, or vice versa.

Mainly created as an exercise to practice with Geofences and the Google Places APIs.

Geofences are intentionally only registered for a period of 24 hours, with no automatic rescheduling via JobService.

The Manifest has its Google API key intentionally removed to avoid abuse, and thus requires a user to input their own.  
One can be obtained here: https://console.developers.google.com/flows/enableapi?apiid=placesandroid&reusekey=true
