package nl.endran.hollywood.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
@ComponentScan(basePackages = arrayOf("nl.endran.hollywood"))
open class CoreConfiguration {

    @Bean
    open fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        objectMapper.findAndRegisterModules()

        val javaTimeModule = JavaTimeModule()
        objectMapper.registerModule(javaTimeModule)

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

        return objectMapper
    }

    @Bean
    open fun databaseReference(@Value(value = "classpath:firebase.json")
                               firebaseJsonResource: Resource): DatabaseReference {
        val options = FirebaseOptions.Builder()
                .setServiceAccount(firebaseJsonResource.inputStream)
                .setDatabaseUrl("https://hollywoodprinciple-fdebd.firebaseio.com/")
                .build()

        FirebaseApp.initializeApp(options)

        return FirebaseDatabase.getInstance().reference
    }

}
