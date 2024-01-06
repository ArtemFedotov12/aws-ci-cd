package com.start.springlearningdemo.service.model;

import java.nio.file.Path;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FileSplitting {
  List<Path> files;
  String encodedPathDirectory;
}
