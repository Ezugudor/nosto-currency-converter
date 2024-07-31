package com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;
import com.influxdb.client.domain.WritePrecision;

import java.time.Instant;

public class InfluxDBAppender extends AppenderBase<ILoggingEvent> {

    private InfluxDBClient influxDBClient;
    private WriteApiBlocking writeApiBlocking;

    private String influxDBUrl;
    private String influxDBToken;
    private String influxDBOrg;
    private String influxDBBucket;
    private String measurement;

    public void setInfluxDBUrl(String influxDBUrl) {
        this.influxDBUrl = influxDBUrl;
    }

    public void setInfluxDBToken(String influxDBToken) {
        this.influxDBToken = influxDBToken;
    }

    public void setInfluxDBOrg(String influxDBOrg) {
        this.influxDBOrg = influxDBOrg;
    }

    public void setInfluxDBBucket(String influxDBBucket) {
        this.influxDBBucket = influxDBBucket;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    @Override
    public void start() {
        try {

            influxDBClient = InfluxDBClientFactory.create(influxDBUrl, influxDBToken.toCharArray(),
                    influxDBOrg,
                    influxDBBucket);
            writeApiBlocking = influxDBClient.getWriteApiBlocking();
            super.start();
        } catch (Exception e) {
            addError("Failed to initialize InfluxDB client.", e);
        }
    }

    @Override
    protected void append(ILoggingEvent eventObject) {

        if (writeApiBlocking == null) {
            addError("WriteApiBlocking is not initialized. Skipping log append.");
            return;
        }

        try {
            Point point = Point.measurement(measurement)
                    .addTag("level", eventObject.getLevel().toString())
                    .addField("message", eventObject.getFormattedMessage())
                    .time(Instant.ofEpochMilli(eventObject.getTimeStamp()), WritePrecision.MS);
            writeApiBlocking.writePoint(influxDBBucket, influxDBOrg, point);
        } catch (Exception e) {
            addError("Error writing log to InfluxDB: " + e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        if (influxDBClient != null) {
            influxDBClient.close();
        }
        super.stop();
    }
}
