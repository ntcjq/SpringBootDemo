package com.cjq.SpringBootDemo.log;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.status.ErrorStatus;

public class MongoDBAppender extends AppenderBase<LoggingEvent> {

    private MongoClient _mongo;
    private MongoCollection<Document> _collection;
    private String _dbHost = "127.0.0.1";
    private int _dbPort = 27017;
    private String _dbName = "log";
    private String _dbCollectionName = "SpringBoot";


    @Override
    public void start() {
        try {
            _mongo = new MongoClient(_dbHost, _dbPort);
            MongoDatabase db = _mongo.getDatabase(_dbName);
            _collection = db.getCollection(_dbCollectionName);
        } catch (Exception e) {
            addStatus(new ErrorStatus("Failed to initialize MongoDB", this, e));
            return;
        }
        super.start();
    }

    public void setDbHost(String dbHost) {
        _dbHost = dbHost;
    }

    public void setDbName(String dbName) {
        _dbName = dbName;
    }

    public void setDbCollectionName(String dbCollectionName) {
        _dbCollectionName = dbCollectionName;
    }

    public void setDbPort(int dbPort) {
        _dbPort = dbPort;
    }


    @Override
    public void stop() {
        _mongo.close();
        super.stop();
    }

    @Override
    protected void append(LoggingEvent event) {
        try{
            Document document = getDocument(event.getFormattedMessage());
            if(document != null){
                _collection.insertOne(document);
            }
        }catch (Exception e) {
            addStatus(new ErrorStatus("日志写入到MongoDB出错", this, e));
        }
    }
    //这里指定只有json格式的日志才能输入到mongo
    private Document getDocument(String json) {
        try{
            return Document.parse(json);
        }catch (Exception e) {
            return null;
        }
    }
}