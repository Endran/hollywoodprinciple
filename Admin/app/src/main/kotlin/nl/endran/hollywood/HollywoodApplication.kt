package nl.endran.hollywood

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class HollywoodApplication() {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(HollywoodApplication::class.java, *args)
        }
    }
}
