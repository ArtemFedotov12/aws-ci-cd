package com.start.springlearningdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilePart {
    private final String fileName;
    private final int partNumber;
}
