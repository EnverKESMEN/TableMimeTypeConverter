# Simple File Converter For Tables
This program can convert your tables from csv and xls to csv,xml,html,json,pdf and xls formats. It also can group your columns.

## Install
You are free to compile it but if you are in a hurry, you can use the converter.jar file directly.

## Usage 

```
$ java -jar converter.jar <input file> <target folders> <output mime types (csv,xml,html,json,pdf,xls)> [-g column_names]
```
### Example 

```
java -jar converter.jar /home/enver/Desktop/Github/readTest/a.xls ~/Desktop/Github/readTest/outputs  html,pdf
```

If you want to see outputs. You can find results of this command at Outputs directory in repository.
