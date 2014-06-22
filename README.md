spray-services-gradle [![Build Status](https://travis-ci.org/lukaszbudnik/spray-services-gradle.svg?branch=master)](https://travis-ci.org/lukaszbudnik/spray-services-gradle)
=====================

Shows how you can optimise REST API in front of slow backend with Spray on top of Scala and Akka.

## Slow backend simulation

In order to simulate slow blocking backend I have implemented `service.ItemService` in the following way:

  * `create` operation blocks for `Thread.sleep(250 + random.nextInt(100))` which means it will execute between 250 and 350 miliseconds aprox.
  * `get` operation blocks for `Thread.sleep(150 + random.nextInt(100))` which means it will execute between 150 and 250 miliseconds aprox.

## Conducting simple load tests

First start Spray Can server with our service. Just execute the following gradle task:

`gradle run`

The server will start in a second or two.

I have written a very simple load tests script for performing 1000 create and 1000 read requests. As a parameter it expects API version.

### /api/v1

V1 is a naive implementation which directly calls legacy `service.ItemService` for every incoming request.

`./test-version.sh v1`

On my dev Ubuntu VM it takes around 43 seconds.

### Load testing /api/v2

V2 sends a message (using ask pattern) to `worker.ItemManager` actor which wraps calls to `service.ItemService` using Scala's `blocking` directive.

`./test-version.sh v2`

On my machine it takes around 7 seconds. That's 6 times faster.
