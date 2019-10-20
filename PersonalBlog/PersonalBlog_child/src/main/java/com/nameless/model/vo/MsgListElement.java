package com.nameless.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class MsgListElement {
    private String name;
    private List<ContentOfMsgListElement> content;
}
