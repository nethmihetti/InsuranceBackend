package com.inno.soramitsu.insurance.RESTserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestServerApplication

fun main(args: Array<String>) {
	runApplication<RestServerApplication>(*args)
}
