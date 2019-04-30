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

Copyright 2016 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.

