package com.nameless.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class NameAndMsg {
    private String id;
    private String name;
    private String msg;
}
