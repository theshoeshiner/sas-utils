# sas-utils

Java library for working with SAS Files

This library started as a Java port of the python xport module (https://pypi.org/project/xport/) with some significant performance optimizations, namely the ability to stream observation records instead of reading them all into memory.

# Usage

Because the library needs random access to the data we must have it in a file. If your data is a stream then you need to write it to a file first. This is mostly to enhance performance so that we dont have to read the entire thing into memory. The initial parsing step skips the data and just reads the metadata. The streamObservations() call then reads the data and uses a Java Stream to allow you to iterate the observations one a time.

```
File file = <your file>
LibraryXpt library = ParserXpt.parseLibrary(file);
//Or For SAS7BDAT use
LibraryBdat library = ParserBdat.parseLibrary(file);

//iterate data sets (sas7bdat will only ever have 1)
for(Dataset dataset : library.getDatasets()) {

  //iterate variables if necessary...
  dataset.getVariables();

  //stream observations by passing the file reference...
  dataset.streamObservations(file).forEach(obs -> {
      //dowhatever
  });

}

```
