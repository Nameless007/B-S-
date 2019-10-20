package com.nameless.model.response;

import com.nameless.model.Record;

import java.util.List;

public class ResponseHasRecords extends Response{

    public List<Record> records;

    public ResponseHasRecords(ResultCode resultCode, List<Record> records) {
        super(resultCode);
        this.records = records;
    }
}
