# News Provider [![Build](https://github.com/Covid-Alert-Microservices/news-provider/actions/workflows/build.yaml/badge.svg)](https://github.com/Covid-Alert-Microservices/news-provider/actions/workflows/build.yaml) [![codecov](https://codecov.io/gh/Covid-Alert-Microservices/news-provider/branch/master/graph/badge.svg?token=R3KWA2KVNT)](https://codecov.io/gh/Covid-Alert-Microservices/news-provider)

This repository contains the source code of the service providing the latest news to the user.

## Development environment

Clone the repository and browse to the project's directory:

`git clone git@github.com:Covid-Alert-Microservices/news-provider.git && cd news-provider`

Start attached services:

`docker compose up`

Then start the application :

`./gradlew bootRun` (or using your IDE)

You then can access the news at http://localhost:8091/api/news.