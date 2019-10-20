package com.nameless.model.response;

import com.nameless.model.Record;
import com.nameless.model.vo.FindRecordApiRespone;

import java.util.List;

public class ResponseHasInitMsg extends Response{

    public FindRecordApiRespone findRecordApiRespone;

    public ResponseHasInitMsg(ResultCode resultCode, FindRecordApiRespone findRecordApiRespone) {
        super(resultCode);
        this.findRecordApiRespone = findRecordApiRespone;
    }
}
