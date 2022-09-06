# Countries Android Project
## Preview

Aim of this project is using API endpoint to retrieve information about countries to serve these data to user.
User can select favourite countries and see details on detail page. These details are country code, flag and wikipedia
information of selected country.

## Technologies

In this project, I used Retrofit, Room, MVVM architecture, Coroutines and Navigation.

### Retrofit

* I should prepare to get information from API endpoint(https://rapidapi.com/wirefreethought/api/geodb-cities/). 
Firstly, I must subscribe this API and get host and api-key information from this website.
Then, I chose Retrofit to send http request to the endpoint. At `CountriesApi.kt` interface I identified methods that use 
at Retrofit. These methods as described with keyword `suspend` because http request may take for a while that's why
I use coroutines in this interface.

```kotlin
    @Headers("X-RapidAPI-Key: ${BuildConfig.API_KEY}",
        "X-RapidAPI-Host: wft-geo-db.p.rapidapi.com")
    @GET("v1/geo/countries")
    suspend fun getCountries(
        @Query("limit") limit: Int = 10
    ): Response<Country>
```
* At`CountryService.kt`  , I generated an object with Retrofit builder. Retrofit implements these methods at background
Then, I wrote data classes to fit json files of endpoints. Parsing process is completed from GsonConverterFactory automatically.
### Room Database
* I used Room DB for saving favourites countries. Room DB is wrapper of SQLite.
* Firstly, I created a table with @Entity annotation at `SavedCountryData.kt`. After this, I wrote interfaces of methods
for database process and Room implements these methods in background.Also, I used `suspend` keyword for these methods as 
the same reason with http requests. In database class, a static database object is generated. I created a repository class
that does the database operations. 

### UI

* In this project, I used an activity and three fragments for UI. With navigation, user can switch between fragments.

![](../../../../navigation.png)

* At home fragment, view as an observer, observes live data from `HomeViewModel.kt`. With generated Retrofit object,
limited number of data information is retrieved at viewmodel. 
```kotlin
 private fun fetchCountries() {

        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = countriesService.getCountries(10)

            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    loading.value = false
                    country.value = response.body()
                }
            }
        }
    }
```
* Adapter is used to make the suitable for RecyclerView.
![](../../../../Screenshot 2022-09-05 225812.png)

## Ongoing
### Bugs

* Home and Saved navigation buttons after clicking wiki data link are invisible at home and saved fragments.
* At detail fragment, flag of country must be shown, but I took 404 http error.

### Features
* Flag will be added to detail fragment.
* Saved button will be added to detail fragment.
* Unit test will be written.
