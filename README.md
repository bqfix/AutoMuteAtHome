# AutoMuteAtHome

### Description
This app automatically unmutes the device when at home, and/or mutes it when going out, or vice versa.
Accomplished via the Geofences and Google Places APIs.
Geofences are intentionally only registered for a period of 24 hours.

The Manifest has its Google API key intentionally removed to avoid abuse, and thus requires a user to input their own.  
One can be obtained here: https://console.developers.google.com/flows/enableapi?apiid=placesandroid&reusekey=true

This project was built for the Udacity Android Developer nanodegree.


### What I accomplished in this project:

* Accessed the Google Places API to allow for choosing of locations to create a Geofence around.
* Implemented Geofences to create barriers that the device reacts to upon crossing, and stored these in a local SQLite database.
* Allowed the user to choose whether to mute or unmute the device upon entering/exiting a Geofence, and stored these choices locally.
* Utilized Broadcast Receivers to execute code when the device crosses a Geofence.

### License

MIT License

Copyright (c) 2019 Benjamin Quimado Fix

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

