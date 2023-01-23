package com.example.graphqlandroid.repository

import com.apollographql.apollo3.ApolloClient
import com.example.graphqlandroid.FindCountriesOfAContinentQuery
import com.example.graphqlandroid.GetContinentsQuery
import javax.inject.Inject

class MainRepository @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getContinents() =
        apolloClient.query(GetContinentsQuery()).execute()

    suspend fun getCountriesOfSelectedContinent(continentCode: String) = apolloClient.query(
        FindCountriesOfAContinentQuery(continentCode)
    ).execute()

}
