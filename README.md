# EarthquakeScanner
Presenting an android app that provide the features for analyzing earthquakes in different regions based on user parameters. 
The app integrates google maps that will show the real-time location of regions effected with earthquake.

SPLASH SCREEN:
The splash screen is the first user interaction screen that checks internet connection for the user and proceeds accordingly. 
If user do not have internet connection then a toast will be displayed informing the user about no internet connection.

FLASH CARDS:
Upon successful connection, user is navigated to home screen where he has different options to see earthquakes in last 1hr., 12 hr., 24hr., 24 hr. with magnitude higher than 5 all over the world.

MAPS SCREEN:
Marker clustering is google maps concept to show clusters in the area where we have too many markers and upon zooming it will zoom out markers.
The app has implemented this feature to give better user interface

CUSTOM SEARCH:
The user is also provided with the functionality to customize their search along with one touch search options where he has to select city, date range, time range and miles.

SEARCH SCREEN:
The search screen searches the city for the user and is an implementation of reactive programming using RxAndroid.
Upon selection, it provides it required latitude and longitude to the API for fetching the data.

DATE & TIME SCREEN:
This screen allows the user to select the date and time range for the customized query. 
The library used for this beautiful UI is 
com.borax12.materialdaterangepicker:library:1.9

DISTANCE SEEKBAR:
A material design library is used to display more interactive spinner for the user.
The library used
org.adw.library:discrete-seekbar:1.0.1
Send button is also best example of google’s material design for floating action button.
The library used is
com.github.clans:fab:1.6.4

Send button will again take the user to Google maps indication the regions resulted in user’s customized query.

APPLICATION LOGIC & MVP ARCHITECTURE
ARCHITECTURE:
The design of the app follows MVP architecture to separate the UI from logic and it follows repository design that separates the logic of getting data.
APP LOGIC:
The design of the application was to give the user best interface and there should be scope of further app design and functional enhancements.
The card layout is used to give the user feature to easy and smart access to detect earthquakes all around the world. Since it is almost impossible to provide the user with all easy buttons to access so another functionality of customizing the search is implemented that will allow the user to choose his own search criteria.
Since, the API needs location in latitude and longitude, so APIXU API is used to give the user a feature where he can search city in terms of name but on background, it will return selected latitude and longitude to the API.
The app also checks internet connection and GPS access.
The app design has followed industry standard coding and designing specifications.



