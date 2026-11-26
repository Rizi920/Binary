# Binary: Contacts Manager

An Android app (Java) for browsing and adding contacts through a REST API, with an **All** and a
**Favourites** tab. Built in 2019 as a take-home Android assessment.

## Features

- List contacts from the API (`GET /api/v1/user`) with avatars loaded by Glide
- Add a new contact through a form (`POST /api/v1/user`)
- Mark contacts as favourites and view them in their own tab
- Contact details screen

## Tech stack

Java · Android SDK (Support Library, RecyclerView, ViewPager, Fragments) · Retrofit 2 · OkHttp 3 · Gson · Glide

## Project structure

```
app/src/main/java/com/example/jobportal/
├── network/      # ApiClient and ApiServices
├── models/       # Contacts and insertContact response models
├── adapters/     # ContactsAdapter, MyPagerAdapter
├── ui/           # MainActivity, AddContactActivity, DetailsActivity, fragments
└── utils/
```

## Note on the API

The app uses a public mock REST server configured in `network/ApiClient.java`, which may no longer be online.
Point `BASE_URL` at any server with the same endpoints to run it.

## Getting started

1. Clone the repository and open the project folder in Android Studio.
2. Let Android Studio sync Gradle. The project was written in 2019 against Android Gradle Plugin 3.x and the
   Support Library, so Android Studio may offer to upgrade the Gradle wrapper and plugin; accept the
   suggested upgrades if the sync fails on a recent IDE.
3. Run the `app` configuration on an emulator or device.

## License

[MIT](LICENSE)
