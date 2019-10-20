package com.nameless.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class FindRecordApiRespone {
    private List<NewMsgListElement> newMsgList;
    private List<MsgListElement> MsgList;
}
