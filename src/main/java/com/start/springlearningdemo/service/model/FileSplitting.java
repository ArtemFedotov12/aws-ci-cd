package com.start.springlearningdemo.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FileSplitting {
    List<Path> files;
    String encodedPathDirectory;
}
