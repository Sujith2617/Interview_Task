# Image Upload App – Interview Task

This Android app allows the user to:

- Select an image from the gallery
- Upload the image using Multipart API
- Receive request_id from server
- Fetch processed result after delay
- Display the result image

## Tech Stack
- Kotlin
- Jetpack Compose
- MVVM
- Retrofit (Multipart)
- Coroutines
- StateFlow
- Coil

## API Flow
1. Upload image → get request_id
2. Wait 10 seconds
3. Call result API with request_id
4. Show result image

## Features
- Loading screen while processing
- Success and error handling
- Clean MVVM architecture

## Outcome
This project demonstrates image upload, API polling, and state management in modern Android.
