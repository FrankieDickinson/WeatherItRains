# WeatherItRains
WeatherItRains App Which uses the Dark Sky Web API. 
Currently has multichannel notifications support.
Uses a constraint layout and async background notifications. Layout is in Alpha so I can display information for testing
purposes.

Support for a variety of SDK versions. Works well on my test device SDK version 23. 
Using timers allows the user to be able to set when the notifications are recieved. 
If no time is set then a default value is used. This default value will be displayed to the user when they first launch the 
application. 

The end goal of this project is to create a weather app for people who only want to know when the weather will
affect their day. This includes when it rains and is too hot and / or humid currently. Therefore I will notifications like "Remeber to bring an umbrella" with relevant image.
"Don't forget to bring sunscreen it will be x temperature today". 

All notifications will be managed in settings. 
