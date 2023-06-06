# Candle: Real-Time Cryptocurrency Price Analysis and Alert System

<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,kafka,docker,mysql" />
  </a>
</p>

The project is a microservice-based system designed to perform real-time cryptocurrency price analysis and generate alerts based on customizable conditions. It consists of three main components: a crawler, a data processing system, and an API service. The crawler retrieves cryptocurrency price data at regular intervals and publishes it to a Kafka message broker. The data processing system consumes the data from Kafka, applies configurable tasks and conditions on time windows, and generates alerts when conditions are met. The alerts are stored in a MySQL database. The API service, built with the Spring Framework, retrieves alert data from the database and exposes endpoints for accessing and displaying the alerts. The project is containerized using Docker and can be easily deployed using Docker Compose.


Key Features:

-   Real-time cryptocurrency price retrieval and analysis
-   Customizable tasks and conditions for alert generation
-   Efficient data processing using Kafka message broker
-   Data storage and retrieval using MySQL database
-   RESTful API service for accessing and displaying alerts
-   Configuration flexibility with YAML parameterization
-   Scalability and easy deployment using Docker and Docker Compose

