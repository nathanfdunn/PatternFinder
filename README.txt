Pattern Matcher and Extractor (PEAM)


Author: Nathan Dunn
	nathan.dunn@umit.maine.edu


Description:
	This application is used to “tokenize” ice core data into high-level features, and then “extract” patterns within the data. This allows researchers to filter through large data sets more efficiently.


Installation Instructions:
-build the project using the build.xml file
-install any dependencies


Dependencies:
-jcommon
-jfreechart

Download from http://www.jfree.org/jfreechart/download.html
Add only the jcommon and jfreechart jars to your build path. Adding all of the jars will cause SWT components to conflict with the Swing components.


Demo:
-Run the main() method in CommandAppTest.java within the 'tests' package


Contents:
-DataSets_R - A folder of CSV files containing test data
-SerializedObjects - A folder where objects can be saved and loaded through serialization
-PatternDetection - the java project 
