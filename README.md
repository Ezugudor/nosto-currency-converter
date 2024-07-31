# Currency Converter API Service

This is a Spring Boot application that provides an endpoint to convert between different currencies. It integrates with InfluxDB for logging and Grafana for monitoring.

## Features

- **Clean Architecture**: Use-case, Infrastructure, Interfaces,Contracts( e.g In the future, Swop exchange rate provider could be swapped with another 3rd party exchange rate api without affecting core business rules).
- **Result Pattern**: Services don't throw exceptions, instead, the interface (Controller) handles exceptions using some helper class methods.
- **Currency Conversion**: Convert amounts between different currencies using the `/api/v1/convert` endpoint.
- **Caching**: Getting list of supported currencies is cached since this list does not change often.
- **Logging**: Utilize InfluxDB for storing logs and metrics.
- **Monitoring**: Visualize and monitor application metrics with Grafana.

## Prerequisites

- Docker ( the 3 main services here Backend-api, InfluxDB and Grafana would be deployed as individual docker containers)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Ezugudor/nosto-currency-converter.git
cd nosto-currency-converter
```

### 2. Rename `.env.example` to `.env `

`.env` variable contains sensitive credentials which we don't want to commit into our version control. For this reason, it has been added to `.gitignore` file.
A common practice is to create a `.env.example` file serving as a template with all the required variables unset or empty. But for simplicity, I have added those credentials in `.env.example`.

Simply rename `.env.example` to `.env`

### 3. Start the services

Run the following command to build the image for the backend app as defined in the DOCKERFILE:

```bash
docker-compose build
```

Now run the following command to start the services (Backend app, Influxdb, and Grafana):

```bash
docker-compose up -d
```

Once this command is successful, the different services are now running and should be accessible in the following ports:

- Backend Api - http://localhost:8080
- Influx DB Dashboard - http://localhost:8086
- Grafana Dashboard - http://localhost:3000

### 4. Make Currency Conversion

You can test the currency conversion service by opening this link on your browser:

```
http://localhost:8080/api/v1/convert/EUR/NGN?amount=200
```

#### Example Response

```json
{
  "timestamp": "2024-07-31T11:09:26.418262919",
  "status": 200,
  "data": 359138.796
}
```

### 5. Monitor with Grafana

- **Grafana Dashboard**: Open your browser and go to `http://localhost:3000`.
- **Login**: Use the following credentials:

  - **Username**: `admin`
  - **Password**: `@dmiN1234`

- **Add InfluxDB Data Source**:

  1. Go to Configuration (gear icon) > Data Sources.
  2. Click "Add data source" and select InfluxDB.
  3. Click the `Query language` dropdown and choose `Flux` option
  4. Under the `HTTP section`, Set the URL to `http://influxdb:8086`,
  5. Username and Password : use (`admin`/`@dmiN1234`)
  6. Token and Organization is as in the `.env.example` in this project.
  7. Click "Save & Test" to confirm the connection.

- **Create Dashboards**: You can now create dashboards to visualize logs and metrics from InfluxDB using the flux query from influxDB dashboard.

## Troubleshooting

- Note that the Swop api free version (which was used in this project) only supports `EUR` as base currency.
- Ensure that Docker is running and InfluxDB and Grafana containers are up.
