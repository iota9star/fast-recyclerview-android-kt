# Fast-RecyclerView
[![jcenter](https://api.bintray.com/packages/iota9star/nichijou/fast-recyclerview/images/download.svg)](https://bintray.com/iota9star/nichijou/fast-recyclerview/_latestVersion) [![Build Status](https://travis-ci.org/iota9star/fast-recyclerview-android-kt.svg?branch=master)](https://travis-ci.org/iota9star/fast-recyclerview-android-kt) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/8a916e4e09e04bea8bc7a3b439e673ed)](https://www.codacy.com/app/iota9star/fast-recyclerview-android-kt?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=iota9star/fast-recyclerview-android-kt&amp;utm_campaign=Badge_Grade)[![License](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) [![API](https://img.shields.io/badge/API-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=14)

Fast-RecyclerView is a kotlin library to use RecyclerView easily without ViewHolder and Adapter.

----

## Gradle Dependency
The Gradle dependency is available via jCenter. Add this to your module's build.gradle file:
``` gradle
dependencies {
    // ... other dependencies
    implementation 'io.nichijou:fastrecyclerview:0.0.2'
}
```

## Screenshot
These are the screenshots of simple project.
| ![single](https://raw.githubusercontent.com/iota9star/fast-recyclerview-kt/master/art/Screenshot_single.png "single") | ![multiple](https://raw.githubusercontent.com/iota9star/fast-recyclerview-kt/master/art/Screenshot_multiple.png "multiple") |
| :------: | :------: |

## Use Case
#### Single ViewType
``` kotlin
recycler_view.with(layoutResId, data) {// init data
    // doSomething
}
```

#### Multiple ViewType
``` kotlin
recycler_view.with(layoutResId1, viewTypeMatching) {
    // doSomething
}.with(layoutResId2, viewTypeMatching) {
    // doSomething
}// with more view type...
```

#### Add or Reset
``` kotlin
recycler_view.addNew(newData, reset)
```
#### Update
``` kotlin
recycler_view.submitList(newData)// use DiffUtil
```
#### Clear
``` kotlin
recycler_view.clearList()
```

#### More
see the sample project. You can download an APK of the sample project. click [here](https://raw.githubusercontent.com/iota9star/fast-recyclerview-kt/master/art/simple.apk).

## Credits
[fast-list](https://github.com/dev-labs-bg/fast-list)

## Licenses
``` plain
   Copyright 2018. iota9star

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```