biz.dfch.j.graylog2.plugin.alarm.execscript
============================================

Plugin: biz.dfch.j.graylog2.plugin.alarm.execscript

d-fens GmbH, General-Guisan-Strasse 6, CH-6300 Zug, Switzerland

This Graylog2 AlarmCallback Plugin lets you run arbitrary scripts on a Graylog2 node as a result of an alarm condition.

See [Creating a Graylog2 Output Plugin](http://d-fens.ch/2015/01/07/howto-creating-a-graylog2-output-plugin/) for further description on how to create plugins.

You can [download the binary](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.alarm.execscript/files) at our [drone.io](https://drone.io/github.com/dfch) account, which gets built every time we commit something to the master branch of the repository.

Getting started for users
-------------------------

This project is using Maven and requires Java 7 or higher.

* Clone this repository.
* Run `mvn package` to build a JAR file.
* Optional: Run `mvn jdeb:jdeb` and `mvn rpm:rpm` to create a DEB and RPM package respectively.
* Copy generated jar file in target directory to your Graylog2 server plugin directory.
* Restart the Graylog2 server.
