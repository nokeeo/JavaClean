#!bin/bash

jarFilePath="/Volumes/Media/Documents/Archive/JavaCleanBuild/"
jarFileName="JavaClean.jar"
configFile="/Volumes/Media/Documents/Archive/JavaCleanBuild/defaultConfig.xml"
sourcePath="/Users/ericlee/Desktop"
destinationPath="/Volumes/Media/Documents/Archive"

cd $jarFilePath

java -jar $jarFilePath$jarFileName $configFile $sourcePath $destinationPath